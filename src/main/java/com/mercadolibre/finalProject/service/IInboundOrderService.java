package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.dtos.InboundOrderDTO;
import com.mercadolibre.finalProject.dtos.response.InboundOrderResponseDTO;
import com.mercadolibre.finalProject.exceptions.*;
import org.springframework.stereotype.Service;

public interface IInboundOrderService {

    InboundOrderResponseDTO create(InboundOrderDTO dto, Long representativeId) throws InboundOrderAlreadyExistException, WarehouseNotFoundException, RepresentativeNotFound, SectorNotFoundException, InternalServerErrorException, CreateBatchStockException;

    InboundOrderResponseDTO update(InboundOrderDTO dto, Long representative);
}
