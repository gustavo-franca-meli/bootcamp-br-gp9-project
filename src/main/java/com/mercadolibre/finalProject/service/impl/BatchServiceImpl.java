package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.dtos.BatchDTO;
import com.mercadolibre.finalProject.dtos.BatchPurchaseOrderDTO;
import com.mercadolibre.finalProject.dtos.request.PurchaseOrderRequestDTO;
import com.mercadolibre.finalProject.dtos.response.BatchPurchaseOrderResponseDTO;
import com.mercadolibre.finalProject.exceptions.BatchCreateException;
import com.mercadolibre.finalProject.exceptions.CreateBatchStockException;
import com.mercadolibre.finalProject.exceptions.ProductTypeNotSuportedInSectorException;
import com.mercadolibre.finalProject.model.Batch;
import com.mercadolibre.finalProject.model.Sector;
import com.mercadolibre.finalProject.model.mapper.BatchMapper;
import com.mercadolibre.finalProject.model.mapper.BatchPurchaseOrderMapper;
import com.mercadolibre.finalProject.repository.BatchRepository;
import com.mercadolibre.finalProject.service.IBatchService;
import com.mercadolibre.finalProject.service.IProductService;
import com.mercadolibre.finalProject.service.ISectorService;
import com.mercadolibre.finalProject.dtos.request.SectorBatchRequestDTO;
import com.mercadolibre.finalProject.dtos.response.SectorBatchResponseDTO;
import com.mercadolibre.finalProject.exceptions.*;
import com.mercadolibre.finalProject.model.Batch;
import com.mercadolibre.finalProject.model.enums.ProductType;
import com.mercadolibre.finalProject.model.mapper.BatchMapper;
import com.mercadolibre.finalProject.repository.BatchRepository;
import com.mercadolibre.finalProject.service.IBatchService;
import com.mercadolibre.finalProject.service.IProductService;
import com.mercadolibre.finalProject.service.IRepresentativeService;
import com.mercadolibre.finalProject.service.ISectorService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class BatchServiceImpl implements IBatchService {
    private static final LocalDate MINIMUM_DUE_DATE = LocalDate.now().plusWeeks(3l);
    private static final Map<String, String> ORDER_BY_FIELDS = new HashMap<>();

    private final BatchRepository batchRepository;
    private final ISectorService sectorService;
    private final IProductService productService;
    private final IRepresentativeService representativeService;

    public BatchServiceImpl(BatchRepository batchRepository, ISectorService sectorService, IProductService productService, IRepresentativeService representativeService) {
        this.batchRepository = batchRepository;
        this.sectorService = sectorService;
        this.productService = productService;
        this.representativeService = representativeService;

        this.ORDER_BY_FIELDS.put("C", "currentQuantity");
        this.ORDER_BY_FIELDS.put("F", "dueDate");
    }


    @Override
    public List<Batch> save(List<BatchDTO> batchStock, Long sectorId,Long orderId) throws CreateBatchStockException {
        //sector has space for batchStock length else throws
        var size = batchStock.size();

        var errorList = new ArrayList<BatchCreateException>();
        var responseBathList = new ArrayList<Batch>();
        //iterate all product if find a error throws all
        Long i = 0L;
        for (var batch: batchStock){
            try{
                var batchModel = BatchMapper.toModel(batch,sectorId,orderId);
                //product seller is registered if not throws
                var product = productService.findById(batch.getProductId());
                //product type pertence a sector
                if(sectorService.hasType(sectorId,product.getProductTypes())){
                    //verify if has space
                    sectorService.isThereSpace(sectorId);
                    //verify if batch exist and has the same order

                    Optional<Batch> findBatch = batchModel.getId() != null?batchRepository.findById(batchModel.getId()): Optional.empty();
                    if(findBatch.isPresent() && !findBatch.get().getInboundOrder().getId().equals(orderId))throw new Exception("this batch id already in use in other order");
                    //save batch
                    var batchResponse = batchRepository.save(batchModel);
                    responseBathList.add(batchResponse);
                }else{
                    throw new ProductTypeNotSuportedInSectorException(product.getId().toString(),product.getProductTypes(),sectorId.toString());
                }


            }catch (Exception e){
                errorList.add(new BatchCreateException(i,e.getMessage()));
            }
            i++;

        }
        if(errorList.isEmpty()){
            return responseBathList;
        }else{
            //rollback all
            responseBathList.forEach((batch -> {
                batchRepository.deleteById(batch.getId());
            }));
            throw new CreateBatchStockException("Error in save " + errorList.size() + " bath in sector",errorList);
        }
        //register all batch in sector if dont works repeat 3 times of fails all throws Internal Server Error.
    }

    @Override
    public BatchDTO withdrawQuantity(Long batchId, Integer withdrawnQuantity) {
        var batch = this.findBatchBy(batchId);
        batch.withdrawQuantity(withdrawnQuantity);
        this.batchRepository.save(batch);
        return BatchMapper.toDTO(batch);
    }

    @Override
    public void deleteAll(List<Batch> batches) {
        batchRepository.deleteAll(batches);
    }
    
    private Batch findBatchBy(Long batchId) {
        var batch = this.batchRepository.findById(batchId);

        return batch.orElseThrow(() -> new BatchNotFoundException("The batch doesn't exist. Id: " + batchId));
    }

    @Override
    public SectorBatchResponseDTO getSectorBatchesByProductId(SectorBatchRequestDTO request) {
        var productResponseDTO = this.productService.findById(request.getProductId());
        var representativeDTO = this.representativeService.findById(request.getRepresentativeId());

        List<Batch> batches = null;
        if (request.getOrdered() == null) {
            batches = this.findBatchByWarehouseIdAndProductIdAndMinimumDueDate(representativeDTO.getWarehouseId(), productResponseDTO.getId());
        } else {
            batches = this.findBatchByWarehouseIdAndProductIdAndMinimumDueDateOrderBy(representativeDTO.getWarehouseId(), productResponseDTO.getId(), request.getOrdered());
        }

        this.isCorrectSectorForProducts(productResponseDTO.getType(), batches.get(0).getSectortype());

        return BatchMapper.toSectorBatchResponseDTO(batches);
    }

    private List<Batch> findBatchByWarehouseIdAndProductIdAndMinimumDueDate(Long warehouseId, Long productId) {
        var batches = this.batchRepository.findBatchByWarehouseIdAndProductIdAndMinimumDueDate(warehouseId, productId, MINIMUM_DUE_DATE);

        this.validateFillBatches(batches, productId);
        return batches;
    }

    private void isCorrectSectorForProducts(Integer productType, Integer sectorType) {
        if (!productType.equals(sectorType))
            throw new IncorrectSectorTypeException("This product doesn't should stay in this sector");
    }

    private List<Batch> findBatchByWarehouseIdAndProductIdAndMinimumDueDateOrderBy(Long warehouseId, Long productId, String ordered) {
        var orderField = ORDER_BY_FIELDS.getOrDefault(ordered, "id");
        var sort = Sort.by(Sort.DEFAULT_DIRECTION, orderField);

        var batches = this.batchRepository.findBatchByWarehouseIdAndProductIdAndMinimumDueDateOrderBySortField(warehouseId, productId, MINIMUM_DUE_DATE, sort);

        this.validateFillBatches(batches, productId);
        return batches;
    }

    private void validateFillBatches(List<Batch> batches, Long productId) {
        if (batches.isEmpty())
            throw new BatchNotFoundException("Doesn't has valid batches with this product. Id product: " + productId);
    }

}
