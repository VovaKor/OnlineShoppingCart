package com.korobko.api;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vova Korobko
 */
public class ShoppingCartReply extends GenericReply {
    public Long shoppingCartId;
    public BigDecimal totalCost;
    public List<OrderReply> orders = new ArrayList<>();
}
