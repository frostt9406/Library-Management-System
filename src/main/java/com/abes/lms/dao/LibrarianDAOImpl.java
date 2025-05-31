package com.abes.lms.dao;

import com.abes.lms.dto.LibrarianDTO;
import com.abes.lms.util.CollectionUtil;

import java.util.List;
import java.util.Objects;

public class LibrarianDAOImpl implements LibrarianDAO {

    private final List<LibrarianDTO> users = CollectionUtil.getLibarianList();

    @Override
    public LibrarianDTO librarianLogin(String username, String password) {
        return users.stream()
                .filter(Objects::nonNull)
                .filter(lib -> lib.getUsername().equalsIgnoreCase(username) && lib.getPassword().equals(password))
                .findFirst()
                .orElse(null); // return null if not found
    }
}
