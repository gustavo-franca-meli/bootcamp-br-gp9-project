
  package com.mercadolibre.finalProject.dtos;
    import com.fasterxml.jackson.annotation.JsonProperty;
    import lombok.Data;
    import lombok.Getter;
    import lombok.Setter;
    import java.time.LocalDate;
    import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class BatchDTO {
    @JsonProperty("batchNumber")
    private Long id;
    private Long productId;
    private Float currentTemperature;
    private Float minimumTemperature;
    private Integer initialQuantity;
    private Integer currentQuantity;
    private LocalDate manufacturingDate;
    private LocalDateTime manufacturingTime;
    private LocalDate dueDate;

    public BatchDTO() {
    }

    public BatchDTO(Long id, Long productId, Float currentTemperature, Float minimumTemperature, Integer initialQuantity, Integer currentQuantity, LocalDate manufacturingDate, LocalDateTime manufacturingTime, LocalDate dueDate) {
        this.id = id;
        this.productId = productId;
        this.currentTemperature = currentTemperature;
        this.minimumTemperature = minimumTemperature;
        this.initialQuantity = initialQuantity;
        this.currentQuantity = currentQuantity;
        this.manufacturingDate = manufacturingDate;
        this.manufacturingTime = manufacturingTime;
        this.dueDate = dueDate;
    }

    public BatchDTO(Long productId, Float currentTemperature, Float minimumTemperature, Integer initialQuantity, Integer currentQuantity, LocalDate manufacturingDate, LocalDateTime manufacturingTime, LocalDate dueDate) {
        this.productId = productId;
        this.currentTemperature = currentTemperature;
        this.minimumTemperature = minimumTemperature;
        this.initialQuantity = initialQuantity;
        this.currentQuantity = currentQuantity;
        this.manufacturingDate = manufacturingDate;
        this.manufacturingTime = manufacturingTime;
        this.dueDate = dueDate;
    }
}