package com.korobko.models;

import javax.persistence.*;

@Entity
@Table(name = "ORDERS")
public class ProductOrder implements Comparable<ProductOrder> {

    @Id
    @GeneratedValue(generator = "ORDER_GEN")
    @SequenceGenerator(name = "ORDER_GEN", sequenceName = "ORDER_SEQ", allocationSize = 1)
    private Long orderId;
    @ManyToOne
    @JoinColumn(name = "FK_PRODUCT_ID")
    private Product product;
    private Integer amount;
    @ManyToOne
    @JoinColumn(name = "FK_SHOPPING_CART_ID")
    private ShoppingCart shoppingCart;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    @Override
    public int compareTo(ProductOrder o) {
        return orderId.compareTo(o.getOrderId());
    }
}
