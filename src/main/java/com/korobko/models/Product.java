package com.korobko.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PRODUCTS")
public class Product {

    @Id
    @GeneratedValue(generator = "PRODUCT_GEN")
    @SequenceGenerator(name = "PRODUCT_GEN", sequenceName = "PRODUCT_SEQ", allocationSize = 1)
    private Long productId;
    private String productName;
    private Double price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductOrder> orders = new ArrayList<>();
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_CATEGORY_ID")
    private Category category;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<ProductOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<ProductOrder> orders) {
        this.orders = orders;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
