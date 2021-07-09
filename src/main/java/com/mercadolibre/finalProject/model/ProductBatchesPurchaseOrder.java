package com.mercadolibre.finalProject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Double currentPricePerUnit;

    @OneToMany(mappedBy = "productBatchPurchaseOrder", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<BatchPurchaseOrder> purchaseBatches = new ArrayList<>();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "purchase_order_id")
    private PurchaseOrder purchaseOrder;

    public ProductBatchesPurchaseOrder(Double currentPricePerUnit, List<BatchPurchaseOrder> purchaseBatches, PurchaseOrder purchaseOrder) {
        this.currentPricePerUnit = currentPricePerUnit;
        this.purchaseBatches = purchaseBatches;
        this.purchaseOrder = purchaseOrder;
    }

    public ProductBatchesPurchaseOrder(Product product, Double currentPricePerUnit, PurchaseOrder purchaseOrder) {
        this.product = product;
        this.currentPricePerUnit = currentPricePerUnit;
        this.purchaseOrder = purchaseOrder;
    }

    public ProductBatchesPurchaseOrder(Product product, Double currentPricePerUnit) {
        this.product = product;
        this.currentPricePerUnit = currentPricePerUnit;
    }

    public Integer getTotalQuantity () {
        return this.getPurchaseBatches().stream().mapToInt(BatchPurchaseOrder::getQuantity).sum();
    }

    public Double getTotalPrice () {
        return this.getTotalQuantity() * this.getCurrentPricePerUnit();
    }
}
