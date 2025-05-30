package com.abes.lms.service.implementation;

import com.abes.lms.dto.BookDTO;
import com.abes.lms.service.BookServices;
import com.abes.lms.util.CollectionUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BookServiceImpl implements BookServices {
    private int nextId = 1;

    @Override
    public boolean addBook(BookDTO book) {
        book.setId(nextId++);
        return CollectionUtil.getBooks().add(book);
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
