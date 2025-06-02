package com.abes.lms.dto;

/**
 * UserBase is an abstract base class for common user properties such as username, password, and email.
 * It is extended by specific user types like LibrarianDTO and UserDTO.
 */
public abstract class UserBase {

    // Common fields shared across all user types
    protected String username;
    protected String password;
    protected String email;

    //Constructs a UserBase object with the specified username, password, and email.
    public UserBase(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
    public String getUsername() { return username; }


    public String getPassword() { return password; }
    public String getEmail() { return email; }
}
