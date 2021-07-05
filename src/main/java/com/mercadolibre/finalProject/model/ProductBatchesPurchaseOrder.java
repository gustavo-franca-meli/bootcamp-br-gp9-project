package com.mercadolibre.finalProject.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product_batch_purchase_order")
@Data
@NoArgsConstructor
public class ProductBatchesPurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double currentPricePerUnit;

    @OneToMany(mappedBy = "productBatchPurchaseOrder")
    private List<BatchPurchaseOrder> purchaseBatches = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "purchase_order_id")
    private PurchaseOrder purchaseOrder;

    public ProductBatchesPurchaseOrder(Double currentPricePerUnit, List<BatchPurchaseOrder> purchaseBatches, PurchaseOrder purchaseOrder) {
        this.currentPricePerUnit = currentPricePerUnit;
        this.purchaseBatches = purchaseBatches;
        this.purchaseOrder = purchaseOrder;
    }
}
