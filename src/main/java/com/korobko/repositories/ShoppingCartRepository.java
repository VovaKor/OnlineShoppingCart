package com.korobko.repositories;

import com.korobko.models.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Vova Korobko
 */
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    ShoppingCart findShoppingCartByUserId(Long userId);
}
