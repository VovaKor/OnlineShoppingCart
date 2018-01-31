package com.korobko.repositories;

import com.korobko.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Vova Korobko
 */

public interface ProductRepository extends JpaRepository<Product, Long> {
}
