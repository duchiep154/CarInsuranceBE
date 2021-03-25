package com.c0920g1.c0920g1carinsurancebe.service.impl;

import com.c0920g1.c0920g1carinsurancebe.entities.product.ProductType;
import com.c0920g1.c0920g1carinsurancebe.repository.ProductTypeRepository;
import com.c0920g1.c0920g1carinsurancebe.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductTypeServiceImpl implements ProductTypeService {
    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Override
    public Iterable<ProductType> getAll() {
        return productTypeRepository.findAll();
    }

    @Override
    public Optional<ProductType> findById(Long id) {
        return null;
    }

    @Override
    public void save(ProductType productType) {
        productTypeRepository.saveAndFlush(productType);
    }

    @Override
    public void update(ProductType productType) {
    }

    @Override
    public boolean remove(Long id) {
        Optional<ProductType> productTypeTemp = productTypeRepository.findById(id);
        if(productTypeTemp.isPresent()){
            productTypeRepository.deleteById(id);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<ProductType> getProductTypeList() {
        return productTypeRepository.getAllProductTypeByQuery();
    }
}

