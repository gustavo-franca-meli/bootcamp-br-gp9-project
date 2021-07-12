package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.dtos.request.TransferOrderRequestDTO;
import com.mercadolibre.finalProject.dtos.response.TransferOrderResponseDTO;
import com.mercadolibre.finalProject.exceptions.*;
import com.mercadolibre.finalProject.model.Batch;
import com.mercadolibre.finalProject.model.TransferBatch;
import com.mercadolibre.finalProject.model.TransferOrder;
import com.mercadolibre.finalProject.model.enums.BatchStatus;
import com.mercadolibre.finalProject.model.mapper.BatchMapper;
import com.mercadolibre.finalProject.repository.BatchRepository;
import com.mercadolibre.finalProject.repository.TransferBatchRepository;
import com.mercadolibre.finalProject.repository.TransferOrderRepository;
import com.mercadolibre.finalProject.service.IRepresentativeService;
import com.mercadolibre.finalProject.service.IWarehouseService;
import com.mercadolibre.finalProject.service.TransferOrderService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransferOrderServiceImpl  implements TransferOrderService {
    private final IRepresentativeService representativeService;
    private final IWarehouseService warehouseService;
    private final BatchRepository batchRepository;
    private final TransferBatchRepository transferBatchRepository;
    private final TransferOrderRepository transferOrderRepository;

    public TransferOrderServiceImpl(IRepresentativeService representativeService, IWarehouseService warehouseService, BatchRepository batchRepository, TransferBatchRepository transferBatchRepository, TransferOrderRepository transferOrderRepository) {
        this.representativeService = representativeService;
        this.warehouseService = warehouseService;
        this.batchRepository = batchRepository;
        this.transferBatchRepository = transferBatchRepository;
        this.transferOrderRepository = transferOrderRepository;
    }

    @Override
    public TransferOrderResponseDTO create(TransferOrderRequestDTO dto, String username) throws TransferStockException {
        //validate if representative works in warehouse
        var representative  = representativeService.findByAccountUsernameAndWarehouseId(username,dto.getWarehouseId());
        //validade if warehouse to transfer exist
        warehouseService.findById(dto.getToWarehouseId());


        var transferOrderForPersist = new TransferOrder(representative.getId());
        var transferOrder = transferOrderRepository.save(transferOrderForPersist);

        List<TransferBatch> transferBatches = new ArrayList<>();
        List<Batch> batches = new ArrayList<>();
        List<SubError> errorList = new ArrayList<>();
        try {
            dto.getBatchStock().forEach((batchId)->{
            //validate if batch id exist
            try{
                var batch = batchRepository.findByIdAndWarehouseId(batchId.getId(), dto.getWarehouseId());
                if (batch == null) throw new BatchNotFoundException("Batch Not Found in Warehouse");
                if(batch.getStatus() == BatchStatus.IN_TRANSFER)throw new Exception("batch "+ batch.getId() + " already is in transfer");
                batch.setStatus(BatchStatus.IN_TRANSFER);
                transferBatches.add(new TransferBatch(batch, transferOrder));
            }catch (Exception e){
                errorList.add(new TransferBatchException(batchId.getId().toString(),e.getMessage()));

            }

            });
            if(errorList.size() > 0)throw new TransferStockException(errorList);
            //save all transferBatches and Batches
            batches = transferBatches.stream().map(TransferBatch::getBatch).collect(Collectors.toList());
            batchRepository.saveAll(batches);
            transferBatchRepository.saveAll(transferBatches);

            return new TransferOrderResponseDTO(transferOrder.getId(), BatchMapper.toListDTO(batches));

        }catch (Exception e){
            rollbackCreate(transferOrder,batches,transferBatches);
            throw e;
        }
    }

    private void rollbackCreate(TransferOrder transferOrder, List<Batch> batches, List<TransferBatch> transferBatches) {
        if(batches != null){
            var batchesToSave = batches.stream().map((batch)->{
                batch.setStatus(BatchStatus.IN_STOCK);
                return batch;
            }).collect(Collectors.toList());
            batchRepository.saveAll(batchesToSave);
        }
        if(transferBatches != null)
            transferBatches.forEach(transferBatchRepository::delete);
        if(transferOrder != null)
            transferOrderRepository.delete(transferOrder);





    }
}
