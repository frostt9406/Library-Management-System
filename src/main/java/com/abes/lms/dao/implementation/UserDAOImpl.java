package com.abes.lms.dao.implementation;

import com.abes.lms.dao.UserDAO;
import com.abes.lms.dto.UserDTO;
import com.abes.lms.util.CollectionUtil;

import java.util.List;
public class UserDAOImpl implements UserDAO {

    private final List<UserDTO> users = CollectionUtil.getUserList();

    @Override
    public UserDTO userLogin(String username, String password) {
        return users.stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(username)
                        && user.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean userRegister(UserDTO user) {
        if (getUser(user.getUsername()) != null) {
            return false; // User already exists
        }
        return users.add(user);
    }

    @Override
    public UserDTO getUser(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElse(null);
    }
}
