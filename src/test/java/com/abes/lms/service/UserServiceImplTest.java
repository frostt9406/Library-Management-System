package com.abes.lms.service;

import com.abes.lms.dao.UserDAO;
import com.abes.lms.dto.BookDTO;
import com.abes.lms.dto.UserDTO;
import com.abes.lms.util.CollectionUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceImplTest {

    private BookServices bookService;
    private UserDAO userDAO;
    private UserServiceImpl userService;

    @BeforeEach
    public void setup() {
        // Mocking the BookServices and UserDAO dependencies
        bookService = Mockito.mock(BookServices.class);
        userDAO = Mockito.mock(UserDAO.class);

        // Creating the service with mocked dependencies
        userService = new UserServiceImpl(bookService, userDAO);
    }

    // Test for userRegister (Valid)
    @Test
    public void testUserRegister_valid() {
        // Valid: User registration
        String username = "john_doe";
        String password = "password123";
        String email = "john_doe@example.com";

        boolean result = userService.userRegister(username, password, email);
        assertTrue(result);  // should return true because registration is successful
    }

    // Test for userLogin (Valid)
    @Test
    public void testUserLogin_valid() {
        // Valid: Correct username and password
        String username = "john_doe";
        String password = "password123";
        UserDTO mockUser = new UserDTO(username, password, "john_doe@example.com");

        Mockito.when(userDAO.userLogin(username, password)).thenReturn(mockUser);  // Mocking userDAO to return a valid user

        boolean result = userService.userLogin(username, password);
        assertTrue(result);  // should return true because login is successful
    }

    // Test for userLogin (Invalid)
    @Test
    public void testUserLogin_invalid() {
        // Invalid: Incorrect password
        String username = "john_doe";
        String password = "wrongPassword";

        Mockito.when(userDAO.userLogin(username, password)).thenReturn(null);  // Mocking invalid login

        boolean result = userService.userLogin(username, password);
        assertFalse(result);  // should return false because the login is invalid
    }

    // Test for borrowBook (Valid)
    @Test
    public void testBorrowBook_valid() {
        // Valid: Borrowing a book that exists and is available
        String username = "john_doe";
        String title = "Clean Code";
        BookDTO book = new BookDTO(title, "Robert C. Martin", 101, 4.8, 5); // book with quantity > 0
        Mockito.when(bookService.getBookByTitle(title)).thenReturn(book);  // Mocking bookService to return the book

        userService.borrowBook(username, title);

        // Since the book is available, the borrowBook method should reduce the quantity of the book
        assertEquals(4, book.getQuantity());  // quantity should have decreased by 1
    }

    // Test for borrowBook (Invalid - Book out of stock)
    @Test
    public void testBorrowBook_invalid_outOfStock() {
        // Invalid: Borrowing a book that is out of stock
        String username = "john_doe";
        String title = "Effective Java";
        BookDTO book = new BookDTO(title, "Joshua Bloch", 102, 4.7, 0); // book with quantity 0
        Mockito.when(bookService.getBookByTitle(title)).thenReturn(book);  // Mocking bookService to return the book

        userService.borrowBook(username, title);

        // The book is out of stock, so no quantity change should occur
        assertEquals(0, book.getQuantity());  // quantity should remain the same (0)
    }

    // Test for returnBook (Valid)
    @Test
    public void testReturnBook_valid() {
        // Valid: Returning a book
        String username = "john_doe";
        String title = "Clean Code";
        BookDTO book = new BookDTO(title, "Robert C. Martin", 101, 4.8, 5); // book with quantity > 0
        List<BookDTO> borrowedBooks = new ArrayList<>();
        borrowedBooks.add(book);

        // Mocking the borrowed books for the user
        Mockito.when(bookService.getBookByTitle(title)).thenReturn(book);  // Mocking bookService
        Mockito.when(CollectionUtil.getUserBorrowedBooks()).thenReturn(new HashMap<>() {{
            put(username, borrowedBooks);
        }});

        userService.returnBook(username, title);

        // After returning the book, the quantity should be increased by 1
        assertEquals(6, book.getQuantity());  // quantity should have increased by 1
        assertTrue(borrowedBooks.isEmpty());  // the borrowed books list should be empty for this user
    }

    // Test for returnBook (Invalid - Book not borrowed)
    @Test
    public void testReturnBook_invalid_notBorrowed() {
        // Invalid: Returning a book that the user hasn't borrowed
        String username = "john_doe";
        String title = "Head First Java";
        List<BookDTO> borrowedBooks = new ArrayList<>(); // user has no borrowed books

        // Mocking the borrowed books for the user
        Mockito.when(CollectionUtil.getUserBorrowedBooks()).thenReturn(new HashMap<>() {{
            put(username, borrowedBooks);
        }});

        userService.returnBook(username, title);

        // No book should be returned, the list remains empty
        assertTrue(borrowedBooks.isEmpty());  // borrowed books should still be empty
    }

    // Test for getUser (Valid)
    @Test
    public void testGetUser_valid() {
        // Valid: Getting a user that exists
        String username = "john_doe";
        UserDTO mockUser = new UserDTO(username, "password123", "john_doe@example.com");

        Mockito.when(userDAO.getUser(username)).thenReturn(mockUser);  // Mocking userDAO to return a valid user

        UserDTO result = userService.getUser(username);
        assertNotNull(result);  // should return a non-null user
        assertEquals(username, result.getUsername());  // the returned user should match the expected username
    }

    // Test for getUser (Invalid - Non-existing user)
    @Test
    public void testGetUser_invalid() {
        // Invalid: Getting a user that doesn't exist
        String username = "non_existent_user";

        Mockito.when(userDAO.getUser(username)).thenReturn(null);  // Mocking userDAO to return null for non-existing user

        UserDTO result = userService.getUser(username);
        assertNull(result);  // should return null because the user doesn't exist
    }

    // Test for getAllUsers (Valid)
    @Test
    public void testGetAllUsers_valid() {
        // Valid: Getting all users
        List<UserDTO> mockUsers = List.of(new UserDTO("john_doe", "password123", "john_doe@example.com"));
        Mockito.when(userDAO.getAllUsers()).thenReturn(mockUsers);  // Mocking userDAO to return a list of users

        List<UserDTO> result = userService.getAllUsers();
        assertNotNull(result);  // should return a non-null list of users
        assertFalse(result.isEmpty());  // the list should not be empty
    }

    // Test for viewBooks (Valid)
    @Test
    public void testViewBooks_valid() {
        // Valid: Viewing all books
        List<BookDTO> mockBooks = List.of(new BookDTO("Clean Code", "Robert C. Martin", 101, 4.8, 5));
        Mockito.when(bookService.getAllBooks()).thenReturn(mockBooks);  // Mocking bookService to return a list of books

        List<BookDTO> result = userService.viewBooks();
        assertNotNull(result);  // should return a non-null list of books
        assertFalse(result.isEmpty());  // the list should not be empty
    }
}
