package com.abes.lms.service;

import com.abes.lms.dao.BookDAO;
import com.abes.lms.dao.BookDaoImpl;
import com.abes.lms.dto.BookDTO;
import com.abes.lms.util.CollectionUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookServiceImplTest {

    private BookDAO bookDAO;
    private BookServiceImpl bookService;
    private List<BookDTO> bookList;

    @BeforeEach
    public void setup() {
        bookList = CollectionUtil.getBookList();
        bookList.clear();  // Reset the list before each test
        bookDAO = new BookDaoImpl();  // Use real DAO
        bookService = new BookServiceImpl(bookDAO);  // Inject DAO into service
    }

    @Test
    public void testAddBook_valid() {
        boolean result = bookService.addBook("Effective Java", "Joshua Bloch", 102, 4.7, 4);
        assertTrue(result);
        assertEquals(1, bookList.size());
        assertEquals("Effective Java", bookList.get(0).getTitle());
    }

    @Test
    public void testAddBook_invalid_existingTitle() {
        bookService.addBook("Clean Code", "Robert C. Martin", 101, 4.8, 5);
        boolean result = bookService.addBook("Clean Code", "Another Author", 201, 3.9, 2);
        assertFalse(result);  // Same title
    }

    @Test
    public void testAddBook_edge_duplicateId() {
        bookService.addBook("Book A", "Author A", 103, 4.1, 5);
        boolean result = bookService.addBook("Book B", "Author B", 103, 4.2, 3);  // Duplicate ID
        assertFalse(result);
    }

    @Test
    public void testRemoveBook_valid() {
        bookService.addBook("Effective Java", "Joshua Bloch", 102, 4.7, 4);
        boolean result = bookService.removeBook("Effective Java");
        assertTrue(result);
    }

    @Test
    public void testRemoveBook_invalid() {
        boolean result = bookService.removeBook("Non-Existent Book");
        assertFalse(result);
    }

    @Test
    public void testIsBookPresent_valid() {
        bookService.addBook("Clean Code", "Robert C. Martin", 101, 4.8, 5);
        boolean result = bookService.isBookPresent("Clean Code");
        assertTrue(result);
    }

    @Test
    public void testIsBookPresent_invalid() {
        boolean result = bookService.isBookPresent("Unknown Book");
        assertFalse(result);
    }

    @Test
    public void testAddQuantity_valid() {
        bookService.addBook("Design Patterns", "Erich Gamma", 105, 4.9, 2);
        BookDTO book = bookService.getBookByTitle("Design Patterns");
        boolean result = bookService.addQuantity(book, 3);
        assertTrue(result);
        assertEquals(5, book.getQuantity());
    }

    @Test
    public void testAddQuantity_invalid_nullBook() {
        boolean result = bookService.addQuantity(null, 3);
        assertFalse(result);
    }

    @Test
    public void testGetBookByTitle_valid() {
        bookService.addBook("Clean Code", "Robert C. Martin", 101, 4.8, 5);
        BookDTO result = bookService.getBookByTitle("Clean Code");
        assertNotNull(result);
        assertEquals("Clean Code", result.getTitle());
    }

    @Test
    public void testGetBookByTitle_invalid() {
        BookDTO result = bookService.getBookByTitle("Unknown Title");
        assertNull(result);
    }

    @Test
    public void testGetBookById_valid() {
        bookService.addBook("Java Concurrency in Practice", "Brian Goetz", 108, 4.6, 3);
        BookDTO result = bookService.getBookById(108);
        assertNotNull(result);
        assertEquals(108, result.getId());
    }

    @Test
    public void testGetBookById_invalid() {
        BookDTO result = bookService.getBookById(999);
        assertNull(result);
    }
}
