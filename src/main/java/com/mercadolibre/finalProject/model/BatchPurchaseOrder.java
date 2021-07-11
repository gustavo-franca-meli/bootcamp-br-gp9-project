package com.mercadolibre.finalProject.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "batch_purchase_order")
@Data
@NoArgsConstructor
public class BatchPurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "batch_id")
    private Batch batch;

    @ManyToOne
    @JoinColumn(name = "purchase_batch_order_id")
    private ProductBatchesPurchaseOrder productBatchPurchaseOrder;

    public BatchPurchaseOrder(Integer quantity, Batch batch, ProductBatchesPurchaseOrder productBatchPurchaseOrder) {
        this.quantity = quantity;
        this.batch = batch;
        this.productBatchPurchaseOrder = productBatchPurchaseOrder;
    }

    public BatchPurchaseOrder(Integer quantity, Batch batch) {
        this.quantity = quantity;
        this.batch = batch;
    }

    public Long getWarehouseId() {
        return this.batch.getSector().getWareHouseId();
    }
}
