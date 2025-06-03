package com.abes.lms.dto;

public class UserDTO extends UserBase {

    public UserDTO(String username, String password, String email) {
        super(username, password, email);
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
