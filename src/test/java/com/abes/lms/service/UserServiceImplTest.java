package com.abes.lms.service;

import com.abes.lms.dao.BookDAO;
import com.abes.lms.dao.BookDaoImpl;
import com.abes.lms.dao.UserDAO;
import com.abes.lms.dao.UserDAOImpl;
import com.abes.lms.dto.BookDTO;
import com.abes.lms.dto.UserDTO;
import com.abes.lms.util.CollectionUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceImplTest {

    private BookServices bookService;
    private UserDAO userDAO;
    private UserServiceImpl userService;

    private List<UserDTO> userList;
    private List<BookDTO> bookList;

    @BeforeEach
    public void setup() {
        // Reset shared collections
        userList = CollectionUtil.getUserList();
        bookList = CollectionUtil.getBookList();
        userList.clear();
        bookList.clear();

        // Add test users
        userList.add(new UserDTO("john_doe", "password123", "john_doe@example.com"));

        // Add test books
        bookList.add(new BookDTO("Clean Code", "Robert C. Martin", 101, 4.8, 5));
        bookList.add(new BookDTO("Effective Java", "Joshua Bloch", 102, 4.7, 0)); // Out of stock

        // Instantiate real service classes
        BookDAO bookDAO = new BookDaoImpl();
        bookService = new BookServiceImpl(bookDAO);
        userDAO = new UserDAOImpl();
        userService = new UserServiceImpl(bookService, userDAO);
    }

    @Test
    public void testUserRegister_valid() {
        boolean result = userService.userRegister("jane_doe", "securePass", "jane@example.com");
        assertTrue(result);

        UserDTO user = userDAO.getUser("jane_doe");
        assertNotNull(user);
        assertEquals("jane@example.com", user.getEmail());
    }

    @Test
    public void testUserLogin_valid() {
        boolean result = userService.userLogin("john_doe", "password123");
        assertTrue(result);
    }

    @Test
    public void testUserLogin_invalid() {
        boolean result = userService.userLogin("john_doe", "wrongPassword");
        assertFalse(result);
    }

    @Test
    public void testBorrowBook_valid() {
        BookDTO book = bookService.getBookByTitle("Clean Code");
        int originalQty = book.getQuantity();

        userService.borrowBook("john_doe", "Clean Code");

        assertEquals(originalQty - 1, book.getQuantity());
    }

    @Test
    public void testBorrowBook_invalid_outOfStock() {
        BookDTO book = bookService.getBookByTitle("Effective Java");
        int originalQty = book.getQuantity();

        userService.borrowBook("john_doe", "Effective Java");

        assertEquals(originalQty, book.getQuantity());
    }

    @Test
    public void testGetUser_valid() {
        UserDTO result = userService.getUser("john_doe");
        assertNotNull(result);
        assertEquals("john_doe", result.getUsername());
    }

    @Test
    public void testGetUser_invalid() {
        UserDTO result = userService.getUser("ghost_user");
        assertNull(result);
    }

    @Test
    public void testGetAllUsers_valid() {
        List<UserDTO> users = userService.getAllUsers();
        assertNotNull(users);
        assertFalse(users.isEmpty());
    }

    @Test
    public void testViewBooks_valid() {
        List<BookDTO> books = userService.viewBooks();
        assertNotNull(books);
        assertFalse(books.isEmpty());
    }
}
