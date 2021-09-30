package com.example.springboot;

import java.util.Arrays;

import com.example.springboot.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMapping;


@SpringBootApplication
public class Application {


	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Application.class, args);

		System.out.println("Let's inspect the beans provided by Spring Boot:");

		String[] beanNames = ctx.getBeanDefinitionNames();
		Arrays.sort(beanNames);
		for (String beanName : beanNames) {
			System.out.println(beanName);
		}
	}

	@Bean
	CommandLineRunner run(AccountService accountService){
		return args -> {
			//hardcoded data
			accountService.saveOrUpdate(new Login("user1", "password1"));
			accountService.saveOrUpdate(new Login("user2", "password2"));
			accountService.saveOrUpdate(new Login("user3", "password3"));
			accountService.saveOrUpdate(new Login("user4", "password4"));
			accountService.saveOrUpdate(new Login("user5", "password5"));
		};
	}

	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.cors().and().csrf().disable()
					.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
					.authorizeRequests()
					.antMatchers(HttpMethod.POST, "/user").permitAll()
					.antMatchers("/").permitAll()
					.antMatchers("/h2-console/**").permitAll()
					.antMatchers("/database").permitAll()
					.antMatchers("/allTrades").permitAll()
					.antMatchers("/aggBuys").permitAll()
					.antMatchers("/aggSells").permitAll()
					.antMatchers("/allAccounts").permitAll()
					.antMatchers("/allPasswords").permitAll()
					.anyRequest().authenticated();
			http.headers().frameOptions().disable();
		}


		@Bean
		PasswordEncoder passwordEncoder(){
			return new BCryptPasswordEncoder();
		}

	}

}
