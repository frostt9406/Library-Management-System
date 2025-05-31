package com.abes.lms.service;

import com.abes.lms.dao.LibrarianDAO;
import com.abes.lms.dto.LibrarianDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class LibrarianServiceImplTest {

    private BookServices bookService;
    private LibrarianDAO librarianDAO;
    private LibrarianServiceImpl librarianService;

    @BeforeEach
    public void setup() {
        // Mocking the BookServices and LibrarianDAO dependencies
        bookService = Mockito.mock(BookServices.class);
        librarianDAO = Mockito.mock(LibrarianDAO.class);

        // Creating the service with mocked dependencies
        librarianService = new LibrarianServiceImpl(bookService, librarianDAO);
    }

    // Test for LibrarianLogin (Valid)
    @Test
    public void testLibrarianLogin_valid() {
        // Valid: Correct username and password
        String username = "lib";
        String password = "lib";
        Mockito.when(librarianDAO.librarianLogin(username, password))
                .thenReturn(new LibrarianDTO(username, password, "lib@lib.com"));  // Mocking DAO to return valid librarian

        boolean result = librarianService.LibrarianLogin(username, password);
        assertTrue(result);  // should return true because login is successful
    }

    // Test for LibrarianLogin (Invalid - Incorrect password)
    @Test
    public void testLibrarianLogin_invalid_incorrectPassword() {
        // Invalid: Incorrect password
        String username = "lib";
        String password = "wrongPassword";
        Mockito.when(librarianDAO.librarianLogin(username, password)).thenReturn(null);  // Mocking invalid login

        boolean result = librarianService.LibrarianLogin(username, password);
        assertFalse(result);  // should return false because the password is incorrect
    }

    // Test for LibrarianLogin (Invalid - Non-existing librarian)
    @Test
    public void testLibrarianLogin_invalid_nonExistingLibrarian() {
        // Invalid: Non-existing librarian
        String username = "nonExistentLibrarian";
        String password = "somePassword";
        Mockito.when(librarianDAO.librarianLogin(username, password)).thenReturn(null);  // Mocking the DAO to return null

        boolean result = librarianService.LibrarianLogin(username, password);
        assertFalse(result);  // should return false because librarian doesn't exist
    }

    // Test for removeBook (Valid)
    @Test
    public void testRemoveBook_valid() {
        // Valid: Removing an existing book
        String title = "Effective Java";
        Mockito.when(bookService.removeBook(title)).thenReturn(true);  // Mocking bookService to return true

        boolean result = librarianService.removeBook(title);
        assertTrue(result);  // should return true because the book was successfully removed
    }

    // Test for removeBook (Invalid - Non-existing book)
    @Test
    public void testRemoveBook_invalid() {
        // Invalid: Trying to remove a non-existing book
        String title = "Non-Existent Book";
        Mockito.when(bookService.removeBook(title)).thenReturn(false);  // Mocking bookService to return false

        boolean result = librarianService.removeBook(title);
        assertFalse(result);  // should return false because the book does not exist
    }

    // Test for isBookPresent (Valid)
    @Test
    public void testIsBookPresent_valid() {
        // Valid: Checking if a book exists
        String title = "Clean Code";
        Mockito.when(bookService.isBookPresent(title)).thenReturn(true);  // Mocking bookService to return true

        boolean result = librarianService.isBookPresent(title);
        assertTrue(result);  // should return true because the book is present
    }

    // Test for isBookPresent (Invalid - Non-existing book)
    @Test
    public void testIsBookPresent_invalid() {
        // Invalid: Checking if a non-existing book exists
        String title = "Non-Existent Book";
        Mockito.when(bookService.isBookPresent(title)).thenReturn(false);  // Mocking bookService to return false

        boolean result = librarianService.isBookPresent(title);
        assertFalse(result);  // should return false because the book is not present
    }
}
