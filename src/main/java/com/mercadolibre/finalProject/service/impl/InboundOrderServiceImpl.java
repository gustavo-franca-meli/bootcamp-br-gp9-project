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
    private IWarehouseService warehouseService;
    private ISectorService sectorService;
    private IRepresentativeService representativeService;
    private OrderRepository repository;
    private IBatchService batchService;

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

        //representative works in warehouse if not throws?
        var representative = representativeService.findByIdAndWarehouseId(representation, warehouse.getId());
        //sector is valid if not throws
        var sector = sectorService.findById(dto.getSection().getCode());
        // save all batchStock if fails throws
        var batchStock = batchService.create(dto.getBatchStock(), sector);
        List<BatchDTO> batchStockResponse = batchStock.stream().map(BatchMapper::toDTO).collect(Collectors.toList());

        //register order and assign representative if fails throws
        var order = new Order(dto.getOrderDate(), representative, batchStock);
        repository.save(order);
        return new InboundOrderResponseDTO(batchStockResponse);
    }

    @Override
    public InboundOrderResponseDTO save(InboundOrderDTO dto, String representative) throws InboundOrderNotFoundException, InternalServerErrorException {
        return null;
    }
}
