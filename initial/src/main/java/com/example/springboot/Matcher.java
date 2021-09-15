package com.example.springboot;

import java.util.*;

public class Matcher {   //every Java program has a class name which must match the filename

    public ArrayList<Order> buyList;
    public ArrayList<Order> sellList;
    public ArrayList<Trade> transHist;

    public Matcher() {                     //constructor
        buyList = new ArrayList<Order>();
        sellList = new ArrayList<Order>();
        transHist = new ArrayList<Trade>();
//        createOrder("user1", 2, 3, "buy");
//        createOrder("user2", 6, 7, "buy");
//        createOrder("user3", 9, 5, "buy");
    }


    public void createOrder(String account, float price, int quantity, String action) {
        Order newOrder=new Order(account, price, quantity, action);
        processOrder(newOrder);
    }

    public void addOrder(Order order) {
        if (order.getAction().equals("buy")) {
            //sort buy list in descending
            if (buyList.size() == 0) {
                buyList.add(order);
            } else {
                for (int i = 0; i < buyList.size(); i++) {
                    if (buyList.get(i).getPrice() >= order.getPrice()) {
                        int len = buyList.size() - 1;
                        if (i == len) {
                            buyList.add(order);
                            break;
                        } else {
                            continue;
                        }
                    } else {
                        buyList.add(i, order);
                        break;
                    }
                }
            }
        } else {
            //sort sell list in ascending
            if (sellList.size() == 0) {
                sellList.add(order);
            } else {
                for (int i = 0; i < sellList.size(); i++) {
                    if (sellList.get(i).getPrice() <= order.getPrice()) {
                        int len = sellList.size() - 1;
                        if (i == len) {
                            sellList.add(order);
                            break;
                        } else {
                            continue;
                        }
                    } else {
                        sellList.add(i, order);
                        break;
                    }
                }
            }
        }
    }


    public void newMatch(Order order, Order matchedOrder) {
        if (order.getQuantity() == matchedOrder.getQuantity()) {
            //pair off - transaction history
            newTrade(order.getAccount(), matchedOrder.getAccount(), matchedOrder.getPrice(), order.getQuantity(), order.getAction());
            order.setQuantity(0);
            matchedOrder.setQuantity(0);  //make order in sell list null value
        }
        else if (order.getQuantity() <= matchedOrder.getQuantity()) {
            newTrade(order.getAccount(), matchedOrder.getAccount(), matchedOrder.getPrice(), order.getQuantity(), order.getAction());
            matchedOrder.setQuantity(matchedOrder.getQuantity() - order.getQuantity());
            order.setQuantity(0);
            //pair off to transaction history with order quantity
        }
        else {  //order quantity > matchedOrder quantity
            newTrade(order.getAccount(), matchedOrder.getAccount(), matchedOrder.getPrice(), matchedOrder.getQuantity(), order.getAction());
            order.setQuantity(order.getQuantity() - matchedOrder.getQuantity());
            matchedOrder.setQuantity(0);  //make order in sell list null value
        }
    }

    public void newTrade(String account1, String account2, float price, int quantity, String action) {
        Trade trade=new Trade(account1, account2, price, quantity, action);
        transHist.add(trade);
    }

    public void removeNullOrders(ArrayList<Order> list) {
        for (int i=0; i<list.size();i++) {
            if (list.get(i).getQuantity()==0) {
                list.remove(i);
                i=i-1;
            }
        }
    }

    public void processOrder(Order order) {
        if (order.getAction().equals("buy")) {
            for (Order sellOrder:sellList) {
                if (order.getAccount().equals(sellOrder.getAccount())) {
                    continue;
                } else {
                    if (order.getPrice() >= sellOrder.getPrice()) {
                        newMatch(order, sellOrder);
                    } else {
                        break;
                    }
                }
                if (order.getQuantity() == 0) {
                    break;
                }
            }
            removeNullOrders(sellList);
            if (order.getQuantity() != 0) {
                addOrder(order);
            }
        } else {  //if action is sell
            for (Order buyOrder:buyList) {
                if (order.getAccount().equals(buyOrder.getAccount())) {
                    continue;
                } else {
                    if (order.getPrice() <= buyOrder.getPrice()) {
                        newMatch(order, buyOrder);
                    } else {
                        break;
                    }
                }
                if (order.getQuantity() == 0) {
                    break;
                }
            }
            removeNullOrders(buyList);
            if (order.getQuantity() != 0) {
                addOrder(order);
            }
        }
    }

    //private order book
    public ArrayList<Order> privBuy = new ArrayList<Order>();
    public ArrayList<Order> privSell = new ArrayList<Order>();
    public ArrayList<Trade> privTransHist = new ArrayList<Trade>();


    public void privateOrderBuy(String currentAccount) {
        privBuy.clear();
        for (Order buyOrder:buyList) {
            if(buyOrder.getAccount().equals(currentAccount)) {
                privBuy.add(buyOrder);
            }
        }
    }

    public void privateOrderSell(String currentAccount) {
        privSell.clear();
        for (Order sellOrder:sellList) {
            if(sellOrder.getAccount().equals(currentAccount)) {
                privSell.add(sellOrder);
            }
        }
    }

    public void privateOrderTrans(String currentAccount) {
        privTransHist.clear();
        for (Trade trans:transHist) {
            if(trans.getAccount1().equals(currentAccount) || trans.getAccount2().equals(currentAccount)) {
                privTransHist.add(trans);
            }
        }
    }

    //aggregated order book
    public HashMap<Float, Integer> aggSell = new HashMap<Float, Integer>();
    public HashMap<Float, Integer> aggBuy = new HashMap<Float, Integer>();

    public void aggregatedSell() {
        aggSell.clear();
        for (Order sellOrder:sellList) {
            float price = sellOrder.getPrice();
            if (aggSell.containsKey(price)) {
                int quantity = aggSell.get(price) + sellOrder.getQuantity();
                aggSell.put(price, quantity);
            } else {
                aggSell.put(price, sellOrder.getQuantity());
            }
        }
    }

    public void aggregatedBuy() {
        aggBuy.clear();
        for (Order buyOrder:buyList) {
            float price = buyOrder.getPrice();
            if (aggBuy.containsKey(price)) {
                int quantity = aggBuy.get(price) + buyOrder.getQuantity();
                aggBuy.put(price, quantity);
            } else {
                aggBuy.put(price, buyOrder.getQuantity());
            }
        }
    }


//    public void main(String[] args) {  //every program must contain the main() method
//        Matcher matcher = new Matcher();
//        matcher.createOrder("user1", 12, 6, "sell");
//        matcher.createOrder("user2", 12, 6, "buy");
//        matcher.createOrder("user3", 15, 6, "sell");
//        System.out.println(Matcher.sellList.size());
//        System.out.println(Matcher.buyList.size());
//    }

}