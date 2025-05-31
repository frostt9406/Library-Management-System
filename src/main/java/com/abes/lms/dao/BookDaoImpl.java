package com.abes.lms.dao;
import com.abes.lms.dto.BookDTO;
import com.abes.lms.util.CollectionUtil;

import java.util.List;
import java.util.Optional;
public class BookDaoImpl implements BookDAO{
    private final List<BookDTO> books = CollectionUtil.getBookList();

    @Override
    public boolean addBook(BookDTO book) {
        if (isBookPresent(book.getTitle()) || getBookById(book.getId()) != null) {
            return false;
        }
        return books.add(book);
    }

    @Override
    public boolean removeBook(String title) {
        Optional<BookDTO> bookToRemove = books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .findFirst();
        return bookToRemove.map(books::remove).orElse(false);
    }

    @Override
    public boolean isBookPresent(String title) {
        return books.stream()
                .anyMatch(book -> book.getTitle().equalsIgnoreCase(title));
    }

    @Override
    public List<BookDTO> getAllBooks() {
        return books;
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
    public boolean addQuantity(BookDTO book, int quantity) {
        if(book==null) {
            return false;
        }
        book.setQuantity(book.getQuantity() + quantity);
        return true;
    }
}