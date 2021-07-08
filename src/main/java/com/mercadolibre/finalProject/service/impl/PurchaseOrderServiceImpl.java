package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.dtos.BatchDTO;
import com.mercadolibre.finalProject.dtos.ProductStockDTO;
import com.mercadolibre.finalProject.dtos.request.ProductPurchaseOrderRequestDTO;
import com.mercadolibre.finalProject.dtos.request.PurchaseOrderRequestDTO;
import com.mercadolibre.finalProject.dtos.request.PurchaseOrderUpdateRequestDTO;
import com.mercadolibre.finalProject.dtos.response.ProductResponseDTO;
import com.mercadolibre.finalProject.dtos.response.PurchaseOrderResponseDTO;
import com.mercadolibre.finalProject.exceptions.*;
import com.mercadolibre.finalProject.model.*;
import com.mercadolibre.finalProject.model.mapper.BatchPurchaseOrderMapper;
import com.mercadolibre.finalProject.model.mapper.PurchaseOrderMapper;
import com.mercadolibre.finalProject.repository.*;
import com.mercadolibre.finalProject.service.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseOrderServiceImpl implements IPurchaseOrderService {

    private PurchaseOrderRepository repository;
    private AccountRepository accountRepository;
    private ProductRepository productRepository;
    private BatchRepository batchRepository;
    private IBatchService batchService;
    private ISessionService sessionService;
    private IProductService productService;

    public PurchaseOrderServiceImpl(PurchaseOrderRepository repository, AccountRepository accountRepository, ProductRepository productRepository, BatchRepository batchRepository, IBatchService batchService, ISessionService sessionService, IProductService productService) {
        this.repository = repository;
        this.accountRepository = accountRepository;
        this.productRepository = productRepository;
        this.batchRepository = batchRepository;
        this.batchService = batchService;
        this.sessionService = sessionService;
        this.productService = productService;
    }

    @Override
    public PurchaseOrderResponseDTO create (PurchaseOrderRequestDTO purchaseOrderRequest) throws WarehouseNotFoundException, ProductNotFoundException {

//        Account buyer = this.findAccountByToken(token);
//        if(!this.verifyAccount(buyer,purchaseOrderRequest.getBuyerId())) { throw new BadRequestException("buyer token wronng etcc");}

        Account buyer = this.findAccountById(purchaseOrderRequest.getBuyerId());

        this.isStockEnough(purchaseOrderRequest.getProducts(), buyer.getCountry().getId(), purchaseOrderRequest.getDate().plusWeeks(3));

        PurchaseOrder purchaseOrder = new PurchaseOrder(buyer,purchaseOrderRequest.getDate(), purchaseOrderRequest.getOrderStatus());

        List<ProductBatchesPurchaseOrder> productBatches = this.createProductBatches(purchaseOrderRequest,purchaseOrder);
        purchaseOrder.setProducts(productBatches);
        this.repository.save(purchaseOrder);
        return PurchaseOrderMapper.toResponseDTO(purchaseOrder);
    }

    @Override
    public PurchaseOrderResponseDTO update (PurchaseOrderUpdateRequestDTO updateRequest) throws ProductNotFoundException {
        PurchaseOrder purchaseOrder = this.findById(updateRequest.getPurchaseOrderId());

        ProductBatchesPurchaseOrder productBatches = this.findProductInPurchaseOrderById(purchaseOrder,updateRequest.getProductId());
        if(productBatches == null) {

            ProductResponseDTO productResponseDTO = this.productService.findById(updateRequest.getProductId());

            ProductPurchaseOrderRequestDTO productRequest = new ProductPurchaseOrderRequestDTO(updateRequest.getProductId(), updateRequest.getNewQuantity());
            Boolean isStockForProductEnough = this.isStockForProductEnough(productRequest, purchaseOrder.getBuyer().getCountry().getId(), purchaseOrder.getOrderDate().plusWeeks(3));

            ProductBatchesPurchaseOrder newProductBatches = this.createProductBatch(productRequest,purchaseOrder);
            purchaseOrder.getProducts().add(newProductBatches);
        }

        else if(productBatches.getTotalQuantity() > updateRequest.getNewQuantity()) {
            this.downsizeOrder(productBatches,productBatches.getTotalQuantity() - updateRequest.getNewQuantity());
        }

        else if (productBatches.getTotalQuantity() < updateRequest.getNewQuantity()) {
            ProductPurchaseOrderRequestDTO productRequest = new ProductPurchaseOrderRequestDTO(updateRequest.getProductId(),updateRequest.getNewQuantity() - productBatches.getTotalQuantity());
            Boolean isStockForProductEnough = this.isStockForProductEnough(productRequest, purchaseOrder.getBuyer().getCountry().getId(), purchaseOrder.getOrderDate().plusWeeks(3));

            this.upsizeOrder(productBatches,updateRequest.getNewQuantity() - productBatches.getTotalQuantity());
        }

        this.repository.save(purchaseOrder);
        return PurchaseOrderMapper.toResponseDTO(purchaseOrder);
    }

    @Override
    public PurchaseOrderResponseDTO getById (Long id) {
        //token verification

        PurchaseOrder purchaseOrder = this.findById(id);
        return PurchaseOrderMapper.toResponseDTO(purchaseOrder);
    }

    private void downsizeOrder(ProductBatchesPurchaseOrder productBatches, Integer quantityToReturn) {
        List<BatchPurchaseOrder> batches = productBatches.getPurchaseBatches(); // sort by due date in batches

        Integer returnedQuantity = 0;
        for(BatchPurchaseOrder batchPurchaseOrder : batches) {

            if(batchPurchaseOrder.getQuantity() >= quantityToReturn - returnedQuantity) {
                this.batchService.returnQuantity(batchPurchaseOrder.getBatch(), quantityToReturn - returnedQuantity);
                batchPurchaseOrder.setQuantity(batchPurchaseOrder.getQuantity() - quantityToReturn + returnedQuantity);
                break;
            }
            else {
                this.batchService.returnQuantity(batchPurchaseOrder.getBatch(), batchPurchaseOrder.getQuantity());
                batchPurchaseOrder.setQuantity(0);
                returnedQuantity += batchPurchaseOrder.getQuantity();
            }
        }
    }

    private void upsizeOrder(ProductBatchesPurchaseOrder productBatches, Integer quantityToIncrease) throws ProductNotFoundException {
        List<BatchPurchaseOrder> batches = productBatches.getPurchaseBatches(); // sort by due date in batches

        Integer withdrawnQuantity = 0;
        ProductStockDTO productStock = this.productService.getStockForProductInCountryByData(productBatches.getProduct().getId(), productBatches.getPurchaseOrder().getBuyer().getCountry().getId(), productBatches.getPurchaseOrder().getOrderDate().plusWeeks(3));

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
        ProductStockDTO productStock = this.productService.getStockForProductInCountryByData(productRequest.getProductId(), countryId, minimumDueDate);

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
        Batch batch = this.batchRepository.findById(batchId).get(); //arrumar isso

        return BatchPurchaseOrderMapper.toModel(batch,quantity,productBatchesPurchaseOrder);
    }

    private void upsizeBatchPurchaseOrder (BatchPurchaseOrder batchPurchaseOrder, Integer quantity) {
        batchPurchaseOrder.setQuantity(batchPurchaseOrder.getQuantity() + quantity);
        BatchDTO batchDTO = this.batchService.withdrawQuantity(batchPurchaseOrder.getBatch().getId(),quantity);
    }

    public Boolean isStockEnough (List<ProductPurchaseOrderRequestDTO> productRequests, Long countryId, LocalDate date) throws ProductNotFoundException {
        List<ProductStockInsufficientException> exceptions = new ArrayList<>();

        for(ProductPurchaseOrderRequestDTO productRequestDTO : productRequests) {
            ProductResponseDTO product = this.productService.findById(productRequestDTO.getProductId());

            Integer quantityProduct = this.productService.getQuantityOfProductByCountryAndDate(productRequestDTO.getProductId(), countryId, date);
            if (quantityProduct < productRequestDTO.getQuantity()) {
                exceptions.add(new ProductStockInsufficientException(
                        "Stock for product " + productRequestDTO.getProductId() + " is insufficient for order: " + quantityProduct)); }
        }

        if(!exceptions.isEmpty()) { throw new StockInsufficientException("Stock insufficient for order ", exceptions); }
        return true;
    }

    public Boolean isStockForProductEnough (ProductPurchaseOrderRequestDTO productRequest, Long countryId, LocalDate date) {
        Integer quantityProduct = this.productService.getQuantityOfProductByCountryAndDate(productRequest.getProductId(), countryId, date);

        if (quantityProduct < productRequest.getQuantity()) {
            throw new ProductStockInsufficientException(
                    "Stock for product " + productRequest.getProductId() + " is insufficient for order: " + quantityProduct); }
        return true;
    }

    private Account findAccountByToken (String token) {
        String username = SessionServiceImpl.getUsername(token);
        Optional<Account> buyerOpt = this.accountRepository.findByUsername(username);
        if(buyerOpt.isEmpty()) { throw new NotFoundException("buyer not found"); }
        return buyerOpt.get();
    }

    private Account findAccountById (Long id) {
        Optional<Account> buyerOpt = this.accountRepository.findById(id);
        if(buyerOpt.isEmpty()) { throw new NotFoundException("buyer not found"); }
        return buyerOpt.get();
    }

    private Boolean verifyAccount (Account buyer, Long buyerId) {
        return buyer.getId().equals(buyerId);
    }

    private ProductBatchesPurchaseOrder findProductInPurchaseOrderById (PurchaseOrder purchaseOrder, Long productId) {
        Optional<ProductBatchesPurchaseOrder> productBatchesOpt = purchaseOrder.getProducts().stream().filter(p -> p.getProduct().getId().equals(productId)).findFirst();
        if(productBatchesOpt.isEmpty()) { return null; }
        return productBatchesOpt.get();
    }

    private PurchaseOrder findById (Long id) {
        Optional<PurchaseOrder> purchaseOrderOpt = this.repository.findById(id);
        if(purchaseOrderOpt.isEmpty()) { throw new NotFoundException("Purchase Order ID " + id + " invalid"); }
        return purchaseOrderOpt.get();
    }

}
