package com.abes.lms.dao;
import com.abes.lms.dto.UserDTO;
public interface UserDAO {
    UserDTO userLogin(String username, String password);
    boolean userRegister(UserDTO user);
    UserDTO getUser(String username);
}
