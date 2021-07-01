package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.dtos.BatchDto;
import com.mercadolibre.finalProject.model.Batch;
import com.mercadolibre.finalProject.model.Sector;
import com.mercadolibre.finalProject.service.IBathService;
import org.modelmapper.internal.util.Stack;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
public class BathService implements IBathService {
    @Override
    public List<Batch> create(@NotNull List<BatchDto> batchStock, Sector sector) {

        //sector has space for batchStock length else throws

        //iterate all product if find a error throws all

        //product seller is registered if not throws

        //product type pertence a sector


        //register all batch in sector if dont works repeat 3 times of fails all throws Internal Server Error.

        return new ArrayList<>();


    }
}
