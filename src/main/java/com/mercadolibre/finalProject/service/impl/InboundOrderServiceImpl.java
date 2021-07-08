package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.dtos.BatchDTO;
import com.mercadolibre.finalProject.dtos.InboundOrderDTO;
import com.mercadolibre.finalProject.dtos.request.inboundOrder.InboundOrderCreateRequestDTO;
import com.mercadolibre.finalProject.dtos.request.inboundOrder.InboundOrderUpdateRequestDTO;
import com.mercadolibre.finalProject.dtos.response.InboundOrderResponseDTO;
import com.mercadolibre.finalProject.exceptions.*;
import com.mercadolibre.finalProject.model.InboundOrder;
import com.mercadolibre.finalProject.model.mapper.BatchMapper;
import com.mercadolibre.finalProject.repository.OrderRepository;
import com.mercadolibre.finalProject.service.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InboundOrderServiceImpl implements IInboundOrderService {
    private final IWarehouseService warehouseService;
    private final ISectorService sectorService;
    private final IRepresentativeService representativeService;
    private final OrderRepository repository;
    private final IBatchService batchService;

    public InboundOrderServiceImpl(IWarehouseService warehouseService, ISectorService sectorService, IRepresentativeService representativeService, OrderRepository repository, IBatchService batchService) {
        this.warehouseService = warehouseService;
        this.sectorService = sectorService;
        this.representativeService = representativeService;
        this.repository = repository;
        this.batchService = batchService;
    }

    @Override
    public InboundOrderResponseDTO create(InboundOrderCreateRequestDTO dto, String username) throws InboundOrderAlreadyExistException, WarehouseNotFoundException, RepresentativeNotFound, SectorNotFoundException, InternalServerErrorException, CreateBatchStockException {
        var representativeId = validadeSaveRequest(dto.getSection().getWarehouseCode(), username, dto.getSection().getCode());
        //register order and assign representative if fails throws
        var saveOrder = new InboundOrder(dto.getOrderDate(), representativeId);
        var order = repository.save(saveOrder);

        return save(new InboundOrderDTO(dto), representativeId, order.getId());
    }

    @Override
    public InboundOrderResponseDTO update(InboundOrderUpdateRequestDTO dto, String username) throws CreateBatchStockException, InboundOrderNotFoundException {
        var representativeId = validadeSaveRequest(dto.getSection().getWarehouseCode(), username, dto.getSection().getCode());
        var order = repository.findById(dto.getOrderNumber());
        if (order.isPresent()) {
            var batches = order.get().getBatches();
            try {
                var response = save(new InboundOrderDTO(dto), representativeId, order.get().getId());

                var toDelete = batches.stream().filter((b) -> response.getBatchStock().stream().noneMatch((r) -> r.getId().equals(b.getId()))).collect(Collectors.toList());

                batchService.deleteAll(toDelete);
                return response;
            } catch (Exception e) {
                if (!batches.isEmpty())
                    batchService.save(batches.stream().map(BatchMapper::toDTO).collect(Collectors.toList()), batches.get(0).getSector().getId(), order.get().getId());
                throw e;
            }

        }

        throw new InboundOrderNotFoundException();
    }

    private Long validadeSaveRequest(Long warehouseId, String username, Long sectorId) {
        //warehouse exist if not throws
        var warehouse = warehouseService.findById(warehouseId);
        //representative works in warehouse if not throws?
        var representative = representativeService.findByAccountUsernameAndWarehouseId(username, warehouse.getId());
        //sector is valid if not throws
        var sector = sectorService.findById(sectorId);
        if (!sector.getWarehouseId().equals(warehouse.getId()))
            throw new SectorNotFoundException("Sector id " + sector.getId() + " not found in warehouse Id " + warehouse.getId());
        return representative.getId();
    }

    private InboundOrderResponseDTO save(InboundOrderDTO dto, Long representativeId, Long orderId) throws CreateBatchStockException {
        // save all batchStock if fails throws
        var batchDTOList = dto.getBatchStock();
        var batchStock = batchService.save(batchDTOList, dto.getSection().getCode(), orderId);


        List<BatchDTO> batchStockResponse = batchStock.stream().map(BatchMapper::toDTO).collect(Collectors.toList());
        return new InboundOrderResponseDTO(orderId, batchStockResponse);

    }
}
