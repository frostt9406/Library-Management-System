package com.abes.lms.service;

import com.abes.lms.dao.BookDAO;
import com.abes.lms.dto.BookDTO;
import com.abes.lms.util.CollectionUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Implementation of the BookServices interface.
 * Handles business logic for managing books.
 */
public class BookServiceImpl implements BookServices {

    // DAO instance to perform database operations (in this case, dummy operations).
    public BookDAO bookDAO;

    //Constructor to inject BookDAO dependency.
    public BookServiceImpl(BookDAO bookDAO) {
        setBookDAO(bookDAO);
    }

    //setter for bookDAO
    public void setBookDAO(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    //Adds a new book to the system
        @Override
    public boolean addBook(String title,String author,int id,double rating,int quantity) {
        BookDTO book = new BookDTO(title, author, id, rating, quantity);
        return bookDAO.addBook(book);
    }

    //removes borrowed book from the system
    @Override
    public boolean removeBook(String title) {
        return bookDAO.removeBook(title);
    }

    //check if book is present in the system using the title(case insensitive)
    @Override
    public boolean isBookPresent(String title) {
        return CollectionUtil.getBookList().stream().anyMatch(book -> book.getTitle().equalsIgnoreCase(title));
    }

    //check if book is present in the system using the id
    @Override
    public boolean isBookPresentById(int id) {
        return CollectionUtil.getBookList().stream().anyMatch(book-> book.getId() == id );
    }

    //adds quantity to an existing book
    @Override
    public boolean addQuantity(BookDTO book, int quantity) {
        return bookDAO.addQuantity(book, quantity);
    }

    //Returns all books as a new list to prevent external modification.
    @Override
    public List<BookDTO> getAllBooks() {
        return new ArrayList<>(CollectionUtil.getBookList());
    }

    //Retrieves a book by title (case-insensitive)
    @Override
    public BookDTO getBookByTitle(String title) {
        return CollectionUtil.getBookList().stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }

    // Retrieves a book by its ID.
    @Override
    public BookDTO getBookById(int id) {
        return CollectionUtil.getBookList().stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElse(null);
    }

    //Sorts and returns the book list by ID in ascending order.
    @Override
    public List<BookDTO> sortBooksById() {
        return CollectionUtil.getBookList().stream()
                .sorted(Comparator.comparingInt(BookDTO::getId))
                .toList();
    }

    //Sorts and returns the book list by rating in descending order.
    @Override
    public List<BookDTO> sortBooksByRating() {
        return CollectionUtil.getBookList().stream()
                .sorted(Comparator.comparingDouble(BookDTO::getRating).reversed())
                .toList();
    }

    //Sorts and returns the book list alphabetically by title.
    @Override
    public List<BookDTO> sortBooksByTitle() {
        return CollectionUtil.getBookList().stream()
                .sorted(Comparator.comparing(BookDTO::getTitle))
                .toList();
    }
}
