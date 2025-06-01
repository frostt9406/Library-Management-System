package com.abes.lms.dao;

import com.abes.lms.dto.LibrarianDTO;
import com.abes.lms.util.CollectionUtil;

import java.util.List;
import java.util.Objects;

/**
 * Implementation of the LibrarianDAO interface.
 * Handles authentication of librarians using an in-memory list.
 */
public class LibrarianDAOImpl implements LibrarianDAO {

    // In-memory list of librarian users, initialized from CollectionUtil
    private final List<LibrarianDTO> users = CollectionUtil.getLibarianList();

    //Authenticates a librarian by checking if the provided username and password match any entry in the users list.
    @Override
    public LibrarianDTO librarianLogin(String username, String password) {
        return users.stream()
                .filter(Objects::nonNull) // avoid NullPointerException
                .filter(lib -> lib.getUsername().equalsIgnoreCase(username) && lib.getPassword().equals(password)) // case-insensitive username check, exact password match
                .findFirst()
                .orElse(null); // return null if no match is found
    }
}
