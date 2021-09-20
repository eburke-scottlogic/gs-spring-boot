package com.example.springboot;


import com.example.springboot.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

// http://localhost:8080

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

	//buy list
	@GetMapping("/allBuys")
	public ArrayList<Order> callBuyList() {
		return matcher.buyList;
	}

	//sell list
	@GetMapping("/allSells")
	public ArrayList<Order> callSellList() {
		return matcher.sellList;
	}

	//new order
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

	//login token, validates against database
	@PostMapping("/login")
	public String newLogin(@Valid @RequestBody Login login) {
		List<Integer> ids = accountService.getAllIds();
		List<String> usernames = accountService.getAllAccounts();
		List<String> passwords = accountService.getAllPasswords();
		boolean auth = authlogin.authenticate(ids, usernames, passwords, login);
		int token = (login.getUsername()+login.getPassword()).hashCode();
		if (auth) {
			return "Success! Token: " + token;
		} else {
			return "Incorrect details, please try again.";
		}
	}


	//retrieves all the usernames from the database
	@GetMapping("/allAccounts")
	private List<String> getAllLogins()
	{
		return accountService.getAllAccounts();
	}

	//retrieves the detail of a specific account
	@GetMapping("/account/{id}")
	private Login getStudent(@PathVariable("id") int id)
	{
		return accountService.getAccountById(id);
	}

	//deletes a specific account
	@DeleteMapping("/account/{id}")
	private void deleteAccount(@PathVariable("id") int id)
	{
		accountService.delete(id);
	}

	//post account to the database
	@PostMapping("/account")
	public String saveAccount(@Valid @RequestBody Login login) {
		accountService.saveOrUpdate(login);
		String user = login.getUsername();
		return "New account created, username: " + user;
	}


}
