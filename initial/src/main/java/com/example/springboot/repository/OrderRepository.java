package com.example.springboot.repository;
import org.springframework.data.repository.CrudRepository;
import com.example.springboot.Orders;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Orders, Integer>
{
}