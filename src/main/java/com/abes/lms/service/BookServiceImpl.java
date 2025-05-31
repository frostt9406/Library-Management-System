package com.abes.lms.service;

import com.abes.lms.dao.BookDAO;
import com.abes.lms.dto.BookDTO;
import com.abes.lms.util.CollectionUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BookServiceImpl implements BookServices {
    public BookDAO bookDAO;
    public BookServiceImpl(BookDAO bookDAO) {
        setBookDAO(bookDAO);
    }
    public void setBookDAO(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

        @Override
    public boolean addBook(String title,String author,int id,double rating,int quantity) {
        BookDTO book = new BookDTO(title, author, id, rating, quantity);
        return bookDAO.addBook(book);
    }

    @Override
    public boolean removeBook(String title) {
        return CollectionUtil.getBooks().removeIf(book -> book.getTitle().equalsIgnoreCase(title));
    }

    @Override
    public boolean isBookPresent(String title) {
        return CollectionUtil.getBooks().stream().anyMatch(book -> book.getTitle().equalsIgnoreCase(title));
    }

    @Override
    public boolean isBookPresentById(int id) {
        return CollectionUtil.getBooks().stream().anyMatch(book-> book.getId() == id );
    }

    @Override
    public boolean addQuantity(BookDTO book, int quantity) {
        return bookDAO.addQuantity(book, quantity);
    }

    @Override
    public List<BookDTO> getAllBooks() {
        return new ArrayList<>(CollectionUtil.getBooks());
    }

    @Override
    public BookDTO getBookByTitle(String title) {
        return CollectionUtil.getBooks().stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }

    @Override
    public BookDTO getBookById(int id) {
        return CollectionUtil.getBooks().stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<BookDTO> sortBooksById() {
        return CollectionUtil.getBooks().stream()
                .sorted(Comparator.comparingInt(BookDTO::getId))
                .toList();
    }

    @Override
    public List<BookDTO> sortBooksByRating() {
        return CollectionUtil.getBooks().stream()
                .sorted(Comparator.comparingDouble(BookDTO::getRating).reversed())
                .toList();
    }

    @Override
    public List<BookDTO> sortBooksByTitle() {
        return CollectionUtil.getBooks().stream()
                .sorted(Comparator.comparing(BookDTO::getTitle))
                .toList();
    }
}
