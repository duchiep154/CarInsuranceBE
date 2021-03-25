package com.c0920g1.c0920g1carinsurancebe.entities.product;
import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "product_type")
public class ProductType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "productType", cascade = CascadeType.ALL)
    @JsonBackReference(value = "product_type_product")
    private Set<Product> productTypeSet;

    public ProductType() {
    }

    public Set<Product> getProductTypeSet() {
        return productTypeSet;
    }

    public void setProductTypeSet(Set<Product> productTypeSet) {
        this.productTypeSet = productTypeSet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
