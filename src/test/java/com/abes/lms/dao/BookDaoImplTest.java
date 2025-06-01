package com.abes.lms.dao;

import com.abes.lms.dto.BookDTO;
import com.abes.lms.util.CollectionUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookDaoImplTest {

    private BookDAO bookDAO;

    @BeforeEach
    public void setup() {
        // Initializing the BookDAO implementation
        bookDAO = new BookDaoImpl();
    }

    // Test for addBook (Valid)
    @Test
    public void testAddBook_valid() {
        // Valid: Adding a book that does not already exist
        BookDTO book = new BookDTO("Clean Code", "Robert C. Martin", 101, 4.8, 5);
        boolean result = bookDAO.addBook(book);

        assertTrue(result);  // The book should be added successfully
        assertNotNull(bookDAO.getBookById(101));  // The book should now be retrievable by its ID
    }

    // Test for addBook (Invalid - Duplicate Title)
    @Test
    public void testAddBook_invalid_duplicateTitle() {
        // Invalid: Adding a book with a title that already exists
        BookDTO book1 = new BookDTO("Clean Code", "Robert C. Martin", 101, 4.8, 5);
        BookDTO book2 = new BookDTO("Clean Code", "Robert C. Martin", 102, 4.9, 10);

        bookDAO.addBook(book1);  // Adding the first book
        boolean result = bookDAO.addBook(book2);  // Trying to add a book with the same title

        assertFalse(result);  // The book should not be added because the title is duplicate
    }

    // Test for addBook (Invalid - Duplicate ID)
    @Test
    public void testAddBook_invalid_duplicateId() {
        // Invalid: Adding a book with an ID that already exists
        BookDTO book1 = new BookDTO("Clean Code", "Robert C. Martin", 101, 4.8, 5);
        BookDTO book2 = new BookDTO("Effective Java", "Joshua Bloch", 101, 4.7, 3);

        bookDAO.addBook(book1);  // Adding the first book
        boolean result = bookDAO.addBook(book2);  // Trying to add a book with the same ID

        assertFalse(result);  // The book should not be added because the ID is duplicate
    }

    // Test for removeBook (Valid)
    @Test
    public void testRemoveBook_valid() {
        // Valid: Removing a book that exists
        BookDTO book = new BookDTO("Clean Code", "Robert C. Martin", 101, 4.8, 5);
        bookDAO.addBook(book);  // Adding the book to the list

        boolean result = bookDAO.removeBook("Clean Code");  // Removing the book
        assertTrue(result);  // The book should be removed successfully
        assertNull(bookDAO.getBookByTitle("Clean Code"));  // The book should no longer be in the list
    }

    // Test for removeBook (Invalid - Book Not Found)
    @Test
    public void testRemoveBook_invalid_notFound() {
        // Invalid: Trying to remove a book that does not exist
        boolean result = bookDAO.removeBook("Non Existent Book");
        assertFalse(result);  // The removal should fail because the book doesn't exist
    }

    // Test for isBookPresent (Valid)
    @Test
    public void testIsBookPresent_valid() {
        // Valid: Checking if a book exists in the list
        BookDTO book = new BookDTO("Clean Code", "Robert C. Martin", 101, 4.8, 5);
        bookDAO.addBook(book);  // Adding the book to the list

        boolean result = bookDAO.isBookPresent("Clean Code");
        assertTrue(result);  // The book should be found
    }

    // Test for isBookPresent (Invalid - Book Not Found)
    @Test
    public void testIsBookPresent_invalid_notFound() {
        // Invalid: Checking for a book that doesn't exist
        boolean result = bookDAO.isBookPresent("Non Existent Book");
        assertFalse(result);  // The book should not be found
    }

    // Test for getAllBooks (Valid)
    @Test
    public void testGetAllBooks_valid() {
        // Valid: Retrieving all books
        BookDTO book1 = new BookDTO("Clean Code", "Robert C. Martin", 101, 4.8, 5);
        BookDTO book2 = new BookDTO("Effective Java", "Joshua Bloch", 102, 4.7, 3);
        bookDAO.addBook(book1);
        bookDAO.addBook(book2);  // Adding two books to the list

        List<BookDTO> books = bookDAO.getAllBooks();
        assertNotNull(books);  // Should return a non-null list
        assertEquals(2, books.size());  // Should contain exactly two books
    }

    // Test for getBookByTitle (Valid)
    @Test
    public void testGetBookByTitle_valid() {
        // Valid: Retrieving a book by its title
        BookDTO book = new BookDTO("Clean Code", "Robert C. Martin", 101, 4.8, 5);
        bookDAO.addBook(book);  // Adding the book to the list

        BookDTO result = bookDAO.getBookByTitle("Clean Code");
        assertNotNull(result);  // The book should be found
        assertEquals("Clean Code", result.getTitle());  // The book title should match
    }

    // Test for getBookByTitle (Invalid - Book Not Found)
    @Test
    public void testGetBookByTitle_invalid_notFound() {
        // Invalid: Trying to retrieve a book that does not exist
        BookDTO result = bookDAO.getBookByTitle("Non Existent Book");
        assertNull(result);  // The book should not be found, so it should return null
    }

    // Test for getBookById (Valid)
    @Test
    public void testGetBookById_valid() {
        // Valid: Retrieving a book by its ID
        BookDTO book = new BookDTO("Clean Code", "Robert C. Martin", 101, 4.8, 5);
        bookDAO.addBook(book);  // Adding the book to the list

        BookDTO result = bookDAO.getBookById(101);
        assertNotNull(result);  // The book should be found
        assertEquals(101, result.getId());  // The book ID should match
    }

    // Test for getBookById (Invalid - Book Not Found)
    @Test
    public void testGetBookById_invalid_notFound() {
        // Invalid: Trying to retrieve a book by an ID that does not exist
        BookDTO result = bookDAO.getBookById(999);
        assertNull(result);  // The book should not be found, so it should return null
    }

    // Test for addQuantity (Valid)
    @Test
    public void testAddQuantity_valid() {
        // Valid: Adding quantity to a book
        BookDTO book = new BookDTO("Clean Code", "Robert C. Martin", 101, 4.8, 5);
        bookDAO.addBook(book);  // Adding the book to the list

        boolean result = bookDAO.addQuantity(book, 5);  // Adding 5 more copies
        assertTrue(result);  // The operation should succeed
        assertEquals(10, book.getQuantity());  // The quantity should now be 10
    }

    // Test for addQuantity (Invalid - Null Book)
    @Test
    public void testAddQuantity_invalid_nullBook() {
        // Invalid: Adding quantity to a null book
        boolean result = bookDAO.addQuantity(null, 5);
        assertFalse(result);  // The operation should fail because the book is null
    }
}