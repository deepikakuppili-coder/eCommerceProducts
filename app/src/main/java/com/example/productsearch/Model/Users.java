package com.example.productsearch.Model;

public class Users {
    private String name,email,password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Users(){}

    public Users(String email,String password){
        this.setEmail(email);
        this.setPassword(password);
    }
}
