package com.example.springboot;


import com.example.springboot.service.AccountService;
import com.example.springboot.service.OrderService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

// postman: http://localhost:8080
// h2: http://localhost:8080/h2-console

@RestController
@CrossOrigin("*")
public class HelloController {

	@Autowired
	Matcher matcher;

	@Autowired
	AuthLogin authlogin;

	@Autowired
	AccountService accountService;

	@Autowired
	OrderService orderService;

	@Autowired
	PlaceOrder placeOrder;

	String userToAccount;

	@GetMapping("/")
	public String index() {
		String sentence = "Welcome to the trading app!";
		return sentence;
	}

	//buy list
	@GetMapping("/allBuys")
	public ArrayList<Orders> callBuyList() {
		return matcher.buyList;
	}

	//sell list
	@GetMapping("/allSells")
	public ArrayList<Orders> callSellList() {
		return matcher.sellList;
	}

	//trade list
	@GetMapping("/allTrades")
	public ArrayList<Trade> callTransHist() {
		return matcher.transHist;
	}

	//agg buy list
	@GetMapping("/aggBuys")
	public HashMap<Float, Integer>  aggBuyList() {
		matcher.aggregatedBuy();
		return matcher.aggBuy;
	}

	//agg sell list
	@GetMapping("/aggSells")
	public HashMap<Float, Integer>  aggSellList() {
		matcher.aggregatedSell();
		return matcher.aggSell;
	}


	//private trades
	@GetMapping("/privTrades")
	public ArrayList<Trade> privTransHist() {
		matcher.privateOrderTrans(userToAccount);
		return matcher.privTransHist;
	}

	//private buy list
	@GetMapping("/privBuy")
	public ArrayList<Orders> privBuyList() {
		matcher.privateOrderBuy(userToAccount);
		return matcher.privBuy;
	}

	//private sell list
	@GetMapping("/privSell")
	public ArrayList<Orders> privSellList() {
		matcher.privateOrderSell(userToAccount);
		return matcher.privSell;
	}


	//new order
	@PostMapping("/createOrder")
	public PlaceOrder placeOrder(@Valid @RequestBody OrderInfo orderInfo) {
		Orders order = new Orders(userToAccount, orderInfo.getPrice(), orderInfo.getQuantity(), orderInfo.getAction());
		orderService.saveOrUpdate(order);
		matcher.processOrder(order);
		ArrayList[] lists = new ArrayList[4];
		lists[0]=matcher.transHist;
		lists[1]=privSellList();
		lists[2]=privBuyList();
		lists[3]=privTransHist();
		HashMap[] aggregateBook=new HashMap[2];
		aggregateBook[0] = aggBuyList();
		aggregateBook[1] = aggSellList();
		placeOrder.setLists(lists);
		placeOrder.setAggLists(aggregateBook);
		return placeOrder;
	}

//	//login, authenticates against database
//	@PostMapping("/login")
//	public String newLogin(@Valid @RequestBody Login login) {
//		List<String> usernames = accountService.getAllAccounts();
//		List<String> passwords = accountService.getAllPasswords();
//		boolean auth = authlogin.authenticate(usernames, passwords, login);
//		int token = (login.getUsername()+login.getPassword()).hashCode();
//		if (auth) {
//			return "Success! Token: " + token;
//		} else {
//			return "Incorrect details, please try again.";
//		}
//	}


	//retrieves all the orders from the database
	@GetMapping("/allOrders")
	private List<Orders> getAllOrders()
	{
		return orderService.getAllOrders();
	}

	//retrieves all the usernames from the database
	@GetMapping("/allAccounts")
	private List<String> getAllLogins()
	{
		return accountService.getAllAccounts();
	}

//	//retrieves the detail of a specific account
//	@GetMapping("/account/{id}")
//	private Login getStudent(@PathVariable("id") int id)
//	{
//		return accountService.getAccountById(id);
//	}
//
//	//deletes a specific account
//	@DeleteMapping("/account/{id}")
//	private void deleteAccount(@PathVariable("id") int id)
//	{
//		accountService.delete(id);
//	}

	//post account to the database
	@PostMapping("/database")
	public String saveAccount(@Valid @RequestBody Login login) {
		if (accountService.getAllAccounts().contains(login.getUsername())) {
			return "Username already in use, please try again.";
		}else{
			accountService.saveOrUpdate(login);
			String user = login.getUsername();
			return "New account created, username: " + user;
		}
	}

	//initial login
	@PostMapping("user")
	public String initialLogin(@Valid @RequestBody Login login) {
		List<String> usernames = accountService.getAllAccounts();
		List<String> passwords = accountService.getAllPasswords();
		boolean auth = authlogin.authenticate(usernames, passwords, login);
		if (auth) {
			userToAccount=login.getUsername();
			String token = getJWTToken(login.getUsername());
			return token;
		}else{
			return "invalid";
		}

	}

	//used in initial login
	private String getJWTToken(String username) {
		String secretKey = "mySecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER");

		String token = Jwts
				.builder()
				.setId("softtekJWT")
				.setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();

		return "Bearer " + token;
	}


}
