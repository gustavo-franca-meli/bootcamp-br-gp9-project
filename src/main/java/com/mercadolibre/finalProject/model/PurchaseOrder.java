package com.mercadolibre.finalProject.model;

import com.mercadolibre.finalProject.model.enums.OrderStatus;
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
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate orderDate;

    private Integer orderStatus;

    @OneToMany(mappedBy = "purchaseOrder")
    private List<ProductBatchesPurchaseOrder> products = new ArrayList<>();

    public PurchaseOrder(LocalDate orderDate, Integer orderStatus, List<ProductBatchesPurchaseOrder> products) {
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.products = products;
    }

    public PurchaseOrder(LocalDate orderDate, Integer orderStatus) {
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
    }
}
