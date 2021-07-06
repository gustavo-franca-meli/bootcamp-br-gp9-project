package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.dtos.BatchDTO;
import com.mercadolibre.finalProject.dtos.InboundOrderDTO;
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
    public InboundOrderResponseDTO create(InboundOrderDTO dto, Long representativeId) throws InboundOrderAlreadyExistException, WarehouseNotFoundException, RepresentativeNotFound, SectorNotFoundException, InternalServerErrorException, CreateBatchStockException {
        //warehouse exist if not throws
        var warehouse = warehouseService.findById(dto.getSection().getWarehouseCode());
        //representative works in warehouse if not throws?
        var representative = representativeService.findByIdAndWarehouseId(representativeId, warehouse.getId());
        //sector is valid if not throws
        var sector = sectorService.findById(dto.getSection().getCode());
        // save all batchStock if fails throws
        var batchStock = batchService.create(dto.getBatchStock(), null);
        List<BatchDTO> batchStockResponse = batchStock.stream().map(BatchMapper::toDTO).collect(Collectors.toList());

        //register order and assign representative if fails throws
        var order = new InboundOrder(dto.getOrderDate(), representative.getId(), batchStock);
        repository.save(order);

        return new InboundOrderResponseDTO(batchStockResponse);
    }

    @Override
    public InboundOrderResponseDTO update(InboundOrderDTO dto, Long representative) {
        return null;
    }
}
