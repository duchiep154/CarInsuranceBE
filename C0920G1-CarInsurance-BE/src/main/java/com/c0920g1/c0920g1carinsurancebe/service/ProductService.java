package com.c0920g1.c0920g1carinsurancebe.service;
import com.c0920g1.c0920g1carinsurancebe.DTO.ProductUpdateDTO;
import com.c0920g1.c0920g1carinsurancebe.entities.product.Product;
import com.c0920g1.c0920g1carinsurancebe.repository.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ProductService extends Service<Product> {

    Page<Product> getAllProductByQuery(Pageable pageable);

// service lấy dữ liệu -THINH
    Page<Product> getAllProductByStatusTrue(Pageable pageable);

    Page<Product> getAllProductByStatusFalse(Pageable pageable);

// service tìm kiếm -THINH

    Page<Product> findBySearchTrue(Pageable pageable, String searchTrue);

    Page<Product> findBySearchFalse(Pageable pageable, String searchFalse);

//    Hưng làm hợp đồng lấy dữ liệu
    List<Product> getAllProduct();

    // Hieu
    // Tính năng: delete những product  không có trong danh sách hợp đồng
    boolean deleteProducIdNotInContract(Long idProduct);

    // Hieu
    // Tính năng: update product vào database
    boolean updateProductByQuery(Product product);

    // Hieu
    // Tính năng: Lấy danh sách id sản phẩm mà chưa được sử dụng trong hợp đồng nào
    List<Long> getListProductIdNotInContract();

    // Hieu
    // Tính năng: Thay đổi status product trong database từ true thành false và ngược lại
    boolean changeStatusProduct(Long productId);

    // Hieu
    // Tính năng: Trả lại ProductDTO
    List<ProductUpdateDTO> getProductIdAndName();

}



