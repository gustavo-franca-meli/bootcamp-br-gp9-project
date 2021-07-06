package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.dtos.BatchDTO;
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
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BatchServiceImpl implements IBatchService {
    private static final LocalDate MINIMUM_DUE_DATE = LocalDate.now().plusWeeks(3l);
    private static final String ORDERED_BY_CURRENT_QUANTITY = "C";

    private final BatchRepository batchRepository;
    private final ISectorService sectorService;
    private final IProductService productService;
    private final IRepresentativeService representativeService;

    public BatchServiceImpl(BatchRepository batchRepository, ISectorService sectorService, IProductService productService, IRepresentativeService representativeService) {
        this.batchRepository = batchRepository;
        this.sectorService = sectorService;
        this.productService = productService;
        this.representativeService = representativeService;
    }

    @Override
    public List<Batch> create(List<BatchDTO> batchStock, Long sectorId) throws CreateBatchStockException {
        //sector has space for batchStock length else throws
        var size = batchStock.size();

        var errorList = new ArrayList<BatchCreateException>();
        var responseBathList = new ArrayList<Batch>();
        //iterate all product if find a error throws all
        batchStock.forEach((batch) -> {
            try {
                var batchModel = BatchMapper.toModel(batch, sectorId);
                //product seller is registered if not throws
                var product = productService.findById(batch.getProductId());
                //product type pertence a sector
                if (sectorService.hasType(sectorId, product.getType())) {
                    sectorService.isThereSpace(batchModel, sectorId);
                    var bathResponse = batchRepository.save(batchModel);
                    responseBathList.add(bathResponse);
                } else {
                    throw new ProductTypeNotSuportedInSectorException(product.getId(), ProductType.toEnum(product.getType()).getDescription(), sectorId);
                }

            } catch (Exception e) {
                errorList.add(new BatchCreateException(batch, e.getMessage()));
            }

        });
        if (errorList.isEmpty()) {
            return responseBathList;
        } else {
            //rollback all
            responseBathList.forEach((batch -> {
                batchRepository.deleteById(batch.getId());
            }));
            throw new CreateBatchStockException("Error in save " + errorList.size() + " bath in sector ", errorList);
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
        if (productType != sectorType)
            throw new IncorrectSectorTypeException("This product doesn't should stay in this sector");
    }

    private List<Batch> findBatchByWarehouseIdAndProductIdAndMinimumDueDateOrderBy(Long warehouseId, Long productId, String ordered) {
        List<Batch> batches = new ArrayList<>();
        if (ordered.equals(ORDERED_BY_CURRENT_QUANTITY)) {
            batches = this.batchRepository.findBatchByWarehouseIdAndProductIdAndMinimumDueDateOrderByCurrentQuantity(warehouseId, productId, MINIMUM_DUE_DATE);
        } else {
            batches = this.batchRepository.findBatchByWarehouseIdAndProductIdAndMinimumDueDateOrderByDueDate(warehouseId, productId, MINIMUM_DUE_DATE);
        }

        this.validateFillBatches(batches, productId);
        return batches;
    }

    private void validateFillBatches(List<Batch> batches, Long productId) {
        if (batches.isEmpty())
            throw new BatchNotFoundException("Doesn't has valid batches with this product. Id product: " + productId);
    }

}
