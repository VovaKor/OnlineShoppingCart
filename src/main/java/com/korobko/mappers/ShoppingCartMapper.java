package com.korobko.mappers;

import com.korobko.api.CategoryReply;
import com.korobko.api.OrderReply;
import com.korobko.api.ProductReply;
import com.korobko.api.ShoppingCartReply;
import com.korobko.models.Category;
import com.korobko.models.Product;
import com.korobko.models.ProductOrder;
import com.korobko.models.ShoppingCart;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author Vova Korobko
 */
@Component
public class ShoppingCartMapper {
    /**
     * Converts internal JPA model to external REST model
     *
     * @param shoppingCart the {@code ShoppingCart} object
     * @return the {@code ShoppingCartReply} REST object
     */
    public ShoppingCartReply fromInternal(ShoppingCart shoppingCart) {
        ShoppingCartReply shoppingCartReply = new ShoppingCartReply();
        if (Objects.nonNull(shoppingCart)) {
            shoppingCartReply.shoppingCartId = shoppingCart.getShoppingCartId();
            List<ProductOrder> orders = shoppingCart.getProductOrders();
            if (!orders.isEmpty()) {
                orders.forEach(productOrder -> {
                    OrderReply orderReply = new OrderReply();
                    orderReply.orderId = productOrder.getOrderId();
                    orderReply.amount = productOrder.getAmount();

                    Product product = productOrder.getProduct();
                    ProductReply productReply = new ProductReply();
                    productReply.productId = product.getProductId();
                    productReply.productName = product.getProductName();
                    productReply.price = product.getPrice();

                    Category category = product.getCategory();
                    CategoryReply categoryReply = new CategoryReply();
                    categoryReply.categoryId = category.getCategoryId();
                    categoryReply.name = category.getName();
                    categoryReply.description = category.getDescription();

                    productReply.category = categoryReply;
                    orderReply.productReply = productReply;
                    shoppingCartReply.orders.add(orderReply);
                });
            }
        }
        return shoppingCartReply;
    }
}
