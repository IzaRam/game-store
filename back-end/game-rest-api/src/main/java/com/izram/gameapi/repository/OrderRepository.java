package com.izram.gameapi.repository;

import com.izram.gameapi.model.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Integer> {

}
