package com.mercadolibre.finalProject.unit.service;

import com.mercadolibre.finalProject.exceptions.NotFoundException;
import com.mercadolibre.finalProject.model.ReturnOrder;
import com.mercadolibre.finalProject.repository.ReturnOrderRepository;
import com.mercadolibre.finalProject.service.IPurchaseOrderService;
import com.mercadolibre.finalProject.service.IRepresentativeService;
import com.mercadolibre.finalProject.service.impl.ReturnOrderServiceImpl;
import com.mercadolibre.finalProject.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

public class ReturnOrderServiceImplTest {

    private final ReturnOrderRepository returnOrderRepository = Mockito.mock(ReturnOrderRepository.class);
    private final IPurchaseOrderService purchaseOrderService = Mockito.mock(IPurchaseOrderService.class);
    private final IRepresentativeService representativeService = Mockito.mock(IRepresentativeService.class);
    private ReturnOrderServiceImpl returnOrderService;

    @BeforeEach
    void setUp() {
        this.returnOrderService = new ReturnOrderServiceImpl(returnOrderRepository, purchaseOrderService, representativeService);
    }

    @Test
    void shouldCreateReturnOrder() {
        var request = TestUtils.getReturnOrderRequestDTO();
        String username = "onias-rocha";
        request.setBuyerUsername(username);

        var purchaseOrder = TestUtils.getPurchaseOrderResponseDTO();
        when(purchaseOrderService.getById(Mockito.anyLong(), Mockito.anyString())).thenReturn(purchaseOrder);

        var returnOrder = new ReturnOrder(1L, "", 1, null);
        when(returnOrderRepository.save(Mockito.any())).thenReturn(returnOrder);

        returnOrderService.returnOrder(request);

        verify(purchaseOrderService, times(1)).getById(Mockito.anyLong(), Mockito.anyString());
        verify(returnOrderRepository, times(1)).save(Mockito.any());
    }

    @Test
    void shouldUpdateStatus() {
        var request = TestUtils.getUpdateReturnOrderDTO();
        String username = "onias-rocha";
        request.setUsername(username);

        var returnOrder = TestUtils.getReturnOrder();
        when(returnOrderRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(returnOrder));

        var representative = TestUtils.getRepresentativeResponseDTOValid();
        when(representativeService.findByAccountUsernameAndWarehouseId(Mockito.anyString(), Mockito.anyLong())).thenReturn(representative);

        when(returnOrderRepository.save(Mockito.any())).thenReturn(returnOrder);

        returnOrderService.updateStatus(request);

        verify(representativeService, times(1)).findByAccountUsernameAndWarehouseId(Mockito.anyString(), Mockito.anyLong());
        verify(returnOrderRepository, times(1)).findById(Mockito.anyLong());
        verify(returnOrderRepository, times(1)).save(Mockito.any());
    }

    @Test
    void shouldUpdateStatusAndUpdateStatusOfPurchaseOrder() {
        var request = TestUtils.getUpdateReturnOrderDTO();
        String username = "onias-rocha";
        request.setUsername(username);
        request.setStatusCode(2);
        var returnOrder = TestUtils.getReturnOrder();
        when(returnOrderRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(returnOrder));

        var representative = TestUtils.getRepresentativeResponseDTOValid();
        when(representativeService.findByAccountUsernameAndWarehouseId(Mockito.anyString(), Mockito.anyLong())).thenReturn(representative);

        when(returnOrderRepository.save(Mockito.any())).thenReturn(returnOrder);

        returnOrderService.updateStatus(request);

        verify(representativeService, times(1)).findByAccountUsernameAndWarehouseId(Mockito.anyString(), Mockito.anyLong());
        verify(returnOrderRepository, times(1)).findById(Mockito.anyLong());
        verify(returnOrderRepository, times(1)).save(Mockito.any());
        verify(purchaseOrderService, times(1)).updateStatus(Mockito.any());
    }

    @Test
    void shouldFailUpdateStatus() {
        var request = TestUtils.getUpdateReturnOrderDTO();
        String username = "onias-rocha";
        request.setUsername(username);

        var representative = TestUtils.getRepresentativeResponseDTOValid();
        when(representativeService.findByAccountUsernameAndWarehouseId(Mockito.anyString(), Mockito.anyLong())).thenReturn(representative);

        when(returnOrderRepository.findById(Mockito.anyLong())).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> returnOrderService.updateStatus(request));
    }

}
