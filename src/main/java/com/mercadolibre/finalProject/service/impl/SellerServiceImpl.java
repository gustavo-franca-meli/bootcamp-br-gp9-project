package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.exceptions.NotFoundException;
import com.mercadolibre.finalProject.exceptions.SellerNotFoundException;
import com.mercadolibre.finalProject.model.Seller;
import com.mercadolibre.finalProject.repository.SellerRepository;
import com.mercadolibre.finalProject.service.ISellerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SellerServiceImpl implements ISellerService {

    SellerRepository sellerRepository;

    public SellerServiceImpl(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    @Override
    public Long createSeller(Seller seller) {
        return sellerRepository.save(seller).getId();
    }

    @Override
    public Seller findSellerById(Long id) {
        var seller = sellerRepository.findById(id);

        if (seller.isEmpty()) {
            throw new SellerNotFoundException();
        }

        return seller.get();
    }

    @Override
    public List<Seller> listAllSellers() {
        List<Seller> result = new ArrayList<>();
        sellerRepository.findAll().forEach(result::add);

        return result;
    }

    @Override
    public Seller updateSeller(Seller seller) {
        return sellerRepository.save(seller);
    }
}
