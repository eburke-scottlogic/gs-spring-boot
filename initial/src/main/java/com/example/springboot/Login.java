package com.example.springboot;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table
public class Login {
    @Id
    @Column
    @NotNull
    @Min(1)
    private int id;

    @Column
    @NotNull
    @Size(min=1)
    private String username;

    @Column
    @NotNull
    @Size(min=1)
    private String password;

    public Login () {}

    public Login(int id, String username, String password){
        this.id=id;
        this.username=username;
        this.password=password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
