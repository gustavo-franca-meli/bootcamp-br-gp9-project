package com.mercadolibre.finalProject.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "transfer_order")
@Data
@NoArgsConstructor
public class TransferOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate orderDate;

    @OneToMany(mappedBy = "order")
    private List<TransferBatch> batches;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Representative representative;

    public TransferOrder(Long representativeId) {
        this.representative = new Representative(representativeId);
        this.orderDate = LocalDate.now();
    }

}