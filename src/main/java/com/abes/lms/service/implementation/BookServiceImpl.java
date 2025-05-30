package com.abes.lms.service.implementation;

import com.abes.lms.dto.BookDTO;
import com.abes.lms.service.BookServices;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BookServiceImpl implements BookServices {
    private final List<BookDTO> books = new ArrayList<>();
    private int nextId = 1;

    @Override
    public boolean addBook(BookDTO book) {
        book.setId(nextId++);
        return books.add(book);
    }

    @Override
    public boolean removeBook(String title) {
        return books.removeIf(book -> book.getTitle().equalsIgnoreCase(title));
    }

    @Override
    public boolean isBookPresent(String title) {
        return books.stream().anyMatch(book -> book.getTitle().equalsIgnoreCase(title));
    }

    @Override
    public List<BookDTO> getAllBooks() {
        return new ArrayList<>(books);
    }

    @Override
    public BookDTO getBookByTitle(String title) {
        return books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }

    @Override
    public BookDTO getBookById(int id) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<BookDTO> sortBooksById() {
        return books.stream()
                .sorted(Comparator.comparingInt(BookDTO::getId))
                .toList();
    }

    @Override
    public List<BookDTO> sortBooksByRating() {
        return books.stream()
                .sorted(Comparator.comparingDouble(BookDTO::getRating).reversed())
                .toList();
    }

    @Override
    public List<BookDTO> sortBooksByTitle() {
        return books.stream()
                .sorted(Comparator.comparing(BookDTO::getTitle))
                .toList();
    }
}
