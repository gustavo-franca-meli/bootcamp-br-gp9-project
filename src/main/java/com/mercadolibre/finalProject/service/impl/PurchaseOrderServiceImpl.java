package com.mercadolibre.finalProject.service.impl;

import com.google.common.collect.Lists;
import com.mercadolibre.finalProject.dtos.*;
import com.mercadolibre.finalProject.dtos.request.*;
import com.mercadolibre.finalProject.dtos.response.*;
import com.mercadolibre.finalProject.exceptions.*;
import com.mercadolibre.finalProject.model.*;
import com.mercadolibre.finalProject.model.mapper.*;
import com.mercadolibre.finalProject.repository.*;
import com.mercadolibre.finalProject.service.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class PurchaseOrderServiceImpl implements IPurchaseOrderService {

    private PurchaseOrderRepository repository;
    private ProductRepository productRepository;
    private BatchRepository batchRepository;
    private IBatchService batchService;
    private IProductService productService;
    private AccountRepository accountRepository;

    public PurchaseOrderServiceImpl(PurchaseOrderRepository repository, ProductRepository productRepository, BatchRepository batchRepository, IBatchService batchService, IProductService productService, AccountRepository accountRepository) {
        this.repository = repository;
        this.productRepository = productRepository;
        this.batchRepository = batchRepository;
        this.batchService = batchService;
        this.productService = productService;
        this.accountRepository = accountRepository;
    }

    @Override
    public PurchaseOrderResponseDTO create (PurchaseOrderRequestDTO purchaseOrderRequest, String username) throws WarehouseNotFoundException, ProductNotFoundException, StockInsufficientException {

        Account buyer = this.findValidAccountByUsername(username,purchaseOrderRequest.getBuyerId());

        this.isStockEnough(purchaseOrderRequest.getProducts(), buyer.getCountry().getId(), purchaseOrderRequest.getDate().plusWeeks(3));

        PurchaseOrder purchaseOrder = new PurchaseOrder(buyer,purchaseOrderRequest.getDate(), purchaseOrderRequest.getOrderStatus());

        List<ProductBatchesPurchaseOrder> productBatches = this.createProductBatches(purchaseOrderRequest,purchaseOrder);
        purchaseOrder.setProducts(productBatches);
        this.repository.save(purchaseOrder);
        return PurchaseOrderMapper.toResponseDTO(purchaseOrder);
    }

    @Override
    public PurchaseOrderResponseDTO update (PurchaseOrderUpdateRequestDTO updateRequest) throws ProductNotFoundException, StockInsufficientException {
        PurchaseOrder purchaseOrder = this.findById(updateRequest.getPurchaseOrderId());
        ProductBatchesPurchaseOrder productBatches = this.findProductInPurchaseOrderById(purchaseOrder,updateRequest.getProductId());

        List<ProductPurchaseOrderRequestDTO> productRequests = new ArrayList<>();
        ProductPurchaseOrderRequestDTO productRequest = new ProductPurchaseOrderRequestDTO(updateRequest.getProductId(), updateRequest.getNewQuantity());
        productRequests.add(productRequest);

        if(productBatches == null) {

            this.isStockEnough(productRequests, purchaseOrder.getBuyer().getCountry().getId(), purchaseOrder.getOrderDate().plusWeeks(3));

            ProductBatchesPurchaseOrder newProductBatches = this.createProductBatch(productRequest,purchaseOrder);
            purchaseOrder.getProducts().add(newProductBatches);
        }

        else if(productBatches.getTotalQuantity() > updateRequest.getNewQuantity()) {
            this.downsizeOrder(productBatches,productBatches.getTotalQuantity() - updateRequest.getNewQuantity());
        }

        else if (productBatches.getTotalQuantity() < updateRequest.getNewQuantity()) {
            this.isStockEnough(productRequests, purchaseOrder.getBuyer().getCountry().getId(), purchaseOrder.getOrderDate().plusWeeks(3));
            this.upsizeOrder(productBatches,updateRequest.getNewQuantity() - productBatches.getTotalQuantity());
        }

        this.repository.save(purchaseOrder);
        return PurchaseOrderMapper.toResponseDTO(purchaseOrder);
    }

    @Override
    public PurchaseOrderResponseDTO getById (Long id, String username) {
        PurchaseOrder purchaseOrder = this.findById(id);

        Account account = this.findAccountByUsername(username);
        if(!this.isBuyerIdValid(account,purchaseOrder.getBuyer().getId())) {
            throw new BuyerIdInvalidForRequest("Buyer owner of purchase order id " + purchaseOrder.getId() + " isn't logged in.");
        }
          return PurchaseOrderMapper.toResponseDTO(purchaseOrder);
    }  

    @Override
    public List<PurchaseOrderResponseDTO> getAll(String username) {
        Account account = this.findAccountByUsername(username);

        return PurchaseOrderMapper.toListResponseDTO(this.repository.findByBuyerId(account.getId()));
    }

    private void downsizeOrder (ProductBatchesPurchaseOrder productBatches, Integer quantityToReturn) {
        List<BatchPurchaseOrder> batches = Lists.reverse(productBatches.getPurchaseBatches());

        Integer returnedQuantity = 0;

        for(BatchPurchaseOrder batchPurchaseOrder : batches) {

            if(batchPurchaseOrder.getQuantity() >= quantityToReturn - returnedQuantity) {
                this.batchService.saveNewQuantityBatch(batchPurchaseOrder.getBatch(), quantityToReturn - returnedQuantity);
                batchPurchaseOrder.setQuantity(batchPurchaseOrder.getQuantity() - quantityToReturn + returnedQuantity);
                break;
            }
            else {
                this.batchService.saveNewQuantityBatch(batchPurchaseOrder.getBatch(), batchPurchaseOrder.getQuantity());
                returnedQuantity += batchPurchaseOrder.getQuantity();
                batchPurchaseOrder.setQuantity(0);
            }
        }
    }

    private void upsizeOrder (ProductBatchesPurchaseOrder productBatches, Integer quantityToIncrease) throws ProductNotFoundException {
        List<BatchPurchaseOrder> batches = productBatches.getPurchaseBatches();

        Integer withdrawnQuantity = 0;
        ProductStockDTO productStock = this.productService.getStockForProductInCountryByDate(productBatches.getProduct().getId(), productBatches.getPurchaseOrder().getBuyer().getCountry().getId(), productBatches.getPurchaseOrder().getOrderDate().plusWeeks(3));

        for(BatchDTO batchDTO : productStock.getBatches()) {
            if(withdrawnQuantity >= quantityToIncrease) { break; }

            Integer quantityFromBatch = this.calculateQuantity(batchDTO.getCurrentQuantity(), quantityToIncrease - withdrawnQuantity);
            Optional<BatchPurchaseOrder> batchOpt = batches.stream().filter(b -> b.getBatch().getId().equals(batchDTO.getId())).findFirst();

            if(batchOpt.isEmpty()) {
                batches.add(this.createBatch(batchDTO.getId(), quantityFromBatch, productBatches));
            }
            else {
                BatchPurchaseOrder batchPurchaseOrder = batchOpt.get();
                this.upsizeBatchPurchaseOrder(batchPurchaseOrder,quantityFromBatch);
            }
            withdrawnQuantity += quantityFromBatch;
        }
    }

    private List<ProductBatchesPurchaseOrder> createProductBatches (PurchaseOrderRequestDTO purchaseOrderRequest, PurchaseOrder purchaseOrder) throws ProductNotFoundException {
        List<ProductBatchesPurchaseOrder> productBatches = new ArrayList<>();

        for (ProductPurchaseOrderRequestDTO productRequest : purchaseOrderRequest.getProducts()) {
            productBatches.add(this.createProductBatch(productRequest, purchaseOrder));
        }
        return productBatches;
    }

    private ProductBatchesPurchaseOrder createProductBatch (ProductPurchaseOrderRequestDTO productRequest, PurchaseOrder purchaseOrder) throws ProductNotFoundException {

        Product product = this.productRepository.findById(productRequest.getProductId()).get();

        ProductBatchesPurchaseOrder productBatches = new ProductBatchesPurchaseOrder(product,product.getPrice(), purchaseOrder);
        List<BatchPurchaseOrder> batches = this.createBatches(purchaseOrder,productRequest,productBatches);
        productBatches.setPurchaseBatches(batches);
        return productBatches;
    }

    private List<BatchPurchaseOrder> createBatches (PurchaseOrder purchaseOrder, ProductPurchaseOrderRequestDTO productRequest, ProductBatchesPurchaseOrder productBatchesPurchaseOrder) throws ProductNotFoundException {
        LocalDate minimumDueDate = purchaseOrder.getOrderDate().plusWeeks(3);
        Long countryId = purchaseOrder.getBuyer().getCountry().getId();

        Integer withdrawnQuantity = 0, orderQuantity = productRequest.getQuantity();
        ProductStockDTO productStock = this.productService.getStockForProductInCountryByDate(productRequest.getProductId(), countryId, minimumDueDate);

        List<BatchPurchaseOrder> batchesOrder = new ArrayList<>();
        for(BatchDTO batchDTO : productStock.getBatches()) {
            if(withdrawnQuantity >= orderQuantity) { break; }

            Integer quantityFromBatch = this.calculateQuantity(batchDTO.getCurrentQuantity(),orderQuantity - withdrawnQuantity);
            batchesOrder.add(this.createBatch(batchDTO.getId(),quantityFromBatch,productBatchesPurchaseOrder));
            withdrawnQuantity += quantityFromBatch;
        }
        return batchesOrder;
    }

    private Integer calculateQuantity (Integer batchCurrentQuantity, Integer quantityLeftForOrder) {
        if(batchCurrentQuantity > quantityLeftForOrder) {
            return quantityLeftForOrder;
        }
        return batchCurrentQuantity;
    }

    private BatchPurchaseOrder createBatch (Long batchId, Integer quantity,ProductBatchesPurchaseOrder productBatchesPurchaseOrder) {
        BatchDTO batchDTO = this.batchService.withdrawQuantity(batchId,quantity);
        Batch batch = this.batchRepository.findById(batchId).get();

        return BatchPurchaseOrderMapper.toModel(batch,quantity,productBatchesPurchaseOrder);
    }

    private void upsizeBatchPurchaseOrder (BatchPurchaseOrder batchPurchaseOrder, Integer quantity) {
        batchPurchaseOrder.setQuantity(batchPurchaseOrder.getQuantity() + quantity);
        BatchDTO batchDTO = this.batchService.withdrawQuantity(batchPurchaseOrder.getBatch().getId(),quantity);
    }
  
    public Boolean isStockEnough (List<ProductPurchaseOrderRequestDTO> productRequests, Long countryId, LocalDate date) throws ProductNotFoundException, StockInsufficientException {
        List<ProductStockInsufficientException> exceptions = new ArrayList<>();

        for(ProductPurchaseOrderRequestDTO productRequestDTO : productRequests) {
            ProductResponseDTO product = this.productService.findById(productRequestDTO.getProductId());

            Integer quantityProduct = this.productService.getQuantityOfProductByCountryAndDate(productRequestDTO.getProductId(), countryId, date);
            if (quantityProduct == 0) {
                exceptions.add(new ProductStockInsufficientException(
                        "No stock of product " + productRequestDTO.getProductId() + " in country " + countryId)); }
            else if (quantityProduct < productRequestDTO.getQuantity()) {
                exceptions.add(new ProductStockInsufficientException(
                        "Stock for product " + productRequestDTO.getProductId() + " is insufficient for order. Stock available: " + quantityProduct + " items.")); }
        }

        if(!exceptions.isEmpty()) { throw new StockInsufficientException("Stock insufficient for order ", exceptions); }
        return true;
    }

    private Boolean isBuyerIdValid  (Account account, Long buyerId) {
        return account.getId().equals(buyerId);
    }

    private Account findValidAccountByUsername (String username, Long buyerId) {
        Account account = this.findAccountByUsername(username);

        if(!this.isBuyerIdValid(account,buyerId)) {
            throw new BuyerIdInvalidForRequest ("Buyer Id " + buyerId + " isn't logged in.");}

        return account;
    }

    private Account findAccountByUsername (String username) {
        return this.accountRepository.findByUsername(username).get();
    }

    private ProductBatchesPurchaseOrder findProductInPurchaseOrderById (PurchaseOrder purchaseOrder, Long productId) {
        Optional<ProductBatchesPurchaseOrder> productBatchesOpt = purchaseOrder.getProducts().stream().filter(p -> p.getProduct().getId().equals(productId)).findFirst();
        if(productBatchesOpt.isEmpty()) { return null; }
        return productBatchesOpt.get();
    }

    private PurchaseOrder findById (Long id) {
        Optional<PurchaseOrder> purchaseOrderOpt = this.repository.findById(id);
        if(purchaseOrderOpt.isEmpty()) { throw new PurchaseOrderNotFoundException("Purchase Order ID " + id + " invalid"); }
        return purchaseOrderOpt.get();
    }
}
