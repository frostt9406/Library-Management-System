package com.abes.lms.service;

import com.abes.lms.dao.BookDAO;
import com.abes.lms.dao.BookDaoImpl;
import com.abes.lms.dao.LibrarianDAO;
import com.abes.lms.dao.LibrarianDAOImpl;
import com.abes.lms.dto.BookDTO;
import com.abes.lms.dto.LibrarianDTO;
import com.abes.lms.util.CollectionUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LibrarianServiceImplTest {

    private BookServices bookService;
    private LibrarianDAO librarianDAO;
    private LibrarianServiceImpl librarianService;

    private List<BookDTO> bookList;
    private List<LibrarianDTO> librarianList;

    @BeforeEach
    public void setup() {
        bookList = CollectionUtil.getBookList();
        librarianList = CollectionUtil.getLibarianList();

        bookList.clear();        // Reset shared book collection
        librarianList.clear();   // Reset shared librarian collection

        // Add test data
        bookList.add(new BookDTO("Clean Code", "Robert C. Martin", 101, 4.8, 5));
        bookList.add(new BookDTO("Effective Java", "Joshua Bloch", 102, 4.7, 4));

        librarianList.add(new LibrarianDTO("lib", "lib", "lib@lib.com"));

        // Real implementations
        BookDAO bookDAO = new BookDaoImpl();
        bookService = new BookServiceImpl(bookDAO);
        librarianDAO = new LibrarianDAOImpl();
        librarianService = new LibrarianServiceImpl(bookService, librarianDAO);
    }

    @Test
    public void testLibrarianLogin_valid() {
        boolean result = librarianService.LibrarianLogin("lib", "lib");
        assertTrue(result);
    }

    @Test
    public void testLibrarianLogin_invalid_incorrectPassword() {
        boolean result = librarianService.LibrarianLogin("lib", "wrongPassword");
        assertFalse(result);
    }

    @Test
    public void testLibrarianLogin_invalid_nonExistingLibrarian() {
        boolean result = librarianService.LibrarianLogin("nonExistentLibrarian", "somePassword");
        assertFalse(result);
    }

    @Test
    public void testRemoveBook_valid() {
        boolean result = librarianService.removeBook("Effective Java");
        assertTrue(result);
        assertNull(bookService.getBookByTitle("Effective Java"));  // book should no longer exist
    }

    @Test
    public void testRemoveBook_invalid() {
        boolean result = librarianService.removeBook("Non-Existent Book");
        assertFalse(result);
    }

    @Test
    public void testIsBookPresent_valid() {
        boolean result = librarianService.isBookPresent("Clean Code");
        assertTrue(result);
    }

    @Test
    public void testIsBookPresent_invalid() {
        boolean result = librarianService.isBookPresent("Some Unknown Book");
        assertFalse(result);
    }
}
