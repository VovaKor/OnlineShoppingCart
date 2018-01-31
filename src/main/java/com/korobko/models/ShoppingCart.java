package com.korobko.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vova Korobko
 */
@Entity
@Table(name = "SHOPPING_CARTS")
public class ShoppingCart {
    @Id
    @GeneratedValue(generator = "CART_GEN")
    @SequenceGenerator(name = "CART_GEN", sequenceName = "CART_SEQ", allocationSize = 1)
    private Long shoppingCartId;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "shoppingCart")
    private List<ProductOrder> productOrders = new ArrayList<>();

    public Long getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(Long shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }


    public List<ProductOrder> getProductOrders() {
        return productOrders;
    }

    public void setProductOrders(List<ProductOrder> productOrders) {
        this.productOrders = productOrders;
    }
}
