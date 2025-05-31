package com.abes.lms.dto;

package com.abes.lms.dao;

import com.abes.lms.dao.LibrarianDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LibrarianDaoImplTest {

    private LibrarianDAO librarianDAO;

    @BeforeEach
    void setUp() {
        librarianDAO = new LibrarianDAOImpl();
    }

    // Happy Path
    @Test
    void addAndRetrieveValidLibrarian() {
        LibrarianDTO librarian = new LibrarianDTO("libA", "securePass");
        assertTrue(librarianDAO.addLibrarian(librarian));

        LibrarianDTO result = librarianDAO.getLibrarian("libA");
        assertNotNull(result);
        assertEquals("libA", result.getUsername());
    }

    // Invalid: Duplicate username
    @Test
    void addDuplicateLibrarianShouldFail() {
        LibrarianDTO lib1 = new LibrarianDTO("libB", "abc");
        LibrarianDTO lib2 = new LibrarianDTO("libB", "xyz");

        librarianDAO.addLibrarian(lib1);
        boolean addedAgain = librarianDAO.addLibrarian(lib2);

        assertFalse(addedAgain);
    }

    // Invalid: Null input
    @Test
    void addNullLibrarianShouldThrowException() {
        assertThrows(NullPointerException.class, () -> librarianDAO.addLibrarian(null));
    }

    // Invalid: Get non-existent librarian
    @Test
    void getNonExistentLibrarianShouldReturnNull() {
        LibrarianDTO result = librarianDAO.getLibrarian("nonExistentLib");
        assertNull(result);
    }
}
