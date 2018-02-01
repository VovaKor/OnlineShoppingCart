package com.korobko.controllers;

import com.korobko.api.AddProductRequest;
import com.korobko.api.GenericReply;
import com.korobko.api.ShoppingCartReply;
import com.korobko.mappers.ProductOrderMapper;
import com.korobko.mappers.ShoppingCartMapper;
import com.korobko.services.ShoppingCartService;
import com.korobko.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Vova Korobko
 */
@RestController
@RequestMapping("/shopping_cart")
public class ShoppingCartController {
    @Autowired
    private ProductOrderMapper productOrderMapper;
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * Retrieves shopping cart from database
     *
     * @param cartId the {@code Long} value {@code ShoppingCart} id
     * @return the {@code ShoppingCartReply} object
     */
    @RequestMapping(path = "/{cartId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ShoppingCartReply getShoppingCartById(@PathVariable Long cartId) {
        ShoppingCartReply basket = shoppingCartMapper.fromInternal(shoppingCartService.getShoppingCartById(cartId));
        basket.totalCost = shoppingCartService.getShoppingCartTotalCost(cartId);
        return basket;
    }

    /**
     * Adds product to shopping cart by creating a new order. If product is already in shopping cart
     * then updates product amount in order. Returns shopping cart in new state.
     *
     * @param addProductRequest the {@code AddProductRequest} object
     * @return the {@code ShoppingCartReply} object
     */
    @RequestMapping(method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ShoppingCartReply updateShoppingCart(@RequestBody AddProductRequest addProductRequest) {
        Long basketId = addProductRequest.cartId;
        ShoppingCartReply basket = shoppingCartMapper.fromInternal(shoppingCartService.updateShoppingCart(basketId, productOrderMapper.toInternal(addProductRequest)));
        basket.totalCost = shoppingCartService.getShoppingCartTotalCost(basketId);
        return basket;
    }

    /**
     * Removes {@code ProductOrder} with given id from shopping cart
     *
     * @param orderId the {@code Long} value {@code ProductOrder} id
     * @return the {@code GenericReply} object
     * @see GenericReply
     */
    @RequestMapping(path = "/orders/{orderId}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public GenericReply removeOrderById(@PathVariable Long orderId) {
        GenericReply genericReply = new GenericReply();
        try {
            shoppingCartService.removeProductOrderById(orderId);
        } catch (Exception e) {
            genericReply.retCode = Constants.ERROR_CODE;
            genericReply.error_message = e.getMessage();
        }
        return genericReply;
    }
}
