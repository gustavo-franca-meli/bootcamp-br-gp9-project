package com.mercadolibre.finalProject.model;

import com.mercadolibre.finalProject.model.enums.BatchStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "transfer_batch")
@Data
@NoArgsConstructor
public class TransferBatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Batch batch;
    @ManyToOne
    private TransferOrder order;

    public TransferBatch(Batch batch, TransferOrder order) {
        this.batch = batch;
        this.order = order;
    }
}
