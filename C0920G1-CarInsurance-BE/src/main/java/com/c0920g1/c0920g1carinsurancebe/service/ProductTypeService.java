package com.c0920g1.c0920g1carinsurancebe.service;

import com.c0920g1.c0920g1carinsurancebe.entities.product.ProductType;

import com.c0920g1.c0920g1carinsurancebe.repository.Service;

import java.util.List;


public interface ProductTypeService extends Service<ProductType> {
    List<ProductType> getProductTypeList();
}
