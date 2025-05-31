package com.abes.lms.dao;

import com.abes.lms.dto.LibrarianDTO;
import com.abes.lms.util.CollectionUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LibrarianDAOImplTest {

    private LibrarianDAOImpl librarianDAO;

    @BeforeEach
    void setup() {
        // Clear the shared librarian list before each test for isolation
        CollectionUtil.getLibarianList().clear();
        librarianDAO = new LibrarianDAOImpl();
    }

    // -------- librarianLogin --------

    @Test
    void librarianLogin_HappyPath_ValidCredentials_ReturnsLibrarian() {
        LibrarianDTO librarian = new LibrarianDTO("libUser", "securePass", "Lib Name");
        CollectionUtil.getLibarianList().add(librarian);

        LibrarianDTO loggedIn = librarianDAO.librarianLogin("libUser", "securePass");

        assertNotNull(loggedIn);
        assertEquals(librarian.getUsername(), loggedIn.getUsername());
    }

    @Test
    void librarianLogin_ValidEdge_UsernameCaseInsensitive() {
        LibrarianDTO librarian = new LibrarianDTO("LibUser2", "pass123", "Lib Name 2");
        CollectionUtil.getLibarianList().add(librarian);

        LibrarianDTO loggedIn = librarianDAO.librarianLogin("libuser2", "pass123");

        assertNotNull(loggedIn);
        assertEquals(librarian.getUsername(), loggedIn.getUsername());
    }

    @Test
    void librarianLogin_Invalid_WrongPassword() {
        LibrarianDTO librarian = new LibrarianDTO("libUser3", "correctPass", "Lib Name 3");
        CollectionUtil.getLibarianList().add(librarian);

        LibrarianDTO loggedIn = librarianDAO.librarianLogin("libUser3", "wrongPass");

        assertNull(loggedIn);
    }

    @Test
    void librarianLogin_Invalid_UserNotFound() {
        LibrarianDTO loggedIn = librarianDAO.librarianLogin("unknownUser", "anyPass");
        assertNull(loggedIn);
    }

    @Test
    void librarianLogin_HandlesNullEntries() {
        // Add null entry deliberately to test filter(Objects::nonNull)
        CollectionUtil.getLibarianList().add(null);
        LibrarianDTO librarian = new LibrarianDTO("libUser4", "pass", "Lib Name 4");
        CollectionUtil.getLibarianList().add(librarian);

        LibrarianDTO loggedIn = librarianDAO.librarianLogin("libUser4", "pass");

        assertNotNull(loggedIn);
        assertEquals(librarian.getUsername(), loggedIn.getUsername());
    }
}