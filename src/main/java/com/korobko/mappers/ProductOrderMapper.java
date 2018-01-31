package com.korobko.mappers;

import com.korobko.api.AddProductRequest;
import com.korobko.models.ProductOrder;
import com.korobko.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Vova Korobko
 */
@Component
public class ProductOrderMapper {
    @Autowired
    private ProductService productService;

    /**
     * Creates new {@code ProductOrder} from external REST object
     *
     * @param addProductRequest the external REST {@code AddProductRequest} object
     * @return the {@code ProductOrder} object
     */
    public ProductOrder toInternal(AddProductRequest addProductRequest) {
        ProductOrder productOrder = new ProductOrder();
        productOrder.setAmount(addProductRequest.amount);
        productOrder.setProduct(productService.getProductById(addProductRequest.productId));
        return productOrder;
    }
}
