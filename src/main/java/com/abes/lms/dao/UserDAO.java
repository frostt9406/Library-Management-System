package com.abes.lms.dao;

import com.abes.lms.dto.UserDTO;

import java.util.List;

public interface UserDAO {
//    UserDAO abstract method
    UserDTO userLogin(String username, String password);
    boolean userRegister(UserDTO user);
    UserDTO getUser(String username);
    List<UserDTO> getAllUsers();

}
