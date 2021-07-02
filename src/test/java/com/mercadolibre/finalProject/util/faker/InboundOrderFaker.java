package com.mercadolibre.finalProject.util.faker;

import com.mercadolibre.finalProject.dtos.BatchDTO;
import com.mercadolibre.finalProject.dtos.InboundOrderDTO;
import com.mercadolibre.finalProject.dtos.SectionDto;
import com.mercadolibre.finalProject.model.Sector;
import com.mercadolibre.finalProject.model.Warehouse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class InboundOrderFaker {
    public static InboundOrderDTO getValidInboundOrderRequest() {
        var section = new SectionDto(UUID.randomUUID().toString(),UUID.randomUUID().toString());
        var bathList = new ArrayList<BatchDTO>();
        bathList.add(BathRequestFaker.validRequest());
        bathList.add(BathRequestFaker.validRequest());
        bathList.add(BathRequestFaker.validRequest());
        bathList.add(BathRequestFaker.validRequest());
        bathList.add(BathRequestFaker.validRequest());
        return new InboundOrderDTO(10, LocalDate.now(),section,bathList);
    }

    public static Sector getSector(String code) {
        var sector = new Sector();
        sector.setId(code);
        return sector;
    }

    public static Warehouse getValidwarehouse() {
        var warehouse = new Warehouse();
        warehouse.setId(UUID.randomUUID());
        return warehouse;
    }
}
