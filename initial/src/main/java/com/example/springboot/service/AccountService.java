package com.example.springboot.service;

import com.example.springboot.repository.AccountRepository;
import com.example.springboot.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public void saveOrUpdate(Login login) {
        accountRepository.save(login);
    }

}
