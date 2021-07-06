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
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BatchServiceImpl implements IBatchService {
    private BatchRepository batchRepository;
    private ISectorService  sectorService;
    private IProductService productService;

    public BatchServiceImpl(BatchRepository batchRepository, ISectorService sectorService, IProductService productService) {
        this.batchRepository = batchRepository;
        this.sectorService = sectorService;
        this.productService = productService;
    }

    @Override
    public List<Batch> create(List<BatchDTO> batchStock, Long sectorId) throws CreateBatchStockException {
        //sector has space for batchStock length else throws
        var size = batchStock.size();

        var errorList = new ArrayList<BatchCreateException>();
        var responseBathList = new ArrayList<Batch>();
        //iterate all product if find a error throws all
        batchStock.forEach((batch)->{
            try{
                var batchModel = BatchMapper.toModel(batch,sectorId);
                //product seller is registered if not throws
                var product = productService.findById(batch.getProductId());
                //product type pertence a sector
                if(sectorService.hasType(sectorId,product.getProductTypes())){
                    sectorService.isThereSpace(batchModel,sectorId);
                    var bathResponse = batchRepository.save(batchModel);
                    responseBathList.add(bathResponse);
                }else{
                    throw new ProductTypeNotSuportedInSectorException(product.getId().toString(),product.getProductTypes().toString(),sectorId.toString());
                };


            }catch (Exception e){
                errorList.add(new BatchCreateException(batch,e.getMessage()));
            }

        });
        if(errorList.isEmpty()){
            return responseBathList;
        }else{
            //rollback all
            responseBathList.forEach((batch -> {
                batchRepository.deleteById(batch.getId());
            }));
            throw new CreateBatchStockException("Error in save " + errorList.size() + " bath in sector ",errorList);
        }
        //register all batch in sector if dont works repeat 3 times of fails all throws Internal Server Error.
    }

    public BatchDTO withdrawQuantity (Long batchId, Integer withdrawnQuantity) {
        Batch batch = this.batchRepository.findById(batchId).get(); //arrumar isso
        batch.withdrawQuantity(withdrawnQuantity);
        this.batchRepository.save(batch);
        return BatchMapper.toDTO(batch);
    }

}
