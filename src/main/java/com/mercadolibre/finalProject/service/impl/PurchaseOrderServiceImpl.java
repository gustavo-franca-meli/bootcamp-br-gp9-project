package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.dtos.StockForOrderDTO;
import com.mercadolibre.finalProject.dtos.request.ProductPurchaseOrderRequestDTO;
import com.mercadolibre.finalProject.dtos.request.PurchaseOrderRequestDTO;
import com.mercadolibre.finalProject.dtos.request.PurchaseOrderUpdateRequestDTO;
import com.mercadolibre.finalProject.dtos.response.ProductBatchesPurchaseOrderResponseDTO;
import com.mercadolibre.finalProject.dtos.response.PurchaseOrderResponseDTO;
import com.mercadolibre.finalProject.exceptions.ProductNotFoundException;
import com.mercadolibre.finalProject.exceptions.WarehouseNotFoundException;
import com.mercadolibre.finalProject.model.PurchaseOrder;
import com.mercadolibre.finalProject.repository.PurchaseOrderRepository;
import com.mercadolibre.finalProject.service.IProductBatchesPurchaseOrderService;
import com.mercadolibre.finalProject.service.IProductService;
import com.mercadolibre.finalProject.service.IPurchaseOrderService;
import com.mercadolibre.finalProject.service.ISessionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

        List<StockForOrderDTO> stocksForOrder = this.getStocksForOrder(purchaseOrderRequest,countryId);

        if (!this.isStockEnough(stocksForOrder)) { throw new RuntimeException(); }

        PurchaseOrder purchaseOrder = new PurchaseOrder(purchaseOrderRequest.getDate(), purchaseOrderRequest.getOrderStatus());
        List<ProductBatchesPurchaseOrderResponseDTO> productBatches = new ArrayList<>();

        for (StockForOrderDTO stockForOrder : stocksForOrder) {
            productBatches.add(
                    this.productBatchesPurchaseOrderService.create(
                            stockForOrder.getOrderQuantity(),
                            stockForOrder.getProductStock(),
                            purchaseOrder));
        }

        this.repository.save(purchaseOrder);
        return new PurchaseOrderResponseDTO(purchaseOrderRequest.getDate(), purchaseOrderRequest.getOrderStatus(), productBatches);
    }

    public List<StockForOrderDTO> getStocksForOrder (PurchaseOrderRequestDTO purchaseOrderRequest, Long countryId) throws ProductNotFoundException {
        List<StockForOrderDTO> stocksForOrder = new ArrayList<>();
        LocalDate date = purchaseOrderRequest.getDate();

        for(ProductPurchaseOrderRequestDTO productRequest : purchaseOrderRequest.getProducts()) {
            stocksForOrder.add(new StockForOrderDTO(
                    productRequest.getQuantity(),
                    this.productService.getStockForProductInCountryByData(productRequest.getProductId(),countryId,date)));
        }

        return stocksForOrder;
    }

    public Boolean isStockEnough (List<StockForOrderDTO> stocks) {
        // retornar lista de erros caso falso
        return true; }

    @Override
    public PurchaseOrderResponseDTO update (Long id, List<PurchaseOrderUpdateRequestDTO> updates) {
        return null;
    }

}
