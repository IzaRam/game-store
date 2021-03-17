package com.izram.gameapi.service;

import com.izram.gameapi.exception.InvalidOrderException;
import com.izram.gameapi.exception.OrderNotFoundException;
import com.izram.gameapi.model.Order;
import com.izram.gameapi.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public Iterable<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order findOrderById(int id) throws OrderNotFoundException {
        return orderRepository.findById(id).orElseThrow(() ->
                new OrderNotFoundException(id));
    }

    @Override
    public Order addNewOrder(Order order) throws InvalidOrderException {
        if (order.getGameList().isEmpty()) {
            throw new InvalidOrderException();
        }
        return orderRepository.save(order);
    }

    @Override
    public Order deleteOrderById(int id) throws OrderNotFoundException {
        Order deletedOrder = orderRepository.findById(id).orElseThrow(() ->
                new OrderNotFoundException(id));
        orderRepository.deleteById(id);
        return deletedOrder;
    }
}
