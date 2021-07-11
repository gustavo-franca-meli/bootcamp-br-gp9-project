package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.dtos.request.ReturnOrderRequestDTO;
import com.mercadolibre.finalProject.dtos.request.UpdateReturnOrderDTO;

public interface IReturnOrderService {

    void returnOrder(ReturnOrderRequestDTO dto);

    void updateStatus(UpdateReturnOrderDTO dto);

}
