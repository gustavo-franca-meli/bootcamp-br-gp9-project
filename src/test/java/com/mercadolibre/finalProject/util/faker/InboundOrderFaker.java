package com.mercadolibre.finalProject.util.faker;

import com.mercadolibre.finalProject.dtos.BatchDTO;
import com.mercadolibre.finalProject.dtos.InboundOrderDTO;
import com.mercadolibre.finalProject.dtos.SectorDTO;
import com.mercadolibre.finalProject.model.Sector;
import com.mercadolibre.finalProject.model.Warehouse;

import java.time.LocalDate;
import java.util.ArrayList;

public class InboundOrderFaker {
    static private final SectorDTO validSector = new SectorDTO(10L,10L,10.,100.);
    public static InboundOrderDTO getValidInboundOrderRequest() {

        var bathList = new ArrayList<BatchDTO>();
        bathList.add(BatchRequestFaker.validRequest());
        bathList.add(BatchRequestFaker.validRequest());
        bathList.add(BatchRequestFaker.validRequest());
        bathList.add(BatchRequestFaker.validRequest());
        bathList.add(BatchRequestFaker.validRequest());
        return new InboundOrderDTO(10, LocalDate.now(),validSector,bathList);
    }

    public static Sector getSector(Long code) {
        var sector = new Sector();
        sector.setId(code);
        return sector;
    }

    public static Warehouse getValidWarehouse() {
        var warehouse = new Warehouse();
        warehouse.setId(10L);
        return warehouse;
    }

    public static SectorDTO getValidSectorDTO() {
        return validSector;
    }
}
