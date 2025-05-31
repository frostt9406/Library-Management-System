package com.abes.lms.dao;

import com.abes.lms.dto.BookDTO;
import com.abes.lms.util.CollectionUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookDaoImplTest {

    private BookDaoImpl bookDao;

    @BeforeEach
    void setup() {
        // Clear the shared list before each test
        CollectionUtil.getBookList().clear();
        bookDao = new BookDaoImpl();
    }

    // -------- addBook --------

    @Test
    void addBook_HappyPath_ShouldAddBook() {
        BookDTO book = new BookDTO("Clean Architecture", "Robert C. Martin", 1, 4.8, 5);
        boolean added = bookDao.addBook(book);

        assertTrue(added);
        assertEquals(1, CollectionUtil.getBookList().size());
        assertSame(book, CollectionUtil.getBookList().get(0));
    }

    @Test
    void addBook_ValidEdge_DuplicateTitleShouldNotAdd() {
        BookDTO book1 = new BookDTO("Refactoring", "Martin Fowler", 1, 4.5, 4);
        BookDTO book2 = new BookDTO("Refactoring", "Another Author", 2, 4.0, 3);
        bookDao.addBook(book1);
        boolean added = bookDao.addBook(book2);

        assertFalse(added);
        assertEquals(1, CollectionUtil.getBookList().size());
    }

    @Test
    void addBook_ValidEdge_DuplicateIdShouldNotAdd() {
        BookDTO book1 = new BookDTO("Domain Driven Design", "Eric Evans", 1, 4.6, 6);
        BookDTO book2 = new BookDTO("Some Other Book", "Author", 1, 3.5, 2);
        bookDao.addBook(book1);
        boolean added = bookDao.addBook(book2);

        assertFalse(added);
        assertEquals(1, CollectionUtil.getBookList().size());
    }

    @Test
    void addBook_Invalid_NullBook() {
        assertThrows(NullPointerException.class, () -> bookDao.addBook(null));
    }

    // -------- removeBook --------

    @Test
    void removeBook_HappyPath_ShouldRemoveBook() {
        BookDTO book = new BookDTO("Clean Code", "Robert Martin", 1, 4.7, 3);
        bookDao.addBook(book);

        boolean removed = bookDao.removeBook("Clean Code");

        assertTrue(removed);
        assertTrue(CollectionUtil.getBookList().isEmpty());
    }

    @Test
    void removeBook_ValidEdge_TitleCaseInsensitive() {
        BookDTO book = new BookDTO("Patterns of Enterprise", "Martin Fowler", 1, 4.3, 2);
        bookDao.addBook(book);

        boolean removed = bookDao.removeBook("patterns OF enterprise");

        assertTrue(removed);
        assertTrue(CollectionUtil.getBookList().isEmpty());
    }

    @Test
    void removeBook_Invalid_BookNotFound() {
        boolean removed = bookDao.removeBook("Non Existent Book");
        assertFalse(removed);
    }

    // -------- isBookPresent --------

    @Test
    void isBookPresent_HappyPath_ReturnsTrueIfPresent() {
        BookDTO book = new BookDTO("Effective Java", "Joshua Bloch", 1, 4.9, 7);
        bookDao.addBook(book);

        assertTrue(bookDao.isBookPresent("Effective Java"));
    }

    @Test
    void isBookPresent_ValidEdge_TitleCaseInsensitive() {
        BookDTO book = new BookDTO("Java Concurrency in Practice", "Brian Goetz", 1, 4.7, 5);
        bookDao.addBook(book);

        assertTrue(bookDao.isBookPresent("java concurrency IN practice"));
    }

    @Test
    void isBookPresent_Invalid_ReturnsFalseIfAbsent() {
        assertFalse(bookDao.isBookPresent("Unknown Book"));
    }

    // -------- getAllBooks --------

    @Test
    void getAllBooks_HappyPath_ReturnsAllBooks() {
        BookDTO book1 = new BookDTO("Book A", "Author A", 1, 3.0, 1);
        BookDTO book2 = new BookDTO("Book B", "Author B", 2, 4.0, 2);
        bookDao.addBook(book1);
        bookDao.addBook(book2);

        List<BookDTO> allBooks = bookDao.getAllBooks();

        assertEquals(2, allBooks.size());
        assertTrue(allBooks.contains(book1));
        assertTrue(allBooks.contains(book2));
    }

    @Test
    void getAllBooks_ValidEdge_EmptyList() {
        List<BookDTO> allBooks = bookDao.getAllBooks();

        assertNotNull(allBooks);
        assertTrue(allBooks.isEmpty());
    }

    // -------- getBookByTitle --------

    @Test
    void getBookByTitle_HappyPath_ReturnsCorrectBook() {
        BookDTO book = new BookDTO("Test Driven Development", "Kent Beck", 1, 4.4, 3);
        bookDao.addBook(book);

        BookDTO found = bookDao.getBookByTitle("Test Driven Development");

        assertNotNull(found);
        assertEquals(book.getTitle(), found.getTitle());
    }

    @Test
    void getBookByTitle_ValidEdge_TitleCaseInsensitive() {
        BookDTO book = new BookDTO("Clean Code", "Robert Martin", 1, 4.7, 5);
        bookDao.addBook(book);

        BookDTO found = bookDao.getBookByTitle("clean CODE");

        assertNotNull(found);
        assertEquals(book.getTitle(), found.getTitle());
    }

    @Test
    void getBookByTitle_Invalid_NotFound() {
        BookDTO found = bookDao.getBookByTitle("Non Existing Title");
        assertNull(found);
    }

    // -------- getBookById --------

    @Test
    void getBookById_HappyPath_ReturnsCorrectBook() {
        BookDTO book = new BookDTO("Microservices Patterns", "Chris Richardson", 1, 4.1, 3);
        bookDao.addBook(book);

        BookDTO found = bookDao.getBookById(1);

        assertNotNull(found);
        assertEquals(book.getId(), found.getId());
    }

    @Test
    void getBookById_ValidEdge_NonExistingId() {
        BookDTO found = bookDao.getBookById(999);
        assertNull(found);
    }

    @Test
    void getBookById_Invalid_NegativeId() {
        BookDTO found = bookDao.getBookById(-1);
        assertNull(found);
    }
}