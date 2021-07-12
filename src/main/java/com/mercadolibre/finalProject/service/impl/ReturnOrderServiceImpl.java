package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.dtos.request.ReturnOrderRequestDTO;
import com.mercadolibre.finalProject.dtos.request.UpdateReturnOrderRequestDTO;
import com.mercadolibre.finalProject.exceptions.NotFoundException;
import com.mercadolibre.finalProject.model.ReturnOrder;
import com.mercadolibre.finalProject.model.enums.ReturnOrderStatusType;
import com.mercadolibre.finalProject.model.mapper.ReturnOrderMapper;
import com.mercadolibre.finalProject.repository.ReturnOrderRepository;
import com.mercadolibre.finalProject.service.IPurchaseOrderService;
import com.mercadolibre.finalProject.service.IRepresentativeService;
import com.mercadolibre.finalProject.service.IReturnOrderService;
import org.springframework.stereotype.Service;

@Service
public class ReturnOrderServiceImpl implements IReturnOrderService {

    private final ReturnOrderRepository returnOrderRepository;
    private final IPurchaseOrderService purchaseOrderService;
    private final IRepresentativeService representativeService;

    public ReturnOrderServiceImpl(ReturnOrderRepository returnOrderRepository, IPurchaseOrderService purchaseOrderService, IRepresentativeService representativeService) {
        this.returnOrderRepository = returnOrderRepository;
        this.purchaseOrderService = purchaseOrderService;
        this.representativeService = representativeService;
    }

    @Override
    public void returnOrder(ReturnOrderRequestDTO dto) {
        this.purchaseOrderService.getById(dto.getPurchaseOrderId(), dto.getBuyerUsername());
        var model = ReturnOrderMapper.toModel(dto);

        this.returnOrderRepository.save(model);
    }

    @Override
    public void updateStatus(UpdateReturnOrderRequestDTO dto) {
        this.validateIfRepresentativeBelongsWarehouse(dto.getWarehouseId(), dto.getUsername());
        var returnOrder = findBy(dto.getReturnOrderId());

        this.validateIfStatusTypeExist(dto.getStatusCode());
        returnOrder.setStatus(dto.getStatusCode());
        this.returnOrderRepository.save(returnOrder);
    }

    private ReturnOrder findBy(Long id) {
        var returnOrder = this.returnOrderRepository.findById(id);
        return returnOrder.orElseThrow(() -> new NotFoundException("This return order doesn't exist. Id: " + id));
    }

    private void validateIfRepresentativeBelongsWarehouse(Long warehouseId, String username) {
        this.representativeService.findByAccountUsernameAndWarehouseId(username, warehouseId);
    }

    private void validateIfStatusTypeExist(Integer code) {
        ReturnOrderStatusType.toEnum(code);
    }

}
