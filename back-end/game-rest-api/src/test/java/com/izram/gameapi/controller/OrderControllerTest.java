package com.izram.gameapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.izram.gameapi.model.Order;
import com.izram.gameapi.service.OrderServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderServiceImpl orderService;

    private Order order1;
    private Order order2;
    private List<Order> orderList;

    @InjectMocks
    private OrderController orderController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        order1 = new Order(1, 1, new ArrayList<>());
        order2 = new Order(2, 2, new ArrayList<>());
        orderList = new ArrayList<>(Arrays.asList(order1, order2));
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @AfterEach
    void tearDown() {
        order1 = order2 = null;
        orderList = null;
    }

    @Test
    public void GetMappingOfAllOrders() throws Exception {
        when(orderService.findAll()).thenReturn(orderList);
        mockMvc.perform(get("/api/v1/orders/all"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
        verify(orderService, times(1)).findAll();
    }

    @Test
    public void GetMappingOfOrderIdShouldReturnRespectiveOrder() throws Exception {
        when(orderService.findOrderById(order1.getOrder_id())).thenReturn(order1);
        mockMvc.perform(get("/api/v1/orders/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("1")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void PostMappingOfOrder() throws Exception {
        when(orderService.addNewOrder(any())).thenReturn(order1);
        mockMvc.perform(post("/api/v1/orders/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(order1)))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("1")));
    }

    @Test
    public void DeleteMappingOfOrderIdShouldReturnDeletedOrder() throws Exception {
        when(orderService.deleteOrderById(order1.getOrder_id())).thenReturn(order1);
        mockMvc.perform(delete("/api/v1/orders/del/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string(containsString("1")))
                .andDo(MockMvcResultHandlers.print());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}