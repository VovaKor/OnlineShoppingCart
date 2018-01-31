package com.korobko.services;

import com.korobko.models.Product;
import com.korobko.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Vova Korobko
 */
@Service
@Transactional
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product getProductById(Long productId) {
        return productRepository.findOne(productId);
    }
}
