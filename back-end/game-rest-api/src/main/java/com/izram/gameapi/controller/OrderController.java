package com.izram.gameapi.controller;

import com.izram.gameapi.exception.InvalidOrderException;
import com.izram.gameapi.exception.OrderNotFoundException;
import com.izram.gameapi.model.Order;
import com.izram.gameapi.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/all")
    public Iterable<Order> findAllOrders() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public Order findOrderById(@PathVariable(value = "id") int id)
            throws OrderNotFoundException {
        return orderService.findOrderById(id);
    }

    @PostMapping(path = "/add")
    public ResponseEntity<Order> addNewOrder(@RequestBody Order order)
            throws InvalidOrderException {
        Order savedOrder = orderService.addNewOrder(order);
        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);

    }

    @DeleteMapping("/del/{id}")
    public Order deleteOrderById(@PathVariable(value = "id") int id)
            throws OrderNotFoundException {
        return orderService.deleteOrderById(id);
    }

}
