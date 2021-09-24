package com.example.springboot.repository;
import org.springframework.data.repository.CrudRepository;
import com.example.springboot.Trade;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepository extends CrudRepository<Trade, Integer>
{
}