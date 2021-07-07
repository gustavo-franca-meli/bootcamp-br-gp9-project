package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.dtos.InboundOrderDTO;
import com.mercadolibre.finalProject.dtos.request.inboundOrder.InboundOrderCreateRequestDTO;
import com.mercadolibre.finalProject.dtos.request.inboundOrder.InboundOrderUpdateRequestDTO;
import com.mercadolibre.finalProject.dtos.response.InboundOrderResponseDTO;
import com.mercadolibre.finalProject.exceptions.*;
import org.springframework.stereotype.Service;

public interface IInboundOrderService {

    InboundOrderResponseDTO create(InboundOrderCreateRequestDTO dto, Long representativeId) throws InboundOrderAlreadyExistException, WarehouseNotFoundException, RepresentativeNotFound, SectorNotFoundException, InternalServerErrorException, CreateBatchStockException;

    InboundOrderResponseDTO update(InboundOrderUpdateRequestDTO dto, Long representative) throws CreateBatchStockException, InboundOrderNotFoundException;
}
