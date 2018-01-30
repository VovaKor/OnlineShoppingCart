package com.korobko.controllers;

import com.korobko.api.ShoppingCartReply;
import com.korobko.mappers.ShoppingCartMapper;
import com.korobko.services.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Vova Korobko
 */
@RestController
public class ShoppingCartController {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private ShoppingCartService shoppingCartService;

    @RequestMapping(path = "/shopping_cart/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ShoppingCartReply getShoppingCartByUserId(@PathVariable Long userId) {
        return shoppingCartMapper.fromInternal(shoppingCartService.getShoppingCartByUserId(userId));
    }
}
