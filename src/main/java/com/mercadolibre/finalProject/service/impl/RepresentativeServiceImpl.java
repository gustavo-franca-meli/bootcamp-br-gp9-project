package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.exceptions.RepresentativeNotFound;
import com.mercadolibre.finalProject.model.Representative;
import com.mercadolibre.finalProject.model.Warehouse;
import com.mercadolibre.finalProject.service.IRepresentativeService;
import org.springframework.stereotype.Service;

@Service
public class RepresentativeServiceImpl implements IRepresentativeService {
    @Override
    public boolean worksIn(Warehouse warehouse) {
        return false;
    }

    @Override
    public Representative findById(String representation) {
        return null;
    }

    @Override
    public Representative findByIdAndWarehouse(String representativeId, Warehouse warehouse) throws RepresentativeNotFound {
        return null;
    }
}
