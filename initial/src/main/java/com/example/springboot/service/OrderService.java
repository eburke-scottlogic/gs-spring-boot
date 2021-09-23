package com.example.springboot.service;

import com.example.springboot.repository.OrderRepository;
import com.example.springboot.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public List<Orders> getAllOrders()
    {
        List<Orders> orders = new ArrayList<>();
        orderRepository.findAll().forEach(order -> orders.add(order));
        return orders;
    }

    public void saveOrUpdate(Orders order) {
        orderRepository.save(order);
    }


}