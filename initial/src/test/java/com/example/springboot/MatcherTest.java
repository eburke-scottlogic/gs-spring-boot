package com.example.springboot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;


public class MatcherTest {

//    Matcher matcher;

    @BeforeEach
    public void setUp() {

        //Matcher matcher = new Matcher();
        Matcher.buyList.clear();
        Matcher.sellList.clear();
        Matcher.transHist.clear();
        Matcher.privBuy.clear();
        Matcher.privSell.clear();
        Matcher.privTransHist.clear();
        Matcher.aggBuy.clear();
        Matcher.aggSell.clear();
    }


    @Test
    @DisplayName("Adds to buy list")
    public void addToBuy() {
        Matcher.createOrder("user1", 10, 12, "buy");
        Matcher.createOrder("user2", 8, 8, "buy");
        assertEquals(2, Matcher.buyList.size());
    }

    @Test
    @DisplayName("Adds to sell list")
    public void addToSell() {
        Matcher.createOrder("user1", 12, 6, "sell");
        assertEquals(1, Matcher.sellList.size());
    }

    @Test
    @DisplayName("Adds to trade history")
    public void addToTrades() {
        Matcher.createOrder("user1", 12, 6, "sell");
        Matcher.createOrder("user2", 12, 6, "buy");
        assertEquals(1, Matcher.transHist.size());
    }

    @Test
    @DisplayName("Buy list is ordered")
    public void orderedBuy() {
        Matcher.createOrder("user1", 16, 6, "buy");
        Matcher.createOrder("user2", 12, 6, "buy");
        Matcher.createOrder("user3", 14, 6, "buy");
        assertEquals("user3", Matcher.buyList.get(1).getAccount());
    }

    @Test
    @DisplayName("Sell list is ordered")
    public void orderedSell() {
        Matcher.createOrder("user1", 16, 6, "sell");
        Matcher.createOrder("user2", 12, 6, "sell");
        Matcher.createOrder("user3", 14, 6, "sell");
        assertEquals("user2", Matcher.sellList.get(0).getAccount());
    }

    @Test
    @DisplayName("No trade when same account")
    public void noTrade() {
        Matcher.createOrder("user1", 12, 6, "sell");
        Matcher.createOrder("user1", 12, 6, "buy");
        assertEquals(0, Matcher.transHist.size());
    }

    @Test
    @DisplayName("Buyer quantity reduces after match")
    public void reduceBuy() {
        Matcher.createOrder("user1", 10, 12, "buy");
        Matcher.createOrder("user2", 10, 8, "sell");
        assertEquals(4, Matcher.buyList.get(0).getQuantity());
    }

    @Test
    @DisplayName("Seller quantity reduces after match")
    public void reduceSell() {
        Matcher.createOrder("user1", 10, 12, "buy");
        Matcher.createOrder("user2", 10, 14, "sell");
        assertEquals(2, Matcher.sellList.get(0).getQuantity());
    }

    @Test
    @DisplayName("Buy list empties after trade")
    public void emptyBuyList() {
        Matcher.createOrder("user1", 12, 6, "sell");
        Matcher.createOrder("user2", 12, 6, "buy");
        assertEquals(0, Matcher.buyList.size());
    }

    @Test
    @DisplayName("Sell list empties after trade")
    public void emptySellList() {
        Matcher.createOrder("user1", 12, 6, "sell");
        Matcher.createOrder("user2", 12, 6, "buy");
        assertEquals(0, Matcher.sellList.size());
    }

    @Test
    @DisplayName("Removes null order")
    public void removeNullOrder() {
        ArrayList<Order> newList = new ArrayList<>();
        Order order1=new Order("user1", 2, 0, "buy");
        Order order2=new Order("user2", 4, 2, "buy");
        newList.add(order1);
        newList.add(order2);
        Matcher.removeNullOrders(newList);
        assertEquals(1, newList.size());
    }

    @Test
    @DisplayName("addOrder works for buy list")
    public void addBuyOrder() {
        Order order1=new Order("user1", 2, 8, "buy");
        Order order2=new Order("user2", 4, 2, "buy");
        Matcher.addOrder(order1);
        Matcher.addOrder(order2);
        assertEquals(2, Matcher.buyList.size());
    }

    @Test
    @DisplayName("addOrder works for sell list")
    public void addSellOrder() {
        Order order1=new Order("user1", 2, 8, "sell");
        Order order2=new Order("user2", 4, 2, "sell");
        Matcher.addOrder(order1);
        Matcher.addOrder(order2);
        assertEquals(2, Matcher.sellList.size());
    }

    @Test
    @DisplayName("newMatch adds to transHist")
    public void matchOrders() {
        Order order1=new Order("user1", 2, 8, "buy");
        Order order2=new Order("user2", 2, 8, "sell");
        Matcher.newMatch(order1, order2);
        assertEquals(1, Matcher.transHist.size());
    }

    @Test
    @DisplayName("newTrade adds to transHist")
    public void tradeToTransHist() {
        Matcher.newTrade("user1", "user2", 2, 8, "buy");
        assertEquals(1, Matcher.transHist.size());
    }

    @Test
    @DisplayName("processOrder adds to buyList")
    public void processBuyOrder() {
        Order order1=new Order("user1", 2, 8, "buy");
        Matcher.processOrder(order1);
        assertEquals(1, Matcher.buyList.size());
    }

    @Test
    @DisplayName("processOrder adds to sellList")
    public void processSellOrder() {
        Order order1=new Order("user1", 2, 8, "sell");
        Matcher.processOrder(order1);
        assertEquals(1, Matcher.sellList.size());
    }

    @Test
    @DisplayName("Private buy order book")
    public void privBuyOrder() {
        Matcher.createOrder("user1", 2, 8, "buy");
        Matcher.privateOrderBuy("user1");
        assertEquals(1, Matcher.privBuy.size());
    }

    @Test
    @DisplayName("Private sell order book")
    public void privSellOrder() {
        Matcher.createOrder("user1", 2, 8, "sell");
        Matcher.privateOrderSell("user1");
        assertEquals(1, Matcher.privSell.size());
    }

    @Test
    @DisplayName("Private trade history")
    public void privTrans() {
        Matcher.newTrade("user1", "user2", 2, 8, "buy");
        Matcher.privateOrderTrans("user1");
        assertEquals(1, Matcher.privTransHist.size());
    }

    @Test
    @DisplayName("Agg buy book")
    public void aggBuy() {
        Matcher.createOrder("user1", 16, 4, "buy");
        Matcher.createOrder("user2", 16, 6, "buy");
        Matcher.aggregatedBuy();
        Object [] keys = Matcher.aggBuy.keySet().toArray();  //price
        Object [] values = Matcher.aggBuy.values().toArray();  //cumulative quantity
        assertEquals((float)16.0,keys[0]);  //price
        assertEquals(10,values[0]);  //cumulative quantity
    }

    @Test
    @DisplayName("Agg sell book")
    public void aggSell() {
        Matcher.createOrder("user1", 14, 4, "sell");
        Matcher.createOrder("user2", 14, 3, "sell");
        Matcher.aggregatedSell();
        Object [] keys = Matcher.aggSell.keySet().toArray();  //price
        Object [] values = Matcher.aggSell.values().toArray();  //cumulative quantity
        assertEquals((float)14.0,keys[0]);  //price
        assertEquals(7,values[0]);  //cumulative quantity
    }

}
