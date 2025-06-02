package com.abes.lms.dto;


/**
 * LibrarianDTO is a Data Transfer Object that represents a librarian user in the Library Management System.
 * It extends the common user base class UserBase.
 */
public class LibrarianDTO extends UserBase {

    //Constructs a new LibrarianDTO with the specified username, password, and email.
    public LibrarianDTO(String username, String password, String email) {
        super(username, password, email);
    }

    //Returns a string representation of the LibrarianDTO object.
    @Override
    public String toString() {
        return "LibrarianDTO{" +
                "username='" + username + '\'' +
//                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
