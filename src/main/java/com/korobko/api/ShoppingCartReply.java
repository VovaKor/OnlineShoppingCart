package com.korobko.api;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vova Korobko
 */
public class ShoppingCartReply extends GenericReply {
    public Long shoppingCartId;
    public Double totalCost;
    public List<OrderReply> orders = new ArrayList<>();
}
