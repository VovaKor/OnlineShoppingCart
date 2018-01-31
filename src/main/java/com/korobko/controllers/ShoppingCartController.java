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
import org.springframework.web.bind.annotation.*;

/**
 * @author Vova Korobko
 */
@RestController
public class ShoppingCartController {
    @Autowired
    private ProductOrderMapper productOrderMapper;
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * Calculates the total cost for products in the shopping cart. The {@code ShoppingCartReply}
     * contains only shopping cart id and total cost. List of orders is empty.
     *
     * @param cartId the {@code Long} value {@code ShoppingCart} id
     * @return the {@code ShoppingCartReply} object
     */
    @RequestMapping(path = "/shopping_cart/{cartId}/total",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ShoppingCartReply getShoppingCartTotalCost(@PathVariable Long cartId) {
        ShoppingCartReply shoppingCartReply = new ShoppingCartReply();
        shoppingCartReply.shoppingCartId = cartId;
        shoppingCartReply.totalCost = shoppingCartService.getShoppingCartTotalCost(cartId);
        return shoppingCartReply;
    }

    /**
     * Retrieves shopping cart from database
     *
     * @param cartId the {@code Long} value {@code ShoppingCart} id
     * @return the {@code ShoppingCartReply} object
     */
    @RequestMapping(path = "/shopping_cart/{cartId}",
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
    @RequestMapping(path = "/shopping_cart/update",
            method = RequestMethod.POST,
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
    @RequestMapping(path = "/shopping_cart/remove_item/{orderId}",
            method = RequestMethod.GET,
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
