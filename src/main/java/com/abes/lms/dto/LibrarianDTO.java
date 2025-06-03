package com.abes.lms.dto;

public class LibrarianDTO extends UserBase {
    public LibrarianDTO(String username, String password, String email) {
        super(username, password, email);
    }

    @Override
    public String toString() {
        return "LibrarianDTO{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
