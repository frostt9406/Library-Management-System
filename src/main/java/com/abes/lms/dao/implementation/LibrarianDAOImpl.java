package com.abes.lms.dao.implementation;

import com.abes.lms.dao.LibrarianDAO;
import com.abes.lms.dto.LibrarianDTO;
import com.abes.lms.util.CollectionUtil;

import java.util.List;

public class LibrarianDAOImpl implements LibrarianDAO {

    private final List<LibrarianDTO> users = CollectionUtil.getLibarianList();

    @Override
    public LibrarianDTO librarianLogin(String username, String password) {
        return users.stream()
                .filter(user -> user instanceof LibrarianDTO)
                .map(user -> (LibrarianDTO) user)
                .filter(lib -> lib.getUsername().equalsIgnoreCase(username) && lib.getPassword().equals(password))
                .findFirst()
                .orElse(null); // return null if not found
    }
}

