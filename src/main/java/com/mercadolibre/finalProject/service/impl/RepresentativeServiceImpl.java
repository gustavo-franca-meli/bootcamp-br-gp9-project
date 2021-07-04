package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.dtos.response.RepresentativeResponseDTO;
import com.mercadolibre.finalProject.exceptions.RepresentativeNotFound;
import com.mercadolibre.finalProject.model.Representative;
import com.mercadolibre.finalProject.model.Warehouse;
import com.mercadolibre.finalProject.model.mapper.RepresentativeMapper;
import com.mercadolibre.finalProject.repository.RepresentativeRepository;
import com.mercadolibre.finalProject.service.IRepresentativeService;
import org.springframework.stereotype.Service;

@Service
public class RepresentativeServiceImpl implements IRepresentativeService {

    private final RepresentativeRepository repository;

    public RepresentativeServiceImpl(RepresentativeRepository repository) {
        this.repository = repository;
    }

    @Override
    public RepresentativeResponseDTO findById(Long representativeId) {
        var representative = this.findBy(representativeId);
        return RepresentativeMapper.toResponseDTO(representative);
    }

    private Representative findBy(Long representativeId) {
        var representative = repository.findById(representativeId);

        return representative.orElseThrow(() -> new RepresentativeNotFound("The representative doesn't exist. Id: " + representativeId));
    }

    @Override
    public RepresentativeResponseDTO findByIdAndWarehouseId(Long representativeId, Long warehouseId) throws RepresentativeNotFound {
        var representative = this.findBy(representativeId, warehouseId);
        return RepresentativeMapper.toResponseDTO(representative);
    }

    private Representative findBy(Long representativeId, Long warehouseId) {
        var representative = repository.findByIdAndWarehouseId(representativeId, warehouseId);

        return representative.orElseThrow(() -> new RepresentativeNotFound("The representative doesn't work in this warehouse. Id: " + representativeId));
    }
}
