package com.korobko.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;
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
    private BigDecimal price;

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
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
