package com.abes.lms.service;

import com.abes.lms.dao.BookDAO;
import com.abes.lms.dto.BookDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class BookServiceImplTest {

    private BookDAO bookDAO;
    private BookServiceImpl bookService;

    @BeforeEach
    public void setup() {
        // Mocking BookDAO dependency
        bookDAO = Mockito.mock(BookDAO.class);
        bookService = new BookServiceImpl(bookDAO);  // Injecting the mocked DAO into the service
    }

    // Test for addBook (Valid)
    @Test
    public void testAddBook_valid() {
        // Valid: Adding a book with valid data
        BookDTO newBook = new BookDTO("Effective Java", "Joshua Bloch", 102, 4.7, 4);
        Mockito.when(bookDAO.addBook(Mockito.any(BookDTO.class))).thenReturn(true); // Mocking the DAO

        boolean result = bookService.addBook(newBook.getTitle(), newBook.getAuthor(), newBook.getId(), newBook.getRating(), newBook.getQuantity());
        assertTrue(result);  // should return true when the book is added successfully
    }

    // Test for addBook (Invalid - Already present book by title)
    @Test
    public void testAddBook_invalid_existingTitle() {
        // Invalid: Adding a book with an already existing title
        BookDTO newBook = new BookDTO("Clean Code", "Robert C. Martin", 101, 4.8, 5); // Already exists in CollectionUtil
        Mockito.when(bookDAO.addBook(Mockito.any(BookDTO.class))).thenReturn(false); // Mocking the DAO

        boolean result = bookService.addBook(newBook.getTitle(), newBook.getAuthor(), newBook.getId(), newBook.getRating(), newBook.getQuantity());
        assertFalse(result);  // should return false because the book already exists
    }

    // Test for addBook (Edge Case - Duplicate ID)
    @Test
    public void testAddBook_edge_duplicateId() {
        // Edge: Adding a book with a duplicate ID
        BookDTO newBook = new BookDTO("Head First Java", "Kathy Sierra", 103, 4.5, 6); // ID already exists
        Mockito.when(bookDAO.addBook(Mockito.any(BookDTO.class))).thenReturn(false); // Mocking the DAO

        boolean result = bookService.addBook(newBook.getTitle(), newBook.getAuthor(), newBook.getId(), newBook.getRating(), newBook.getQuantity());
        assertFalse(result);  // should return false because the ID already exists
    }

    // Test for removeBook (Valid)
    @Test
    public void testRemoveBook_valid() {
        // Valid: Removing an existing book
        Mockito.when(bookDAO.removeBook(Mockito.anyString())).thenReturn(true);  // Mocking the DAO
        boolean result = bookService.removeBook("Effective Java");
        assertTrue(result);  // should return true when the book is successfully removed
    }

    // Test for removeBook (Invalid - Non-existing book)
    @Test
    public void testRemoveBook_invalid() {
        // Invalid: Removing a non-existing book
        Mockito.when(bookDAO.removeBook(Mockito.anyString())).thenReturn(false);  // Mocking the DAO
        boolean result = bookService.removeBook("Non-Existent Book");
        assertFalse(result);  // should return false because the book does not exist
    }

    // Test for isBookPresent (Valid)
    @Test
    public void testIsBookPresent_valid() {
        // Valid: Checking if a book exists by title
        Mockito.when(bookDAO.isBookPresent(Mockito.anyString())).thenReturn(true);  // Mocking the DAO
        boolean result = bookService.isBookPresent("Clean Code");
        assertTrue(result);  // should return true because the book is present
    }

    // Test for isBookPresent (Invalid - Non-existing book)
    @Test
    public void testIsBookPresent_invalid() {
        // Invalid: Checking for a non-existing book
        Mockito.when(bookDAO.isBookPresent(Mockito.anyString())).thenReturn(false);  // Mocking the DAO
        boolean result = bookService.isBookPresent("Non-Existent Book");
        assertFalse(result);  // should return false because the book is not present
    }

    // Test for addQuantity (Valid)
    @Test
    public void testAddQuantity_valid() {
        // Valid: Increasing quantity of a book
        BookDTO book = new BookDTO("Design Patterns", "Erich Gamma", 105, 4.9, 2);
        Mockito.when(bookDAO.addQuantity(Mockito.any(BookDTO.class), Mockito.anyInt())).thenReturn(true);  // Mocking the DAO

        boolean result = bookService.addQuantity(book, 3);
        assertTrue(result);  // should return true when the quantity is successfully increased
    }

    // Test for addQuantity (Invalid - Null book)
    @Test
    public void testAddQuantity_invalid_nullBook() {
        // Invalid: Passing a null book
        boolean result = bookService.addQuantity(null, 3);
        assertFalse(result);  // should return false because the book is null
    }

    // Test for getBookByTitle (Valid)
    @Test
    public void testGetBookByTitle_valid() {
        // Valid: Fetching a book by its title
        Mockito.when(bookDAO.getBookByTitle(Mockito.anyString())).thenReturn(new BookDTO("Clean Code", "Robert C. Martin", 101, 4.8, 5));  // Mocking the DAO
        BookDTO result = bookService.getBookByTitle("Clean Code");
        assertNotNull(result);  // should return a valid book object
        assertEquals("Clean Code", result.getTitle());  // should return the correct book by title
    }

    // Test for getBookByTitle (Invalid - Non-existing book)
    @Test
    public void testGetBookByTitle_invalid() {
        // Invalid: Fetching a non-existing book by its title
        Mockito.when(bookDAO.getBookByTitle(Mockito.anyString())).thenReturn(null);  // Mocking the DAO
        BookDTO result = bookService.getBookByTitle("Non-Existent Book");
        assertNull(result);  // should return null because the book does not exist
    }

    // Test for getBookById (Valid)
    @Test
    public void testGetBookById_valid() {
        // Valid: Fetching a book by its ID
        Mockito.when(bookDAO.getBookById(Mockito.anyInt())).thenReturn(new BookDTO("Java Concurrency in Practice", "Brian Goetz", 108, 4.6, 3));  // Mocking the DAO
        BookDTO result = bookService.getBookById(108);
        assertNotNull(result);  // should return a valid book object
        assertEquals(108, result.getId());  // should return the correct book by ID
    }

    // Test for getBookById (Invalid - Non-existing book)
    @Test
    public void testGetBookById_invalid() {
        // Invalid: Fetching a non-existing book by its ID
        Mockito.when(bookDAO.getBookById(Mockito.anyInt())).thenReturn(null);  // Mocking the DAO
        BookDTO result = bookService.getBookById(999);
        assertNull(result);  // should return null because the book does not exist
    }

    
}
