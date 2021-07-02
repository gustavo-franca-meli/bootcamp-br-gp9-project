package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.dtos.BatchDTO;
import com.mercadolibre.finalProject.model.Batch;
import com.mercadolibre.finalProject.model.Sector;
import com.mercadolibre.finalProject.service.IBatchService;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
public class BathServiceImpl implements IBatchService {
    @Override
    public List<Batch> create(@NotNull List<BatchDTO> batchStock, Sector sector) {

        //sector has space for batchStock length else throws

        //iterate all product if find a error throws all

        //product seller is registered if not throws

        //product type pertence a sector


        //register all batch in sector if dont works repeat 3 times of fails all throws Internal Server Error.

        return new ArrayList<>();


    }
}
