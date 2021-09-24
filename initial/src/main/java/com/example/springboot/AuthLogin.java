package com.example.springboot;

import com.example.springboot.repository.AccountRepository;
import com.example.springboot.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthLogin {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public boolean authenticate(List<String> usernames, List<String> passwords, Login login) {
        for(int i = 0; i < usernames.size(); i++) {
                if (usernames.get(i).equals(login.getUsername())) {
                    String encoded = passwords.get(i);
                    boolean isPasswordMatch = passwordEncoder.matches(login.getPassword(), encoded);
                    if (isPasswordMatch) {
                        return true;
                    }
                }
            }
        return false;
    }
}
