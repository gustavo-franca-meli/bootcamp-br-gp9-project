package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.model.Seller;
import com.mercadolibre.finalProject.repository.SellerRepository;
import com.mercadolibre.finalProject.service.ISellerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SellerServiceImpl implements ISellerService {

    SellerRepository repo;

    public SellerServiceImpl(SellerRepository repo) {
        this.repo = repo;
    }

    @Override
    public Long createSeller(Seller seller) {
        return repo.save(seller).getId();
    }

    @Override
    public Seller findSellerById(Long id) {
        return repo.findById(id).get();
    }

    @Override
    public List<Seller> listAllSellers() {
        List<Seller> result = new ArrayList<>();
        repo.findAll().forEach(result::add);
        return result;
    }

    @Override
    public Seller updateSeller(Seller seller) {
        return repo.save(seller);
    }
}
