package com.abes.lms.dto;

import com.abes.lms.dao.BookDAO;
import com.abes.lms.dao.implementation.BookDaoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookDaoImplTest {

    private BookDAO bookDAO;

    @BeforeEach
    void setUp() {
        bookDAO = new BookDaoImpl();
    }

    // Happy Path
    @Test
    void addAndRetrieveValidBook() {
        BookDTO book = new BookDTO(101, "Clean Code", "Robert Martin", "Software");
        assertTrue(bookDAO.addBook(book), "Book should be added successfully");

        BookDTO result = bookDAO.getBookById(101);
        assertNotNull(result);
        assertEquals("Clean Code", result.getTitle());
    }

    // Valid Input but Duplicate ID
    @Test
    void addBookWithDuplicateIdShouldFail() {
        BookDTO book1 = new BookDTO(102, "Effective Java", "Joshua Bloch", "Java");
        BookDTO book2 = new BookDTO(102, "Head First Java", "Kathy Sierra", "Java");

        bookDAO.addBook(book1);
        boolean addedAgain = bookDAO.addBook(book2);

        assertFalse(addedAgain, "Should not allow duplicate book IDs");
    }

    // Invalid Scenario: Null Input
    @Test
    void addNullBookShouldFail() {
        assertThrows(NullPointerException.class, () -> bookDAO.addBook(null));
    }

    // Invalid Book ID
    @Test
    void getNonExistentBookShouldReturnNull() {
        BookDTO result = bookDAO.getBookById(999);
        assertNull(result, "Book with non-existent ID should return null");
    }
}