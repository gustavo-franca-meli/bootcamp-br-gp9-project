package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.dtos.ProductStatusWarehouseDTO;
import com.mercadolibre.finalProject.dtos.response.AccountResponseDTO;
import com.mercadolibre.finalProject.dtos.response.ProductStatusIntervalDueDateResponseDTO;
import com.mercadolibre.finalProject.dtos.response.ProductStatusWarehouseResponseDTO;
import com.mercadolibre.finalProject.exceptions.NotARepresentativeException;
import com.mercadolibre.finalProject.model.Warehouse;
import com.mercadolibre.finalProject.repository.WarehouseRepository;
import com.mercadolibre.finalProject.service.IAccountService;
import com.mercadolibre.finalProject.service.IProductService;
import com.mercadolibre.finalProject.service.IWarehouseStatusService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class WarehouseStatusServiceImpl implements IWarehouseStatusService {

    private WarehouseRepository repository;
    private IAccountService accountService;
    private IProductService productService;

    public WarehouseStatusServiceImpl(WarehouseRepository repository, IAccountService accountService, IProductService productService) {
        this.repository = repository;
        this.accountService = accountService;
        this.productService = productService;
    }

    @Override
    public List<ProductStatusWarehouseResponseDTO> getWarehouseStatus(String representativeUsername) {
        AccountResponseDTO account = this.accountService.getAccountByUsername(representativeUsername);

        if(account.getRol()!=1) {
            throw new NotARepresentativeException("User logged in needs to be a representative for request.");
        }
        return this.getProductStatuses(account.getId());
    }

    private List<ProductStatusWarehouseResponseDTO> getProductStatuses (Long representativeId) {
        LocalDate dateNow = LocalDate.now();
        LocalDate date1 = dateNow.plusWeeks(3);
        LocalDate date2 = dateNow.plusMonths(2);

        List<WarehouseRepository.IProductStatusWarehouseDTO> productStatuses = this.repository.getWarehouseStatus(representativeId,date1,date2);

        if(productStatuses.isEmpty()) {return new ArrayList<>();}

        List<ProductStatusWarehouseResponseDTO> productStatusesResponse = new ArrayList<>();

        productStatuses.forEach(p -> productStatusesResponse.add(new ProductStatusWarehouseResponseDTO(
                Long.valueOf(p.getProduct_id()),
                new ProductStatusIntervalDueDateResponseDTO(Integer.valueOf(p.getCount_batches_less_3_weeks()),Integer.valueOf(p.getQuantity_less_3_weeks())),
                new ProductStatusIntervalDueDateResponseDTO(Integer.valueOf(p.getCount_batches_more_3_weeks()),Integer.valueOf(p.getQuantity_more_3_weeks())),
                new ProductStatusIntervalDueDateResponseDTO(Integer.valueOf(p.getCount_batches_more_2_months()),Integer.valueOf(p.getQuantity_more_2_months())))));

        return productStatusesResponse;
    }
}
