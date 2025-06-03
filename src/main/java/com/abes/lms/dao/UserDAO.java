package com.abes.lms.dao;

import com.abes.lms.dto.UserDTO;

import java.util.List;

/**
 * Data Access Object interface for User-related operations.
 * Defines authentication, registration, and retrieval methods for users.
 */
public interface UserDAO {
    UserDTO userLogin(String username, String password);
    boolean userRegister(UserDTO user);
    UserDTO getUser(String username);
    List<UserDTO> getAllUsers();

}
