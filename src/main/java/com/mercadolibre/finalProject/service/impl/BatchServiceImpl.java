package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.dtos.BatchDTO;
import com.mercadolibre.finalProject.dtos.request.SectorBatchRequestDTO;
import com.mercadolibre.finalProject.dtos.response.BatchValidateDateResponseDTO;
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
import java.util.*;

@Service
public class BatchServiceImpl implements IBatchService {
    private static final LocalDate MINIMUM_DUE_DATE = LocalDate.now().plusWeeks(3L);
    private static final Map<String, String> ORDER_BY_FIELDS = new HashMap<>();
    private static final Map<String, ProductType> PRODUCT_CATEGORY_MAPPER = new HashMap<>();

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
        ORDER_BY_FIELDS.put("F", "dueDate");

        this.PRODUCT_CATEGORY_MAPPER.put("FS", ProductType.FRESH);
        this.PRODUCT_CATEGORY_MAPPER.put("RF", ProductType.REFRIGERATED);
        this.PRODUCT_CATEGORY_MAPPER.put("FF", ProductType.FROZEN);
    }

    @Override
    public List<Batch> save(List<BatchDTO> batchStock, Long sectorId, Long orderId) throws CreateBatchStockException {
        var errorList = new ArrayList<BatchCreateException>();
        var batchList = new ArrayList<Batch>();

        //sector has space for batchStock length else throws
        this.sectorService.isThereSpaceFor(batchStock.size(), sectorId);

        //iterate all product if find a error throws all
        Long i = 0L;
        for (var batch : batchStock) {
            try {
                var batchModel = BatchMapper.toModel(batch, sectorId, orderId);
                //product seller is registered if not throws
                var product = productService.findById(batch.getProductId());
                //product type has a sector
                if (sectorService.hasType(sectorId, product.getType())) {
                    //verify if batch exist and has the same order
                    Optional<Batch> findBatch = batchModel.getId() != null ? batchRepository.findById(batchModel.getId()) : Optional.empty();
                    if (findBatch.isPresent() && !findBatch.get().getInboundOrder().getId().equals(orderId))
                        throw new Exception("this batch id already in use in other order");
                    //save batch
                    batchList.add(batchModel);
                } else {
                    throw new ProductTypeNotSuportedInSectorException(product.getId(), product.getTypeDescription(), sectorId);
                }

            } catch (Exception e) {
                errorList.add(new BatchCreateException(i, e.getMessage()));
            }
            i++;

        }
        if (errorList.isEmpty()) {
            var responseBatchList = batchRepository.saveAll(batchList);
            return responseBatchList;
        } else {
            throw new CreateBatchStockException("Error in save " + errorList.size() + " batch in sector", errorList);
        }
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

    @Override
    public Batch findByIdAndWarehouse(Long id, Long warehouseId) {
        return this.batchRepository.findByIdAndWarehouseId(id,warehouseId);
    }

    private Batch findBatchBy(Long batchId) {
        var batch = this.batchRepository.findById(batchId);
        return batch.orElseThrow(() -> new BatchNotFoundException("The batch doesn't exist. Id: " + batchId));
    }

    @Override
    public BatchDTO findById(Long id) {
        return BatchMapper.toDTO(this.findBatchBy(id));
    }

    @Override
    public void saveNewQuantityBatch(Batch batch, Integer quantity) {
        batch.setCurrentQuantity(batch.getCurrentQuantity() + quantity);
        this.batchRepository.save(batch);
    }

    @Override
    public SectorBatchResponseDTO getSectorBatchesByProductId(SectorBatchRequestDTO request) {
        var productResponseDTO = this.productService.findById(request.getProductId());
        var representativeDTO = this.representativeService.findByAccountUsername(request.getUsername());

        List<Batch> batches = null;
        if (request.getOrdered() == null) {
            batches = this.findBatchByWarehouseIdAndProductIdAndMinimumDueDate(representativeDTO.getWarehouseId(), productResponseDTO.getId());
        } else {
            batches = this.findBatchByWarehouseIdAndProductIdAndMinimumDueDateOrderBy(representativeDTO.getWarehouseId(), productResponseDTO.getId(), request.getOrdered());
        }

        this.isCorrectSectorForProducts(productResponseDTO.getType(), batches.get(0).getSectortype());

        return BatchMapper.toSectorBatchResponseDTO(batches);
    }

    @Override
    public List<BatchValidateDateResponseDTO> getBatchesBySectorId(Long sectorId, Integer daysQuantity) {
        if (!this.sectorService.exist(sectorId)) throw new SectorNotFoundException("Sector " + sectorId + " Not Found");

        var batches = this.batchRepository.findBatchesBySectorId(sectorId, LocalDate.now().plusDays(daysQuantity));

        if (batches.isEmpty()) throw new NotFoundException("List is empty");

        return BatchMapper.toListBatchValidateDateResponseDTO(batches);
    }

    @Override
    public List<BatchValidateDateResponseDTO> getBatchesByProductType(Integer daysQuantity, String category, String direction) {
        Sort.Direction sortDirection;
        if (direction != null && direction.toLowerCase(Locale.ROOT).equals("desc")) {
            sortDirection = Sort.Direction.DESC;
        } else {
            sortDirection = Sort.Direction.ASC;
        }

        var productType = PRODUCT_CATEGORY_MAPPER.get(category == null ? "" : category.toUpperCase(Locale.ROOT));
        if (productType == null) throw new BadRequestException("Category cannot be null");

        var batches = findBatchesByProductTypeAndOrderAscAndDesc(productType.getCod(), sortDirection, daysQuantity);

        return BatchMapper.toListBatchValidateDateResponseDTO(batches);
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

    private List<Batch> findBatchesByProductTypeAndOrderAscAndDesc(Integer productTypeCode, Sort.Direction direction, Integer daysQuantity) {
        var sort = Sort.by(direction, "dueDate");

        var batches = this.batchRepository.findBatchesByProductType(productTypeCode, sort, LocalDate.now().plusDays(daysQuantity));

        if (batches.isEmpty()) throw new NotFoundException("List is empty");

        return batches;
    }

    private void validateFillBatches(List<Batch> batches, Long productId) {
        if (batches.isEmpty())
            throw new BatchNotFoundException("Doesn't has valid batches with this product. Id product: " + productId);
    }

}
