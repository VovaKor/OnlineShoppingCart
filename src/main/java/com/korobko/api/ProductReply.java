package com.korobko.api;

import java.math.BigDecimal;

/**
 * @author Vova Korobko
 */
public class ProductReply {
    public Long productId;
    public String productName;
    public BigDecimal price;
    public CategoryReply category;
}
