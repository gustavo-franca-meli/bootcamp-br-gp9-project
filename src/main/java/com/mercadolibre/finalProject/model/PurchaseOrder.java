package com.mercadolibre.finalProject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "purchase_order")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private Account buyer;

    private LocalDate orderDate;

    private Integer orderStatus;

    @OneToMany(mappedBy = "purchaseOrder", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProductBatchesPurchaseOrder> products = new ArrayList<>();

    @OneToOne(mappedBy = "purchaseOrder", fetch = FetchType.LAZY)
    private ReturnOrder returnOrder;

    public PurchaseOrder(Long id) {
        this.id = id;
    }

    public PurchaseOrder(Long id, Account buyer, LocalDate orderDate, Integer orderStatus, List<ProductBatchesPurchaseOrder> products) {
        this.id = id;
        this.buyer = buyer;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.products = products;
    }

    public PurchaseOrder(Account buyer, LocalDate orderDate, Integer orderStatus) {
        this.buyer = buyer;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
    }

    public Double getTotalPrice() {
        return this.getProducts().stream().mapToDouble(ProductBatchesPurchaseOrder::getTotalPrice).sum();
    }
}
