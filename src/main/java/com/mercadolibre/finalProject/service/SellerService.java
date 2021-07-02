package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.model.Seller;

import java.util.List;

public interface SellerService {
    Long createSeller(Seller seller);
    Seller findSellerById(Long id);
    List<Seller> listAllSellers();
    Seller updateSeller(Seller seller);
    //TODO Implement status functionality within Seller entity and class
//    String deactivateSeller();
}
