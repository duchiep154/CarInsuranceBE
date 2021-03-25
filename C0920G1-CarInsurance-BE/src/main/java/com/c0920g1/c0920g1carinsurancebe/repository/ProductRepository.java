package com.c0920g1.c0920g1carinsurancebe.repository;

import com.c0920g1.c0920g1carinsurancebe.DTO.ProductUpdateDTO;
import com.c0920g1.c0920g1carinsurancebe.entities.product.CarType;
import com.c0920g1.c0920g1carinsurancebe.entities.product.Product;
import com.c0920g1.c0920g1carinsurancebe.entities.product.ProductType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT product from Product product")
    Page<Product> getAllProductByQuery(Pageable pageable);

    //    Query lấy data đang tồn tại-THINH
    @Query(value = "SELECT e FROM Product e WHERE e.statusProduct = 'true' ")
    Page<Product> getAllProductByStatusTrue(Pageable pageable);

    //    Query lấy data đang tạm ẩn-THINH
    @Query(value = "SELECT e FROM Product e WHERE e.statusProduct = 'false' ")
    Page<Product> getAllProductByStatusFalse(Pageable pageable);

    //Query search-THINH
    @Query( "SELECT p from Product p where (p.name like %:searchTrue% or p.carType.name like %:searchTrue% or p.productType.name like  %:searchTrue% )and p.statusProduct like 'true' ")
    Page<Product> findBySearchTrue(Pageable pageable, @Param("searchTrue") String searchTrue);

    //Query search-THINH
    @Query( "SELECT p from Product p where (p.name like %:searchFalse% or p.carType.name like %:searchFalse% or p.productType.name like  %:searchFalse%) and p.statusProduct like 'false' ")
    Page<Product> findBySearchFalse(Pageable pageable, @Param("searchFalse") String searchFalse);

    //    Create product-THINH
    @Modifying
    @Query(value =" insert into Product (name,preson_number,status_product,product_price,id_product_for_car_type,id_product_type)"+
            "value (:name,:personNumber,:statusProduct,:productPrice,:idProductForCarType,:idProductType)",nativeQuery = true)
    @Transactional
    void createProduct(@Param("name") String name,@Param("personNumber") String personNumber,@Param("statusProduct") String statusProduct,
                       @Param("productPrice") Double productPrice,@Param("idProductForCarType") Long idProductForCarType,@Param("idProductType") Long idProductType);

//____ + ____ + ____ + ____ + ____ + ____ + ____ + ____ + ____ +

    //    Hieu - 2021-03-15 - 16h43
    @Query("SELECT e FROM Product e WHERE e.id = ?1")
    Optional<Product> findProductById(Long idProduct);

    // Hieu - 2021-03-16
    // Tính năng: tim danh sach id cac san pham chua duoc su dung
    @Query("SELECT product.id FROM Product product " +
            "WHERE product.id NOT IN (SELECT contract.product FROM Contract contract)")
    List<Long> findAllProductIdNotInContract();

    // Hieu - 2021-03-17
    // Tính năng xóa sản phẩm khỏi danh sách
    @Modifying
    @Query("DELETE FROM Product product WHERE product.id = ?1")
    void deleteProductById(Long idProduct);

    // Hieu - 2021-03-16
    // Tính năng update dữ liệu sản phẩm trong database
    @Modifying
    @Query("UPDATE Product product " +
            "SET product.name = ?1, product.personNumber = ?2, product.statusProduct = ?3, " +
            "product.productPrice = ?4, product.carType = ?5, product.productType = ?6 " +
            "WHERE product.id = ?7 ")
    void updateProductByQuery(String name, String personNumber, String statusProduct,
                              Double productPrice, CarType carType, ProductType productType,
                              Long idProduct);

    // Hieu - 2021-03-16
    // Tính năng update dữ liệu sản phẩm trong database
    @Modifying
    @Query("UPDATE Product product " +
            "SET product.statusProduct = ?1 " +
            "WHERE product.id = ?2 ")
    void updateStatusProduct(String statusProduct, Long idProduct);

    // Hieu - 2021-03-24
    // Tính năng: Lấy dữ liệu id và name đưa vào ProductUpdateDTO
    @Query("SELECT product FROM Product product")
    List<Product> getProductIdAndName();







//____ + ____ + ____ + ____ + ____ + ____ + ____ + ____ + ____ +
    //Hưng làm hợp đồng cấm xóa nhá
    @Query("select p from Product p")
    List<Product> getAllProduct();





    // ____ + ____ + ____ + ____ + ____ + ____ + ____ + ____
}



