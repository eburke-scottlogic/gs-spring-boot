package com.example.springboot;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.example.springboot.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.Login;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    AuthLogin authlogin;

    @Autowired
    AccountService accountService;

    @PostMapping("user")
    public String login(@Valid @RequestBody Login login) {
        List<String> usernames = accountService.getAllAccounts();
        List<String> passwords = accountService.getAllPasswords();
        boolean auth = authlogin.authenticate(usernames, passwords, login);
        if (auth) {
            String token = getJWTToken(login.getUsername());
            login.setToken(token);
            return token;
        }else{
            return "Incorrect details, please try again.";
        }

    }

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
