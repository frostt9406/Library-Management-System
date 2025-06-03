package com.abes.lms.dao;

import com.abes.lms.dto.UserDTO;
import com.abes.lms.util.CollectionUtil;

import java.util.List;

/**
 * Implementation of the UserDAO interface.
 * Manages user authentication, registration, and retrieval using in-memory data.
 */
public class UserDAOImpl implements UserDAO {

    // In-memory list of users fetched from CollectionUtil
    private final List<UserDTO> users = CollectionUtil.getUserList();

    //Authenticates a user using provided username and password.
    @Override
    public UserDTO userLogin(String username, String password) {
        return users.stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(username)
                        && user.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    //Registers a new user by adding them to the list if not already present.
    @Override
    public boolean userRegister(UserDTO user) {
        if (getUser(user.getUsername()) != null) {
            return false; // User already exists
        }
        return users.add(user);
    }

    //Retrieves a user based on their username.
    @Override
    public UserDTO getUser(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElse(null);
    }

    //Retrieves all registered users.
    @Override
    public List<UserDTO> getAllUsers() {
        return users;
    }
}
