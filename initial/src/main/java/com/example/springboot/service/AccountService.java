package com.example.springboot.service;

import com.example.springboot.repository.AccountRepository;
import com.example.springboot.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public List<String> getAllAccounts()
    {
        List<String> accounts = new ArrayList<>();
        accountRepository.findAll().forEach(login -> accounts.add(login.getUsername()));
        return accounts;
    }

    public List<String> getAllPasswords()
    {
        List<String> passwords = new ArrayList<>();
        accountRepository.findAll().forEach(login -> passwords.add(login.getPassword()));
        return passwords;
    }

    public Login getAccountById(int id)
    {
        return accountRepository.findById(id).get();
    }

    public void saveOrUpdate(Login login) {
        accountRepository.save(login);
    }

    public void delete(int id)
    {
        accountRepository.deleteById(id);
    }


}
