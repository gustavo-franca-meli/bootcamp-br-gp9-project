package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.dtos.BatchDTO;
import com.mercadolibre.finalProject.dtos.InboundOrderDTO;
import com.mercadolibre.finalProject.dtos.response.InboundOrderResponseDTO;
import com.mercadolibre.finalProject.exceptions.*;
import com.mercadolibre.finalProject.model.Order;
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
    public InboundOrderResponseDTO create(InboundOrderDTO dto, String representation) throws InboundOrderAlreadyExistException, WarehouseNotFoundException, RepresentativeNotFound, SectorNotFoundException, InternalServerErrorException, CreateBatchStockException {
        //warehouse exist if not throws
        var warehouse = warehouseService.findById(dto.getSection().getWarehouseCode());

        // TODO: retrieve the id of the representative, probably via the token sent by the header, a mocked id was placed (1L) so as not to give an error
        //representative works in warehouse if not throws?
        var representative = representativeService.findByIdAndWarehouseId(1L, warehouse.getId());
        //sector is valid if not throws
        var sector = sectorService.findById(dto.getSection().getCode());
        // save all batchStock if fails throws
        var batchStock = batchService.create(dto.getBatchStock(), sector);
        List<BatchDTO> batchStockResponse = batchStock.stream().map(BatchMapper::toDTO).collect(Collectors.toList());

        //register order and assign representative if fails throws
        var order = new Order(dto.getOrderDate(), null, batchStock);
        repository.save(order);

        return new InboundOrderResponseDTO(batchStockResponse);
    }

    @Override
    public InboundOrderResponseDTO save(InboundOrderDTO dto, String representative) {
        return null;
    }
}
