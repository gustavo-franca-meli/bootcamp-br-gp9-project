package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.dtos.InboundOrderDTO;
import com.mercadolibre.finalProject.dtos.response.InboundOrderResponseDTO;
import com.mercadolibre.finalProject.exceptions.*;

public interface IInboundOrderService {

    InboundOrderResponseDTO create(InboundOrderDTO dto, String representativeId) throws InboundOrderAlreadyExistException, WarehouseNotFoundException, RepresentativeNotFound, SectorNotFoundException, InternalServerErrorException, CreateBathStockException;

    InboundOrderResponseDTO save(InboundOrderDTO dto, String representative) throws InboundOrderNotFoundException, InternalServerErrorException;
}
