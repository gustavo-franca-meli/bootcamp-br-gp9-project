package com.mercadolibre.finalProject.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mercadolibre.finalProject.dtos.request.inboundOrder.InboundOrderCreateRequestDTO;
import com.mercadolibre.finalProject.dtos.request.inboundOrder.InboundOrderUpdateRequestDTO;
import com.mercadolibre.finalProject.model.mapper.BatchMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class InboundOrderDTO {
    @NotNull
    private Long orderNumber;
    @NotNull
    private LocalDate orderDate;
    @NotNull
    private SectorDTO section;
    @NotNull
    private List<BatchDTO> batchStock;

    public InboundOrderDTO(@NotNull Long orderNumber, @NotNull LocalDate orderDate, @NotNull SectorDTO section, @NotNull List<BatchDTO> batchStock) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.section = section;
        this.batchStock = batchStock;
    }

    public InboundOrderDTO() {
    }

    public InboundOrderDTO(InboundOrderCreateRequestDTO dto) {
        this.orderNumber = null;
        this.orderDate = dto.getOrderDate();
        this.section = new SectorDTO(dto.getSection().getCode(),dto.getSection().getWarehouseCode());
        this.batchStock = dto.getBatchStock().stream().map(BatchMapper::toDTO).collect(Collectors.toList());
    }
    public InboundOrderDTO(InboundOrderUpdateRequestDTO dto) {
        this.orderNumber = dto.getOrderNumber();
        this.orderDate = dto.getOrderDate();
        this.section = new SectorDTO(dto.getSection().getCode(),dto.getSection().getWarehouseCode());
        this.batchStock = dto.getBatchStock().stream().map(BatchMapper::toDTO).collect(Collectors.toList());
    }
}