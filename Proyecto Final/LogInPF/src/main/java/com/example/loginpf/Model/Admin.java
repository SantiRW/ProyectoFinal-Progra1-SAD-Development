package com.example.loginpf.Model;

public class Admin extends Person {
    private String report;

    public Admin() {
    }

    public Admin(String report) {
        this.report = report;
    }

    public String getReport() {
        return report;
    }

    @Override
    public String toString() {
        return "Admin";
    }
}
