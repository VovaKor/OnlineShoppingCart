package com.korobko.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CATEGORIES")
public class Category {
    @Id
    @GeneratedValue(generator = "CATEGORY_GEN")
    @SequenceGenerator(name = "CATEGORY_GEN", sequenceName = "CATEGORY_SEQ", allocationSize = 1)
    private Long categoryId;
    private String name;
    private String description;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "category")
    private List<Product> products = new ArrayList<>();

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
