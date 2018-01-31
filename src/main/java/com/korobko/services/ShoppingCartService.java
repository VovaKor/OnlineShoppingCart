package com.korobko.services;

import com.korobko.models.ProductOrder;
import com.korobko.models.ShoppingCart;
import com.korobko.repositories.ProductOrderRepository;
import com.korobko.repositories.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author Vova Korobko
 */
@Service
@Transactional
public class ShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private ProductOrderRepository productOrderRepository;


    public ShoppingCart getShoppingCartById(Long cartId) {
        return shoppingCartRepository.findOne(cartId);
    }


    public ShoppingCart updateShoppingCart(Long cartId, ProductOrder productOrder) {
        ShoppingCart shoppingCart = shoppingCartRepository.getOne(cartId);
        productOrder.setShoppingCart(shoppingCart);
        List<ProductOrder> orders = shoppingCart.getProductOrders();
        if (orders.isEmpty()) {
            orders.add(productOrder);
        } else {
            orders.stream()
                    .sorted()
                    .forEach(o -> {
                        if (o.getProduct().getProductId().longValue() == productOrder.getProduct().getProductId()
                                && productOrder.getAmount() > 0) {
                            o.setAmount(productOrder.getAmount());
                        } else {
                            orders.add(productOrder);
                        }
                    });
        }

        return shoppingCartRepository.saveAndFlush(shoppingCart);
    }

    public void removeProductOrderById(Long orderId) {
        productOrderRepository.delete(orderId);
    }

    public Double getShoppingCartTotalCost(Long cartId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findOne(cartId);
        double sum = 0;
        if (Objects.nonNull(shoppingCart)) {
             sum = shoppingCart.getProductOrders().stream()
                    .mapToDouble(productOrder -> productOrder.getAmount() * productOrder.getProduct().getPrice())
                    .sum();
        }
        return sum;
    }
}
