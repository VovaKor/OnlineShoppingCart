package com.korobko.api;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vova Korobko
 */
public class ShoppingCartReply {
    public Long shoppingCartId;
    public List<OrderReply> orders = new ArrayList<>();
}
