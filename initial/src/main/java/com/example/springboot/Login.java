package com.example.springboot;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Login {
        @NotNull
        @Size(min=1)
        private String username;


        @NotNull
        @Size(min=1)
        private String password;

        public Login(String username, String password){
            this.username=username;
            this.password=password;
        }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
