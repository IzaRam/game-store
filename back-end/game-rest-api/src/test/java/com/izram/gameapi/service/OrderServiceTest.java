package com.izram.gameapi.service;

import com.izram.gameapi.exception.InvalidOrderException;
import com.izram.gameapi.exception.OrderNotFoundException;
import com.izram.gameapi.model.Game;
import com.izram.gameapi.model.Order;
import com.izram.gameapi.repository.OrderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    private Order order1;
    private Order order2;
    private List<Order> orderList;

    @Autowired
    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        order1 = new Order(1, 1, new ArrayList<>());
        order2 = new Order(2, 2, new ArrayList<>());
        orderList = new ArrayList<>(Arrays.asList(order1, order2));
    }

    @AfterEach
    void tearDown() {
        order1 = order2 = null;
        orderList = null;
    }

    @Test
    public void givenGetAllOrdersShouldReturnListOfAllOrders(){
        when(orderRepository.findAll()).thenReturn(orderList);
        List<Order> orderListReturned = (List<Order>) orderService.findAll();
        assertEquals(orderListReturned, orderList);
        verify(orderRepository,times(1)).findAll();
    }

    @Test
    public void givenIdThenShouldReturnOrderOfThatId() throws OrderNotFoundException {
        when(orderRepository.findById(order1.getOrder_id())).thenReturn(Optional.ofNullable(order1));
        assertThat(orderService.findOrderById(order1.getOrder_id())).isEqualTo(order1);
        verify(orderRepository, times(1)).findById(any());
    }

    @Test
    public void givenOrderIdThenShouldThrowException() {
        when(orderRepository.findById(order1.getOrder_id())).thenReturn(Optional.empty());
        OrderNotFoundException e = assertThrows(OrderNotFoundException.class,
                () -> orderService.findOrderById(order1.getOrder_id()));
        assertTrue(e.getMessage().contains("Order not found with ID " + order1.getOrder_id()));
        verify(orderRepository, times(1)).findById(any());
    }

    @Test
    void givenOrderToAddShouldReturnAddedOrder() throws InvalidOrderException {
        order1.getGameList().add(new Game(1, "game_test", 2021, "game for test", "www.image", 0, 0));
        when(orderRepository.save(any())).thenReturn(order1);
        assertEquals(order1, orderService.addNewOrder(order1));
        verify(orderRepository,times(1)).save(any());
    }

    @Test
    void givenEmptyOrderToAddShouldThrowException() {
        InvalidOrderException e = assertThrows(InvalidOrderException.class,
                () -> orderService.addNewOrder(order1));
        assertTrue(e.getMessage().contains("Game List is Empty"));
        verify(orderRepository,times(0)).save(any());
    }

    @Test
    public void givenOrderIdToDeleteThenShouldDeleteTheOrderOfThatId() throws OrderNotFoundException {
        when(orderRepository.findById(order1.getOrder_id())).thenReturn(Optional.ofNullable(order1));
        Order deletedOrder = orderService.deleteOrderById(order1.getOrder_id());
        assertEquals(deletedOrder, order1);
        verify(orderRepository,times(1)).findById(any());
        verify(orderRepository,times(1)).deleteById(any());
    }

    @Test
    public void givenOrderIdToDeleteThenShouldThrowException() {
        when(orderRepository.findById(order1.getOrder_id())).thenReturn(Optional.empty());
        OrderNotFoundException e = assertThrows(OrderNotFoundException.class,
                () -> orderService.deleteOrderById(order1.getOrder_id()));
        assertTrue(e.getMessage().contains("Order not found with ID " + order1.getOrder_id()));
        verify(orderRepository,times(1)).findById(any());
        verify(orderRepository,times(0)).deleteById(any());
    }
}