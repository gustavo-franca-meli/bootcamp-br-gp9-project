package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.exceptions.RepresentativeNotFound;
import com.mercadolibre.finalProject.model.Representative;
import com.mercadolibre.finalProject.model.Warehouse;

public interface IRepresentativeService {
    boolean worksIn(Warehouse warehouse);

    Representative findById(String representation);

    Representative findByIdAndWarehouse(String representativeId, Warehouse warehouse) throws RepresentativeNotFound;
}
