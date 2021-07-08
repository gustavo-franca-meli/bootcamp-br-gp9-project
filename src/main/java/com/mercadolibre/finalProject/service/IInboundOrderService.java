package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.dtos.request.inboundOrder.InboundOrderCreateRequestDTO;
import com.mercadolibre.finalProject.dtos.request.inboundOrder.InboundOrderUpdateRequestDTO;
import com.mercadolibre.finalProject.dtos.response.InboundOrderResponseDTO;
import com.mercadolibre.finalProject.exceptions.*;

public interface IInboundOrderService {

    InboundOrderResponseDTO create(InboundOrderCreateRequestDTO dto, String username) throws InboundOrderAlreadyExistException, WarehouseNotFoundException, RepresentativeNotFound, SectorNotFoundException, InternalServerErrorException, CreateBatchStockException;

    InboundOrderResponseDTO update(InboundOrderUpdateRequestDTO dto, String username) throws CreateBatchStockException, InboundOrderNotFoundException;
}
