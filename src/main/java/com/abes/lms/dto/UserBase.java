package com.abes.lms.dto;

public abstract class UserBase {
    protected String username;
    protected String password;

    public UserBase(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
}
