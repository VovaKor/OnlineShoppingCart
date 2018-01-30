package com.korobko.controllers;

import com.korobko.models.Category;
import com.korobko.models.Product;
import com.korobko.models.ProductOrder;
import com.korobko.models.ShoppingCart;
import com.korobko.repositories.ShoppingCartRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Vova Korobko
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ShoppingCartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ShoppingCartRepository repository;

    @Test
    public void getShoppingCartByUserId() throws Exception {
        ShoppingCart testShoppingCart = bootstrapDB();
        this.mockMvc.perform(get("/shopping_cart/100"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Delfa")))
                .andExpect(content().string(containsString("Kettles")));
        repository.delete(testShoppingCart.getShoppingCartId());
    }

    private ShoppingCart bootstrapDB() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(100L);
        ProductOrder productOrder = new ProductOrder();
        productOrder.setAmount(20);
        Product product = new Product();
        product.setProductName("Delfa");
        product.setPrice(60.05);
        Category category = new Category();
        category.setName("Kettles");
        category.setDescription("Electric kettles");
        product.setCategory(category);
        productOrder.setProduct(product);
        productOrder.setShoppingCart(shoppingCart);
        shoppingCart.getProductOrders().add(productOrder);
        return repository.save(shoppingCart);
    }
}