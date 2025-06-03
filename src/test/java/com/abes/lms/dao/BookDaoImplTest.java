package com.abes.lms.dao;

import com.abes.lms.dto.BookDTO;
import com.abes.lms.util.CollectionUtil;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookDaoImplTest {

    private BookDAO bookDAO;
    private List<BookDTO> bookList;

    @BeforeEach
    public void setup() {
        bookList = CollectionUtil.getBookList();
        bookList.clear(); // reset shared list before each test
        bookDAO = new BookDaoImpl();
    }

    @Test
    public void testAddBook_valid() {
        BookDTO book = new BookDTO("Clean Code", "Robert C. Martin", 101, 4.8, 5);
        boolean result = bookDAO.addBook(book);
        assertTrue(result);
        assertEquals(book, bookDAO.getBookById(101));
    }

    @Test
    public void testAddBook_invalid_duplicateTitle() {
        BookDTO book1 = new BookDTO("Clean Code", "Robert C. Martin", 101, 4.8, 5);
        BookDTO book2 = new BookDTO("Clean Code", "Robert C. Martin", 102, 4.9, 10);

        bookList.add(book1);

        boolean result = bookDAO.addBook(book2);
        assertFalse(result);
    }

    @Test
    public void testAddBook_invalid_duplicateId() {
        BookDTO book1 = new BookDTO("Clean Code", "Robert C. Martin", 101, 4.8, 5);
        BookDTO book2 = new BookDTO("Effective Java", "Joshua Bloch", 101, 4.7, 3);

        bookList.add(book1);

        boolean result = bookDAO.addBook(book2);
        assertFalse(result);
    }

    @Test
    public void testRemoveBook_valid() {
        BookDTO book = new BookDTO("Clean Code", "Robert C. Martin", 101, 4.8, 5);
        bookList.add(book);

        boolean result = bookDAO.removeBook("Clean Code");
        assertTrue(result);
        assertNull(bookDAO.getBookByTitle("Clean Code"));
    }

    @Test
    public void testRemoveBook_invalid_notFound() {
        boolean result = bookDAO.removeBook("Non Existent Book");
        assertFalse(result);
    }

    @Test
    public void testIsBookPresent_valid() {
        BookDTO book = new BookDTO("Clean Code", "Robert C. Martin", 101, 4.8, 5);
        bookList.add(book);

        boolean result = bookDAO.isBookPresent("Clean Code");
        assertTrue(result);
    }

    @Test
    public void testIsBookPresent_invalid_notFound() {
        boolean result = bookDAO.isBookPresent("Non Existent Book");
        assertFalse(result);
    }

    @Test
    public void testGetAllBooks_valid() {
        BookDTO book1 = new BookDTO("Clean Code", "Robert C. Martin", 101, 4.8, 5);
        BookDTO book2 = new BookDTO("Effective Java", "Joshua Bloch", 102, 4.7, 3);
        bookList.add(book1);
        bookList.add(book2);

        List<BookDTO> result = bookDAO.getAllBooks();
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetBookByTitle_valid() {
        BookDTO book = new BookDTO("Clean Code", "Robert C. Martin", 101, 4.8, 5);
        bookList.add(book);

        BookDTO result = bookDAO.getBookByTitle("Clean Code");
        assertNotNull(result);
        assertEquals("Clean Code", result.getTitle());
    }

    @Test
    public void testGetBookByTitle_invalid_notFound() {
        BookDTO result = bookDAO.getBookByTitle("Non Existent Book");
        assertNull(result);
    }

    @Test
    public void testGetBookById_valid() {
        BookDTO book = new BookDTO("Clean Code", "Robert C. Martin", 101, 4.8, 5);
        bookList.add(book);

        BookDTO result = bookDAO.getBookById(101);
        assertNotNull(result);
        assertEquals(101, result.getId());
    }

    @Test
    public void testGetBookById_invalid_notFound() {
        BookDTO result = bookDAO.getBookById(999);
        assertNull(result);
    }

    @Test
    public void testAddQuantity_valid() {
        BookDTO book = new BookDTO("Clean Code", "Robert C. Martin", 101, 4.8, 5);
        bookList.add(book);

        boolean result = bookDAO.addQuantity(book, 5);
        assertTrue(result);
        assertEquals(10, book.getQuantity());
    }

    @Test
    public void testAddQuantity_invalid_nullBook() {
        boolean result = bookDAO.addQuantity(null, 5);
        assertFalse(result);
    }
}
