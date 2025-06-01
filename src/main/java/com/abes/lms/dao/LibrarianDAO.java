package com.abes.lms.dao;

import com.abes.lms.dto.LibrarianDTO;


/**
 * Data Access Object interface for Librarian operations.
 * Defines authentication-related methods for librarians.
 */
public interface LibrarianDAO {
    LibrarianDTO librarianLogin(String username, String password);
}
