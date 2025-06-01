package com.abes.lms.dao;

import com.abes.lms.dto.LibrarianDTO;
import com.abes.lms.util.CollectionUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LibrarianDAOImplTest {

    private LibrarianDAO librarianDAO;

    @BeforeEach
    public void setup() {
        // Initializing the LibrarianDAO implementation
        librarianDAO = new LibrarianDAOImpl();
    }

    // Test for librarianLogin (Valid - Correct username and password)
    @Test
    public void testLibrarianLogin_valid() {
        // Valid: Logging in with correct username and password
        LibrarianDTO librarian = new LibrarianDTO("admin", "password123");
        CollectionUtil.getLibarianList().add(librarian);  // Adding a librarian to the list

        LibrarianDTO result = librarianDAO.librarianLogin("admin", "password123");
        assertNotNull(result);  // The librarian should be found
        assertEquals("admin", result.getUsername());  // The username should match
    }

    // Test for librarianLogin (Invalid - Incorrect password)
    @Test
    public void testLibrarianLogin_invalid_wrongPassword() {
        // Invalid: Logging in with the correct username but incorrect password
        LibrarianDTO librarian = new LibrarianDTO("admin", "password123");
        CollectionUtil.getLibarianList().add(librarian);  // Adding a librarian to the list

        LibrarianDTO result = librarianDAO.librarianLogin("admin", "wrongpassword");
        assertNull(result);  // The librarian should not be found due to incorrect password
    }

    // Test for librarianLogin (Invalid - Incorrect username)
    @Test
    public void testLibrarianLogin_invalid_wrongUsername() {
        // Invalid: Logging in with an incorrect username
        LibrarianDTO librarian = new LibrarianDTO("admin", "password123");
        CollectionUtil.getLibarianList().add(librarian);  // Adding a librarian to the list

        LibrarianDTO result = librarianDAO.librarianLogin("wrongusername", "password123");
        assertNull(result);  // The librarian should not be found due to incorrect username
    }

    // Test for librarianLogin (Invalid - Both username and password incorrect)
    @Test
    public void testLibrarianLogin_invalid_bothIncorrect() {
        // Invalid: Logging in with both incorrect username and password
        LibrarianDTO librarian = new LibrarianDTO("admin", "password123");
        CollectionUtil.getLibarianList().add(librarian);  // Adding a librarian to the list

        LibrarianDTO result = librarianDAO.librarianLogin("wrongusername", "wrongpassword");
        assertNull(result);  // The librarian should not be found because both are incorrect
    }

    // Test for librarianLogin (Edge Case - Null username)
    @Test
    public void testLibrarianLogin_edge_nullUsername() {
        // Edge Case: Logging in with a null username
        LibrarianDTO librarian = new LibrarianDTO("admin", "password123");
        CollectionUtil.getLibarianList().add(librarian);  // Adding a librarian to the list

        LibrarianDTO result = librarianDAO.librarianLogin(null, "password123");
        assertNull(result);  // The librarian should not be found because the username is null
    }

    // Test for librarianLogin (Edge Case - Null password)
    @Test
    public void testLibrarianLogin_edge_nullPassword() {
        // Edge Case: Logging in with a null password
        LibrarianDTO librarian = new LibrarianDTO("admin", "password123");
        CollectionUtil.getLibarianList().add(librarian);  // Adding a librarian to the list

        LibrarianDTO result = librarianDAO.librarianLogin("admin", null);
        assertNull(result);  // The librarian should not be found because the password is null
    }

    // Test for librarianLogin (Edge Case - Both username and password null)
    @Test
    public void testLibrarianLogin_edge_nullUsernameAndPassword() {
        // Edge Case: Logging in with both null username and null password
        LibrarianDTO librarian = new LibrarianDTO("admin", "password123");
        CollectionUtil.getLibarianList().add(librarian);  // Adding a librarian to the list

        LibrarianDTO result = librarianDAO.librarianLogin(null, null);
        assertNull(result);  // The librarian should not be found because both are null
    }
}