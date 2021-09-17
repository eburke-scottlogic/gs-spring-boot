package com.example.springboot;


import com.example.springboot.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.*;

// http://localhost:8080
//place a buy order
//place a sell order

@RestController
public class HelloController {

	@Autowired
	Matcher matcher;

	@Autowired
	AuthLogin authlogin;

	@Autowired
	AccountService accountService;

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
	public ArrayList[] placeOrder(@Valid @RequestBody Order order) {
		matcher.processOrder(order);
		ArrayList[] lists = new ArrayList[2];
		ArrayList<Order> buyList = matcher.buyList;
		ArrayList<Order> sellList = matcher.sellList;
		lists[0]=buyList;
		lists[1]=sellList;
		return lists;
	}

	@PostMapping("/login")
	public String newLogin(@Valid @RequestBody Login login) {
		String username = login.getUsername();
		String password = login.getPassword();
		boolean auth = authlogin.authenticate(username, password);
		int token = (username+password).hashCode();
		if (auth == true) {
			return "Success! Token: " + token;
		} else {
			return "Incorrect details, please try again.";
		}
	}

	@PostMapping("/account")
	public int saveAccount(@Valid @RequestBody Login login) {
		accountService.saveOrUpdate(login);
		return login.getId();
	}

}
