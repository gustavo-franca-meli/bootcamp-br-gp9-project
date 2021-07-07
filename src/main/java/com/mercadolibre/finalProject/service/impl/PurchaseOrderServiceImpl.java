package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.dtos.request.ProductPurchaseOrderRequestDTO;
import com.mercadolibre.finalProject.dtos.request.PurchaseOrderRequestDTO;
import com.mercadolibre.finalProject.dtos.request.PurchaseOrderUpdateRequestDTO;
import com.mercadolibre.finalProject.dtos.response.ProductBatchesPurchaseOrderResponseDTO;
import com.mercadolibre.finalProject.dtos.response.PurchaseOrderResponseDTO;
import com.mercadolibre.finalProject.exceptions.*;
import com.mercadolibre.finalProject.model.ProductBatchesPurchaseOrder;
import com.mercadolibre.finalProject.model.PurchaseOrder;
import com.mercadolibre.finalProject.model.mapper.PurchaseOrderMapper;
import com.mercadolibre.finalProject.repository.PurchaseOrderRepository;
import com.mercadolibre.finalProject.service.IProductBatchesPurchaseOrderService;
import com.mercadolibre.finalProject.service.IProductService;
import com.mercadolibre.finalProject.service.IPurchaseOrderService;
import com.mercadolibre.finalProject.service.ISessionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseOrderServiceImpl implements IPurchaseOrderService {

    private PurchaseOrderRepository repository;
    private ISessionService sessionService;
    private IProductService productService;
    private IProductBatchesPurchaseOrderService productBatchesPurchaseOrderService;

    public PurchaseOrderServiceImpl(PurchaseOrderRepository repository, ISessionService sessionService, IProductService productService, IProductBatchesPurchaseOrderService productBatchesPurchaseOrderService) {
        this.repository = repository;
        this.sessionService = sessionService;
        this.productService = productService;
        this.productBatchesPurchaseOrderService = productBatchesPurchaseOrderService;
    }

    @Override
    public PurchaseOrderResponseDTO create (PurchaseOrderRequestDTO purchaseOrderRequest, String token) throws WarehouseNotFoundException, ProductNotFoundException {
        String buyerUsername = SessionServiceImpl.getUsername(token);
//        Country country = this.sessionService.findByName(buyerUsername).getCountry();
        Long countryId = 1L;
        LocalDate minimumDueDate = purchaseOrderRequest.getDate().minusWeeks(3);

        if (!this.isStockEnough(purchaseOrderRequest.getProducts(), countryId, minimumDueDate)) {
            throw new RuntimeException(); }

        PurchaseOrder purchaseOrder = new PurchaseOrder(purchaseOrderRequest.getDate(), purchaseOrderRequest.getOrderStatus());
        List<ProductBatchesPurchaseOrderResponseDTO> productBatches = new ArrayList<>();

        for (ProductPurchaseOrderRequestDTO productRequest : purchaseOrderRequest.getProducts()) {
            productBatches.add(this.productBatchesPurchaseOrderService.create(productRequest, purchaseOrder, countryId, minimumDueDate));
        }

        this.repository.save(purchaseOrder);
        return PurchaseOrderMapper.toResponseDTO(purchaseOrder,productBatches);
    }

    public Boolean isStockEnough (List<ProductPurchaseOrderRequestDTO> productRequests, Long countryId, LocalDate date) {
        List<ProductStockInsufficientException> exceptions = new ArrayList<>();

        for(ProductPurchaseOrderRequestDTO productRequestDTO : productRequests) {
            Integer quantityProduct = this.productService.getQuantityOfProductByCountryAndDate(productRequestDTO.getProductId(), countryId, date);

            if (quantityProduct < productRequestDTO.getQuantity()) {
                exceptions.add(new ProductStockInsufficientException(
                        "Stock for product " + productRequestDTO.getProductId() + " is insufficient for order: " + quantityProduct)); }
        }

        if(!exceptions.isEmpty()) { throw new StockInsufficientException("Stock insufficient for order ", exceptions); }
        return true;
    }

    @Override
    public PurchaseOrderResponseDTO update (Long id, List<PurchaseOrderUpdateRequestDTO> updateRequest) {
        PurchaseOrder purchaseOrder = this.findById(id);
        return null;
    }

    private PurchaseOrder findById (Long id) {
        Optional<PurchaseOrder> purchaseOrderOpt = this.repository.findById(id);
        if(purchaseOrderOpt.isEmpty()) { throw new NotFoundException("Purchase Order ID " + id + " invalid"); }
        return purchaseOrderOpt.get();
    }

    @Override
    public PurchaseOrderResponseDTO getById (Long id, String token) throws ProductNotFoundException {
        //token verification

        PurchaseOrder purchaseOrder = this.findById(id);
        List<ProductBatchesPurchaseOrderResponseDTO> productBatches = new ArrayList<>();

        for(ProductBatchesPurchaseOrder productBatch : purchaseOrder.getProducts()) {
            productBatches.add(this.productBatchesPurchaseOrderService.findById(productBatch.getId()));
        }

        return PurchaseOrderMapper.toResponseDTO(purchaseOrder,productBatches);
    }

}
