package com.korobko.services;

import com.korobko.models.ShoppingCart;
import com.korobko.repositories.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Vova Korobko
 */
@Service
@Transactional(timeout = 10)
public class ShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Transactional(readOnly = true)
    public ShoppingCart getShoppingCartByUserId(Long userId) {
        return shoppingCartRepository.findShoppingCartByUserId(userId);
    }
}
