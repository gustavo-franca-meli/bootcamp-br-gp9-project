package com.mercadolibre.finalProject.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "inbound_order")
@Data
@NoArgsConstructor
public class InboundOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate orderDate;

    @OneToMany(mappedBy = "inboundOrder")
    private List<Batch> batches;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Representative representative;

    public InboundOrder(Long id) {
        this.id = id;
    }

    public InboundOrder(LocalDate orderDate, Long representativeId) {
        this.orderDate = orderDate;
        this.representative = new Representative(representativeId);
    }

    public InboundOrder(Long id, Long representativeId,LocalDate orderDate) {
        this.id = id;
        this.orderDate = orderDate;
        this.representative = new Representative(representativeId);
    }

}
