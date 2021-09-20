package com.example.springboot;

import com.example.springboot.repository.AccountRepository;
import com.example.springboot.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthLogin {

    @Autowired
    AccountRepository accountRepository;

    //hardcode data

    public boolean authenticate(List<Integer> ids, List<String> usernames, List<String> passwords, Login login) {
        for(int i = 0; i < usernames.size(); i++) {
            if (ids.get(i) == login.getId()) {
                if (usernames.get(i).equals(login.getUsername())) {
                    if (passwords.get(i).equals(login.getPassword())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
