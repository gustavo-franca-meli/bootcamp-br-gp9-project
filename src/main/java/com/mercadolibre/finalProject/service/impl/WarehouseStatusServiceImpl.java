package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.dtos.ProductStatusWarehouseDTO;
import com.mercadolibre.finalProject.dtos.response.AccountResponseDTO;
import com.mercadolibre.finalProject.dtos.response.ProductStatusIntervalDueDateResponseDTO;
import com.mercadolibre.finalProject.dtos.response.ProductStatusWarehouseResponseDTO;
import com.mercadolibre.finalProject.dtos.response.ProductWarningStatusWarehouseResponseDTO;
import com.mercadolibre.finalProject.exceptions.NoProductsFoundException;
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

    public WarehouseStatusServiceImpl(WarehouseRepository repository, IAccountService accountService) {
        this.repository = repository;
        this.accountService = accountService;
    }

    @Override
    public List<ProductStatusWarehouseResponseDTO> getWarehouseStatus(String username) {
        return this.getProductStatuses(this.getRepresentativeIdLoggedIn(username));
    }

    @Override
    public List<ProductWarningStatusWarehouseResponseDTO> getWarehouseWarningProducts(String username) {
        return this.getWarningProductsStatuses(this.getRepresentativeIdLoggedIn(username));
    }

    private Long getRepresentativeIdLoggedIn (String username) {
        AccountResponseDTO account = this.accountService.getAccountByUsername(username);

        if(account.getRol()!=1) {
            throw new NotARepresentativeException("User logged in needs to be a representative for request.");
        }

        return account.getId();
    }

    private List<ProductStatusWarehouseResponseDTO> getProductStatuses (Long representativeId) {
        LocalDate dateNow = LocalDate.now();
        LocalDate date1 = dateNow.plusWeeks(3);
        LocalDate date2 = dateNow.plusMonths(2);

        List<WarehouseRepository.IProductStatusWarehouseDTO> productStatuses = this.repository.getWarehouseStatus(representativeId,date1,date2);

        if(productStatuses.isEmpty()) { throw new NoProductsFoundException("No products found in the warehouse."); }

        List<ProductStatusWarehouseResponseDTO> productStatusesResponse = new ArrayList<>();

        productStatuses.forEach(p -> productStatusesResponse.add(new ProductStatusWarehouseResponseDTO(
                Long.valueOf(p.getProduct_id()),
                new ProductStatusIntervalDueDateResponseDTO(Integer.valueOf(p.getCount_batches_less_3_weeks()),Integer.valueOf(p.getQuantity_less_3_weeks())),
                new ProductStatusIntervalDueDateResponseDTO(Integer.valueOf(p.getCount_batches_more_3_weeks()),Integer.valueOf(p.getQuantity_more_3_weeks())),
                new ProductStatusIntervalDueDateResponseDTO(Integer.valueOf(p.getCount_batches_more_2_months()),Integer.valueOf(p.getQuantity_more_2_months())))));

        return productStatusesResponse;
    }

    private List<ProductWarningStatusWarehouseResponseDTO> getWarningProductsStatuses (Long representativeId) {
        LocalDate dateNow = LocalDate.now();
        LocalDate date1 = dateNow.plusWeeks(3);

        List<WarehouseRepository.IProductWarningStatusWarehouseDTO> productStatuses = this.repository.getWarehouseWarningStatus(representativeId,date1);

        if(productStatuses.isEmpty()) { throw new NoProductsFoundException("No products to expire in less than 3 weeks found in the warehouse."); }

        List<ProductWarningStatusWarehouseResponseDTO> productStatusesResponse = new ArrayList<>();

        productStatuses.forEach(p -> productStatusesResponse.add(new ProductWarningStatusWarehouseResponseDTO(
                Long.valueOf(p.getProduct_id()),
                new ProductStatusIntervalDueDateResponseDTO(Integer.valueOf(p.getCount_batches_less_3_weeks()),Integer.valueOf(p.getQuantity_less_3_weeks())))));

        return productStatusesResponse;
    }
}
