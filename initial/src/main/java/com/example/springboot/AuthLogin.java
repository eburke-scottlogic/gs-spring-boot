package com.example.springboot;

import org.springframework.stereotype.Component;

@Component
public class AuthLogin {

    private String[] usernames = {"user1", "user2", "user3"};
    private String[] passwords = {"password1", "password2", "password3"};


    public boolean authenticate(String username, String password) {
        for(int i = 0; i < usernames.length; i++) {
            if (usernames[i].equals(username)) {
                if (passwords[i].equals(password)) {
                    return true;
                }
            }
        }
        return false;
    }
}
