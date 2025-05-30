package com.abes.lms.dto;

public abstract class UserBase {
    protected String username;
    protected String password;
    protected String email;

    public UserBase(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
    public String getUsername() { return username; }


    public String getPassword() { return password; }
    public String getEmail() { return email; }
}
