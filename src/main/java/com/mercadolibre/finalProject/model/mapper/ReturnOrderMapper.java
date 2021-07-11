package com.mercadolibre.finalProject.model.mapper;

import com.mercadolibre.finalProject.dtos.request.ReturnOrderRequestDTO;
import com.mercadolibre.finalProject.model.PurchaseOrder;
import com.mercadolibre.finalProject.model.ReturnOrder;
import com.mercadolibre.finalProject.model.enums.ReturnOrderStatusType;

public interface ReturnOrderMapper {

    static ReturnOrder toModel(ReturnOrderRequestDTO dto) {
        var purchaseOrder = new PurchaseOrder(dto.getPurchaseOrderId());
        return new ReturnOrder(null, dto.getReason(), ReturnOrderStatusType.PROCESSING.getCod(), purchaseOrder);
    }

}
