package com.izram.gameapi.repository;

import com.izram.gameapi.model.Game;
import com.izram.gameapi.model.Order;
import com.izram.gameapi.model.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    private Order order;

    @BeforeEach
    public void setUp() {
        order = new Order(0, 1, new ArrayList<>());
    }

    @AfterEach
    public void tearDown(TestInfo testInfo) {
        if (!testInfo.getTags().contains("SkipDeleteOrderById"))
            orderRepository.deleteById(order.getOrder_id());
        order = null;
    }

    @Test
    public void givenGetAllOrdersShouldReturnListOfAllOrders(){
        Order savedOrder = orderRepository.save(order);
        order.setOrder_id(savedOrder.getOrder_id());
        List<Order> orderList = (List<Order>) orderRepository.findAll();
        assertThat(orderList).extracting(Order::getUser_id).contains(1);
    }

    @Test
    public void givenOrderToAddShouldReturnAddedOrderById(){
        Order savedOrder = orderRepository.save(order);
        order.setOrder_id(savedOrder.getOrder_id());
        Order fetchedOrder = orderRepository.findById(order.getOrder_id()).get();
        assertEquals(order.getOrder_id(), fetchedOrder.getOrder_id());
        assertEquals(order.getUser_id(), fetchedOrder.getUser_id());
    }

    @Test
    @Tag("SkipDeleteOrderById")
    public void givenOrderIdTODeleteThenShouldDeleteTheOrder() {
        Order orderToDelete = new Order(0, 1, new ArrayList<>());
        orderToDelete = orderRepository.save(orderToDelete);
        orderRepository.deleteById(orderToDelete.getOrder_id());
        Optional<Order> optional = orderRepository.findById(orderToDelete.getOrder_id());
        assertEquals(Optional.empty(), optional);
    }

}