package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.dtos.request.ReturnOrderRequestDTO;
import com.mercadolibre.finalProject.dtos.request.UpdateReturnOrderRequestDTO;

public interface IReturnOrderService {

    void returnOrder(ReturnOrderRequestDTO dto);

    void updateStatus(UpdateReturnOrderRequestDTO dto);

}
