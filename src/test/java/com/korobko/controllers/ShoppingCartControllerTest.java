package com.korobko.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.korobko.api.AddProductRequest;
import com.korobko.api.ShoppingCartReply;
import com.korobko.models.Category;
import com.korobko.models.Product;
import com.korobko.models.ProductOrder;
import com.korobko.models.ShoppingCart;
import com.korobko.repositories.ProductOrderRepository;
import com.korobko.repositories.ProductRepository;
import com.korobko.repositories.ShoppingCartRepository;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    private ObjectMapper objectMapper;
    @Autowired
    private ProductOrderRepository productOrderRepository;

    @Test
    public void getShoppingCartById_givenIdPresentInDB_cartWithGivenId() throws Exception {
        mockMvc.perform(get("/shopping_cart/7"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("\"shoppingCartId\":7")));
    }

    @Test
    public void updateShoppingCart_newOrderInRequest_cartWithGivenOrder() throws Exception {
        AddProductRequest addProductRequest = new AddProductRequest();
        addProductRequest.cartId = 7L;
        addProductRequest.productId = 8L;
        addProductRequest.amount = 10;
        String json = objectMapper.writeValueAsString(addProductRequest);
        MvcResult result = mockMvc.perform(post("/shopping_cart/update")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json)
        )
                .andExpect(status().isOk())
                .andReturn();
        String reply = result.getResponse().getContentAsString();
        ShoppingCartReply shoppingCartReply = objectMapper.readValue(reply, ShoppingCartReply.class);
        assertEquals(7L, shoppingCartReply.shoppingCartId.longValue());
        assertTrue(reply.contains("\"productId\":8"));
        shoppingCartReply.orders.forEach(o -> {
            if (o.productReply.productId == 8L) {
                assertEquals(10, o.amount.intValue());
                productOrderRepository.delete(o.orderId);
            }
        });

    }


    @Test
    public void removeOrderById_withCorrectId_successReply() throws Exception {
        AddProductRequest addProductRequest = new AddProductRequest();
        addProductRequest.cartId = 7L;
        addProductRequest.productId = 6L;
        addProductRequest.amount = 10;
        String json = objectMapper.writeValueAsString(addProductRequest);
        MvcResult result = mockMvc.perform(post("/shopping_cart/update")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json)
        )
                .andExpect(status().isOk())
                .andReturn();
        String reply = result.getResponse().getContentAsString();
        ShoppingCartReply shopCartR = objectMapper.readValue(reply, ShoppingCartReply.class);

        shopCartR.orders.forEach(o -> {
            if (o.productReply.productId == 6L) {
                try {
                    Long orderId = o.orderId;
                    mockMvc.perform(get("/shopping_cart/remove_item/" + orderId))
                            .andDo(print()).andExpect(status().isOk())
                            .andExpect(content().string(containsString("\"retCode\":0")));
                    MvcResult mvcResult = mockMvc.perform(get("/shopping_cart/7"))
                            .andDo(print()).andExpect(status().isOk())
                            .andReturn();
                    String content = mvcResult.getResponse().getContentAsString();
                    assertFalse(content.contains("\"orderId\":" + orderId));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    @Test
    public void getShoppingCartTotalCost_twoOrders_correctSum() throws Exception {
        AddProductRequest product = new AddProductRequest();
        product.cartId = 7L;
        product.productId = 6L;
        product.amount = 10;
        String json = objectMapper.writeValueAsString(product);
        mockMvc.perform(post("/shopping_cart/update")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isOk());
        product.productId = 8L;
        product.amount = 50;
        json = objectMapper.writeValueAsString(product);
        mockMvc.perform(post("/shopping_cart/update")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isOk());
        mockMvc.perform(get("/shopping_cart/7/total"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("\"shoppingCartId\":7")))
                .andExpect(content().string(containsString("1603.0")));
        productOrderRepository.deleteAll();
    }

}