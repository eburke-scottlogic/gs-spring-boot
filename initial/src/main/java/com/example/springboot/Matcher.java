package com.example.springboot;

import java.util.*;

public class Matcher {   //every Java program has a class name which must match the filename

    public static ArrayList<Order> buyList = new ArrayList<Order>();
    public static ArrayList<Order> sellList = new ArrayList<Order>();
    public static ArrayList<Trade> transHist = new ArrayList<Trade>();

    public static void createOrder(String account, float price, int quantity, String action) {
        Order newOrder=new Order(account, price, quantity, action);
        processOrder(newOrder);
    }

    public static void addOrder(Order order) {
        if (order.getAction() == "buy") {
            buyList.add(order);
            //sort list in descending
        } else {
            sellList.add(order);
            //sort list in ascending
        }
    }


    public static void newMatch(Order order, Order matchedOrder) {
        if (order.getQuantity() == matchedOrder.getQuantity()) {
            //pair off - transaction history
            newTrade(order.getAccount(), matchedOrder.getAccount(), matchedOrder.getPrice(), order.getQuantity(), order.getAction());
            order.setQuantity(0);
            matchedOrder.setQuantity(0);; //make order in sell list null value
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

    public static void newTrade(String account1, String account2, float price, int quantity, String action) {
        Trade newTrade=new Trade(account1, account2, price, quantity, action);
        transHist.add(newTrade);
    }

    public static void removeNullOrders(ArrayList<Order> list) {
        for (Order order:list) {
            if(order.getQuantity()==0){
                list.remove(order);
            }
        }
    }

    public static void processOrder(Order order) {
        if (order.getAction() == "buy") {
            for (Order sellOrder:sellList) {
                if (order.getPrice() == sellOrder.getPrice()) {
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
                if (order.getPrice() == buyOrder.getPrice()) {
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
    public static void privateOrderBuy(String currentAccount) {
        ArrayList<Order> privBuy = new ArrayList<Order>();
        for (Order buyOrder:buyList) {
            if(buyOrder.getAccount()==currentAccount) {
                privBuy.add(buyOrder);
            }
        }
    }

    public static void privateOrderSell(String currentAccount) {
        ArrayList<Order> privSell = new ArrayList<Order>();
        for (Order sellOrder:sellList) {
            if(sellOrder.getAccount()==currentAccount) {
                privSell.add(sellOrder);
            }
        }
    }

    public static void privateOrderTrans(String currentAccount) {
        ArrayList<Trade> privTransHist = new ArrayList<Trade>();
        for (Trade trans:transHist) {
            if(trans.getAccount1()==currentAccount || trans.getAccount2()==currentAccount) {
                privTransHist.add(trans);
            }
        }
    }

    //aggregated order book
    public static void aggregatedSell() {
        HashMap<Float, Integer> aggSell = new HashMap<Float, Integer>();
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

    public static void aggregatedBuy() {
        HashMap<Float, Integer> aggBuy = new HashMap<Float, Integer>();
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


    public static void main(String[] args) {  //every program must contain the main() method
        createOrder("user1", 10, 12, "buy");
    }

}