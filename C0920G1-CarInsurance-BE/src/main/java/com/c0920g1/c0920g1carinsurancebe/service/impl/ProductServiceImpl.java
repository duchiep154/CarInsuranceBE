package com.c0920g1.c0920g1carinsurancebe.service.impl;

import com.c0920g1.c0920g1carinsurancebe.DTO.ProductUpdateDTO;
import com.c0920g1.c0920g1carinsurancebe.entities.product.Product;
import com.c0920g1.c0920g1carinsurancebe.repository.ProductRepository;
import com.c0920g1.c0920g1carinsurancebe.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    //    Hieu - 2021-03-15 - 16h43
    @Override
    public Page<Product> getAllProductByQuery(Pageable pageable) {
        return productRepository.getAllProductByQuery(pageable);
    }

    //    Code cua THINH
    @Override
    public Page<Product> getAllProductByStatusTrue(Pageable pageable) {
        return productRepository.getAllProductByStatusTrue(pageable);
    }

    //   Code của THINH
    @Override
    public Page<Product> getAllProductByStatusFalse(Pageable pageable) {
        return productRepository.getAllProductByStatusFalse(pageable);
    }

    //   Code của THINH
    @Override
    public Page<Product> findBySearchFalse(Pageable pageable, String searchFalse) {
        return productRepository.findBySearchFalse(pageable,searchFalse);
    }

// ____ + ____ + ____ + ____ + ____ + ____ + ____ + ____ + ____

    // Hieu - 2021-03-16
    // Tính năng: Xóa đối tượng không có trong bảng hợp đồng
    // Các trường hợp xảy ra là
    //      + list rỗng là các sản phẩm đều được sử dụng trong hợp đồng nên không được xóa
    //      + Id cần xét có trong danh sách product mà "chưa có hợp đồng nào sử dụng" trả về true là xóa được
    @Override
    public boolean deleteProducIdNotInContract(Long idProduct) {
        List<Long> idProducts = productRepository.findAllProductIdNotInContract();
        if (idProducts.isEmpty()) {
            return false;
        }else if(idProducts.indexOf(idProduct) == -1){
            return false;
        }else {
            productRepository.deleteProductById(idProduct);
            return true;
        }
    }

    // Hieu 2020-03-20
    // Tính năng: Lấy danh sách id product không có nằm trong hợp đồng
    //   Code của THINH
    @Override
    public Page<Product> findBySearchTrue(Pageable pageable, String searchTrue) {
        return productRepository.findBySearchTrue(pageable, searchTrue);
    }


    @Override
    public List<Product> getAllProduct() {
        return productRepository.getAllProduct();
    }

    // hieu 2020-03-20
    // tính năng: Lấy danh sách id product không có nằm trong hợp đồng
    @Override
    public List<Long> getListProductIdNotInContract() {
        return productRepository.findAllProductIdNotInContract();
    }

    // Hieu - 2021- 03-20
    // Tính năng change status product
    @Override
    public boolean changeStatusProduct(Long productId) {
        Optional<Product> productOptional = productRepository.findProductById(productId);
        if(productOptional.isPresent()){
            if("true".equals( productOptional.get().getStatusProduct())){
                productRepository.updateStatusProduct("false", productId);
            }else if("false".equals(productOptional.get().getStatusProduct())){
                productRepository.updateStatusProduct("true", productId);
            }
            return true;
        }else {
            return false;
        }
    }

//    hieu - 2021-03-18
//    tính năng: update sản phẩm trong database
    @Override
    public boolean updateProductByQuery(Product product) {
        Optional<Product> existingProduct = productRepository.findProductById(product.getId());
        if (existingProduct.isPresent()) {
            productRepository.updateProductByQuery(
                    product.getName(),
                    product.getPersonNumber(),
                    product.getStatusProduct(),
                    product.getProductPrice(),
                    product.getCarType(),
                    product.getProductType(),
                    product.getId()
            );
            return true;
        } else {
            return false;
        }
    }

//    Hieu - 2021-03-15 - 16h43
//    chuc nang tim doi tuong product bang id, dung native query trong repository
    @Override
    public Optional<Product> findById(Long idProduct) {
        return productRepository.findProductById(idProduct);
    }

//    Hieu - 2021-03-24
//    Tính năng: Trả lại List<ProductDTO>
    @Override
    public List<ProductUpdateDTO> getProductIdAndName() {
        List<Product> products = productRepository.getProductIdAndName();
        List<ProductUpdateDTO> productUpdateDTOs = new ArrayList<>();
        products.forEach(product -> productUpdateDTOs.add(
                new ProductUpdateDTO( product.getId()
                        , product.getName()
                        , product.getPersonNumber()
                        , product.getStatusProduct()
                        , product.getProductPrice()
                        , product.getCarType()
                        , product.getProductType()
                        , product.getContractSet()
                )
        ));
        return productUpdateDTOs;
    }


//    ____ + ____ + ____ + ____ + ____ + ____ + ____ + ____ + ____ + ____ + ____ + ____

    // Hiếu
    // Tính năng: lấy dữ liệu để test với postman bằng các câu lệnh JPA
    @Override
    public Iterable<Product> getAll() {
        return productRepository.findAll();
    }

    // Hiếu
    // Tính năng: Lưu dữ liệu để test với postman bằng các câu lệnh JPA
    @Override
    public void save(Product product) {
        productRepository.createProduct(product.getName(), product.getPersonNumber()
                , product.getStatusProduct(), product.getProductPrice(), product.getCarType().getId(), product.getProductType().getId());
    }

    // Hieu - 2021-03-15 - 16h43
    // Tính năng: Cập nhật dữ liệu để test với postman bằng các câu lệnh JPA
    @Override
    public void update(Product product) {
        productRepository.save(product);
    }

    // Hieu - 2021-03-15 - 16h43
    // Tính năng: Xóa dữ liệu để test với postman bằng các câu lệnh JPA
    @Override
    public boolean remove(Long id) {
        Optional<Product> productTemp = productRepository.findById(id);
        if (productTemp.isPresent()) {
            productRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }




}

