package com.example.loginpf.Model;

public class Client {
    private String name;
    private String username;
    private String password;
    private String id;
    private String account;
    private Double cash;
    private static int accountCounter = 0;


    public Client() {
    }

    public Client(String name,String username, String password,String id, String account, Double cash) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.id = id;
        this.account = account;
        this.cash = cash;

    }

    public Client(String username, String password) {
        this.username = username;
        this.password = password;
        this.account = generateAccount();
        this.cash = 0.0;
    }

    private String generateAccount() {
        accountCounter++;
        return String.format("%04d", accountCounter);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }


    public Double getCash() {
        return cash;
    }

    public void setCash(Double cash) {
        this.cash = cash;
    }

    @Override
    public String toString() {
        return "Cliente";
    }
}