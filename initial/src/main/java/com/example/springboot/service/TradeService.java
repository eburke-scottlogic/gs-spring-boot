package com.example.springboot.service;

import com.example.springboot.repository.TradeRepository;
import com.example.springboot.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TradeService {

    @Autowired
    TradeRepository tradeRepository;

    public List<Trade> getAllTrades()
    {
        List<Trade> trades = new ArrayList<>();
        tradeRepository.findAll().forEach(trade -> trades.add(trade));
        return trades;
    }

    public void saveOrUpdate(Trade trade) {
        tradeRepository.save(trade);
    }


}