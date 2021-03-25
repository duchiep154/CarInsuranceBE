package com.c0920g1.c0920g1carinsurancebe.controller;

import com.c0920g1.c0920g1carinsurancebe.DTO.ProductUpdateDTO;
import com.c0920g1.c0920g1carinsurancebe.entities.employee.Employee;
import com.c0920g1.c0920g1carinsurancebe.entities.product.CarType;
import com.c0920g1.c0920g1carinsurancebe.entities.product.Product;
import com.c0920g1.c0920g1carinsurancebe.entities.product.ProductType;
import com.c0920g1.c0920g1carinsurancebe.service.CarTypeService;
import com.c0920g1.c0920g1carinsurancebe.service.ProductService;
import com.c0920g1.c0920g1carinsurancebe.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/api/employee/product")
public class ProductController {

    @Autowired
    private CarTypeService carTypeService;
    @Autowired
    private ProductTypeService productTypeService;
    @Autowired
    private ProductService productService;

    @GetMapping("")
    public Iterable<Product> getProduct() {
        return this.productService.getAll();
    }

    //code cua thinh 2021-03-15
    @RequestMapping(value = "/page")
    public ResponseEntity<Page<Product>> getProductPage(@PageableDefault(size = 5) Pageable pageable) {
        return new ResponseEntity<>(productService.getAllProductByQuery(pageable), HttpStatus.OK);
    }

    //code cua thinh 2021-03-16
    @GetMapping(value = "/list-status-true")
    public ResponseEntity<Page<Product>> getProductByStatusTruePage(@RequestParam("pageTrue") int pageTrue, @RequestParam("size") int size,
                                                                    @RequestParam("nameSorting") boolean nameSorting) {
        Page<Product> products;
        System.out.println(size);
        if (!nameSorting) {
            System.out.println(size);
            Pageable requestedPage = PageRequest.of(pageTrue, size);
            products = productService.getAllProductByStatusTrue(requestedPage);
        } else {
            Pageable requestedPage = PageRequest.of(pageTrue, size, Sort.by("name"));
            products = productService.getAllProductByStatusTrue(requestedPage);
        }
        System.out.println(products);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // thinh 2021-03-16
    @GetMapping("/list-status-false")
    public ResponseEntity<Page<Product>> getProductByStatusFalsePage(@RequestParam("pageFalse") int pageFalse, @RequestParam("size") int size,
                                                                     @RequestParam("nameSorting") boolean nameSorting) {
        if (!nameSorting) {
            Pageable requestedPage = PageRequest.of(pageFalse, size);
            Page<Product> products;
            products = productService.getAllProductByStatusFalse(requestedPage);
            System.out.println(products);
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            Pageable requestedPage = PageRequest.of(pageFalse, size, Sort.by("name"));
            Page<Product> products;
            products = productService.getAllProductByStatusFalse(requestedPage);
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
    }

    // thinh 2021-03-17
    // Create product -THINH
    @PostMapping(value = "/create")
    public ResponseEntity<Product> save(@Valid @RequestBody Product product) {
        carTypeService.getAll();
        productTypeService.getAll();
        productService.save(product);
        productService.getAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Tim kiếm và list product-THINH
    @GetMapping(value = "/list-status-true", params = {"size", "searchTrue", "pageTrue", "nameSorting"})
    public ResponseEntity<Page<Product>> listProductSearchTrue(
            @RequestParam("searchTrue") String searchTrue, @RequestParam("size") int size,
            @RequestParam("pageTrue") int pageTrue, @RequestParam("nameSorting") boolean nameSorting) {
        try {
            Page<Product> products = productService.findBySearchTrue(PageRequest.of(pageTrue, size), searchTrue);
            if (products.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/list-status-false", params = {"size", "searchFalse", "pageFalse", "nameSorting"})
    public ResponseEntity<Page<Product>> listProductSearchFalse(
            @RequestParam("searchFalse") String searchFalse, @RequestParam("size") int size,
            @RequestParam("pageFalse") int pageFalse, @RequestParam("nameSorting") boolean nameSorting) {
        try {
            Page<Product> products = productService.findBySearchFalse(PageRequest.of(pageFalse, size), searchFalse);
            if (products.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//--------------------------------------------------------------------
//____ + ____ + ____ + ____ + ____ + ____ + ____ + ____ + ____ + ____

    // Hieu - 2021-03-16
    // Tính năng: Xóa sản phẩm không có trong hợp đồng
    @DeleteMapping("/product-not-in-contract/{idProduct}")
    public ResponseEntity<Void> removeProductNotInContract(@PathVariable(value = "idProduct") Long idProduct) {
        try {
            if (productService.deleteProducIdNotInContract(idProduct)) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Hieu 2021-03-17 15h25
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    // Hieu - 2021-03-16
    // Tính năng: Cập nhật dữ liệu cho sản phẩm từ id, và dữ liệu json gởi xuống BE
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Void> updateProductByQuery(@Valid @RequestBody Product product, @PathVariable(value = "id") Long id) {
        System.out.println("product = \n" + product.getName());
        try {
            if (!product.getId().equals(id)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else if (productService.updateProductByQuery(product)) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //  Hieu - 2021-03-16
    //  Tính năng: Thay đổi status sản phẩm
    @GetMapping(value = "/change-status/{id}")
    public ResponseEntity<Void> changeStatusProduct(@PathVariable(value = "id") Long id) {
        try {
            if (productService.changeStatusProduct(id)) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Hieu
    // Tính năng: Lấy sản phẩm từ database bằng id từ phía trình duyệt client gởi về
    @GetMapping("/search-product-by-id/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable(value = "id") Long id) {
        try {
            Optional<Product> productOptional = productService.findById(id);
            if (productOptional.isPresent()) {
                return new ResponseEntity<>(productOptional.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Hieu
    // Lấy danh sách id sản phẩm không có trong hợp đồng
    @GetMapping("/list-product-not-in-contract")
    public ResponseEntity<List<Long>> getListProductNotInContract() {
        try {
            return new ResponseEntity<>(productService.getListProductIdNotInContract(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //  Hieu 2021-03-17
    //  Tính năng: Lấy danh sách car type trong database
    @GetMapping(value = "/car-type")
    public ResponseEntity<List<CarType>> getCarTypeList() {
        try {
            return new ResponseEntity<>(carTypeService.getCarTypeList(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //  Hieu 2021-03-17
    //  Tính năng: lay danh sach product type trong database
    @GetMapping(value = "/product-type")
    public ResponseEntity<List<ProductType>> getProductTypeList() {
        try {
            return new ResponseEntity<>(productTypeService.getProductTypeList(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //  Hieu 2021-03-24
    //  Tính năng: Trả lại List<ProductDTO> với 2 thuộc tính id và name
    @GetMapping(value = "/product-id-name")
    public ResponseEntity<List<ProductUpdateDTO>> getProductIdAndName() {
        try {
            System.out.println("vao duoc controller");
            return new ResponseEntity<>(productService.getProductIdAndName(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




//____ + ____ + ____ + ____ + ____+ ____ + ____ + ____
}


