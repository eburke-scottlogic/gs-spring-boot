package com.example.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.*;

// http://localhost:8080
//place a buy order
//place a sell order

@RestController
public class HelloController {

	Matcher matcher = new Matcher();

	@GetMapping("/")
	public String index() {
		String sentence = "Welcome to the trading app!";
		return sentence;
	}

	@GetMapping("/allBuys")
	public ArrayList<Order> callBuyList() {
		return matcher.buyList;
	}

	@GetMapping("/allSells")
	public ArrayList<Order> callSellList() {
		return matcher.sellList;
	}

	@PostMapping("/createOrder")
	public ArrayList<ArrayList<Order>> placeOrder(@RequestBody Order order) {
		matcher.processOrder(order);
		ArrayList<ArrayList<Order>> lists = new ArrayList<ArrayList<Order>>();
		ArrayList<Order> buyList = matcher.buyList;
		ArrayList<Order> sellList = matcher.sellList;
		lists.add(buyList);
		lists.add(sellList);
		return lists;
	}

}
