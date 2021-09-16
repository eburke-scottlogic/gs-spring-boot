package com.example.springboot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;


public class MatcherTest {

    Matcher matcher;

    @BeforeEach
    public void setUp() {
        matcher = new Matcher();
    }


    @Test
    @DisplayName("Adds to buy list")
    public void addToBuy() {
        matcher.createOrder("user1", 10, 12, "buy");
        matcher.createOrder("user2", 8, 8, "buy");
        assertEquals(2, matcher.buyList.size());
    }

    @Test
    @DisplayName("Adds to sell list")
    public void addToSell() {
        matcher.createOrder("user1", 12, 6, "sell");
        assertEquals(1, matcher.sellList.size());
    }

    @Test
    @DisplayName("Adds to trade history")
    public void addToTrades() {
        matcher.createOrder("user1", 12, 6, "sell");
        matcher.createOrder("user2", 12, 6, "buy");
        assertEquals(1, matcher.transHist.size());
    }

    @Test
    @DisplayName("Buy list is ordered")
    public void orderedBuy() {
        matcher.createOrder("user1", 16, 6, "buy");
        matcher.createOrder("user2", 12, 6, "buy");
        matcher.createOrder("user3", 14, 6, "buy");
        assertEquals("user3", matcher.buyList.get(1).getAccount());
    }

    @Test
    @DisplayName("Sell list is ordered")
    public void orderedSell() {
        matcher.createOrder("user1", 16, 6, "sell");
        matcher.createOrder("user2", 12, 6, "sell");
        matcher.createOrder("user3", 14, 6, "sell");
        assertEquals("user2", matcher.sellList.get(0).getAccount());
    }

    @Test
    @DisplayName("No trade when same account")
    public void noTrade() {
        matcher.createOrder("user1", 12, 6, "sell");
        matcher.createOrder("user1", 12, 6, "buy");
        assertEquals(0, matcher.transHist.size());
    }

    @Test
    @DisplayName("Buyer quantity reduces after match")
    public void reduceBuy() {
        matcher.createOrder("user1", 10, 12, "buy");
        matcher.createOrder("user2", 10, 8, "sell");
        assertEquals(4, matcher.buyList.get(0).getQuantity());
    }

    @Test
    @DisplayName("Seller quantity reduces after match")
    public void reduceSell() {
        matcher.createOrder("user1", 10, 12, "buy");
        matcher.createOrder("user2", 10, 14, "sell");
        assertEquals(2, matcher.sellList.get(0).getQuantity());
    }

    @Test
    @DisplayName("Buy list empties after trade")
    public void emptyBuyList() {
        matcher.createOrder("user1", 12, 6, "sell");
        matcher.createOrder("user2", 12, 6, "buy");
        assertEquals(0, matcher.buyList.size());
    }

    @Test
    @DisplayName("Sell list empties after trade")
    public void emptySellList() {
        matcher.createOrder("user1", 12, 6, "sell");
        matcher.createOrder("user2", 12, 6, "buy");
        assertEquals(0, matcher.sellList.size());
    }

    @Test
    @DisplayName("Removes null order")
    public void removeNullOrder() {
        ArrayList<Order> newList = new ArrayList<>();
        Order order1=new Order("user1", 2, 0, "buy");
        Order order2=new Order("user2", 4, 2, "buy");
        newList.add(order1);
        newList.add(order2);
        matcher.removeNullOrders(newList);
        assertEquals(1, newList.size());
    }

    @Test
    @DisplayName("addOrder works for buy list")
    public void addBuyOrder() {
        Order order1=new Order("user1", 2, 8, "buy");
        Order order2=new Order("user2", 4, 2, "buy");
        matcher.addOrder(order1);
        matcher.addOrder(order2);
        assertEquals(2, matcher.buyList.size());
    }

    @Test
    @DisplayName("addOrder works for sell list")
    public void addSellOrder() {
        Order order1=new Order("user1", 2, 8, "sell");
        Order order2=new Order("user2", 4, 2, "sell");
        matcher.addOrder(order1);
        matcher.addOrder(order2);
        assertEquals(2, matcher.sellList.size());
    }

    @Test
    @DisplayName("newMatch adds to transHist")
    public void matchOrders() {
        Order order1=new Order("user1", 2, 8, "buy");
        Order order2=new Order("user2", 2, 8, "sell");
        matcher.newMatch(order1, order2);
        assertEquals(1, matcher.transHist.size());
    }

    @Test
    @DisplayName("newTrade adds to transHist")
    public void tradeToTransHist() {
        matcher.newTrade("user1", "user2", 2, 8, "buy");
        assertEquals(1, matcher.transHist.size());
    }

    @Test
    @DisplayName("processOrder adds to buyList")
    public void processBuyOrder() {
        Order order1=new Order("user1", 2, 8, "buy");
        matcher.processOrder(order1);
        assertEquals(1, matcher.buyList.size());
    }

    @Test
    @DisplayName("processOrder adds to sellList")
    public void processSellOrder() {
        Order order1=new Order("user1", 2, 8, "sell");
        matcher.processOrder(order1);
        assertEquals(1, matcher.sellList.size());
    }

    @Test
    @DisplayName("Private buy order book")
    public void privBuyOrder() {
        matcher.createOrder("user1", 2, 8, "buy");
        matcher.privateOrderBuy("user1");
        assertEquals(1, matcher.privBuy.size());
    }

    @Test
    @DisplayName("Private sell order book")
    public void privSellOrder() {
        matcher.createOrder("user1", 2, 8, "sell");
        matcher.privateOrderSell("user1");
        assertEquals(1, matcher.privSell.size());
    }

    @Test
    @DisplayName("Private trade history")
    public void privTrans() {
        matcher.newTrade("user1", "user2", 2, 8, "buy");
        matcher.privateOrderTrans("user1");
        assertEquals(1, matcher.privTransHist.size());
    }

    @Test
    @DisplayName("Agg buy book")
    public void aggBuy() {
        matcher.createOrder("user1", 16, 4, "buy");
        matcher.createOrder("user2", 16, 6, "buy");
        matcher.aggregatedBuy();
        Object [] keys = matcher.aggBuy.keySet().toArray();  //price
        Object [] values = matcher.aggBuy.values().toArray();  //cumulative quantity
        assertEquals((float)16.0,keys[0]);  //price
        assertEquals(10,values[0]);  //cumulative quantity
    }

    @Test
    @DisplayName("Agg sell book")
    public void aggSell() {
        matcher.createOrder("user1", 14, 4, "sell");
        matcher.createOrder("user2", 14, 3, "sell");
        matcher.aggregatedSell();
        Object [] keys = matcher.aggSell.keySet().toArray();  //price
        Object [] values = matcher.aggSell.values().toArray();  //cumulative quantity
        assertEquals((float)14.0,keys[0]);  //price
        assertEquals(7,values[0]);  //cumulative quantity
    }


}
