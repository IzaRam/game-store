package com.izram.gameapi.service;

import com.izram.gameapi.exception.InvalidOrderException;
import com.izram.gameapi.exception.OrderNotFoundException;
import com.izram.gameapi.model.Order;

public interface OrderService {
    Iterable<Order> findAll();

    Order findOrderById(int id) throws OrderNotFoundException;

    Order addNewOrder(Order order) throws InvalidOrderException;

    Order deleteOrderById(int id) throws OrderNotFoundException;
}
