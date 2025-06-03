package com.abes.lms.dao;

import com.abes.lms.dto.BookDTO;
import com.abes.lms.util.CollectionUtil;

import java.util.List;

/*
* Implementation for bookDao is written here
*/
public class BookDaoImpl implements BookDAO{
    private final List<BookDTO> books = CollectionUtil.getBookList();

    //Adds a new book to the list if it doesn't already exist by title or ID.
    @Override
    public boolean addBook(BookDTO book) {
        if (isBookPresent(book.getTitle()) || getBookById(book.getId()) != null) {
            return false;
        }
        return books.add(book);
    }

    //Removes a book based on the given title.
    @Override
    public boolean removeBook(String title) {
        return CollectionUtil.getBookList().removeIf(book -> book.getTitle().equalsIgnoreCase(title));
    }

    //Checks if a book with the given title exists.
    @Override
    public boolean isBookPresent(String title) {
        return books.stream()
                .anyMatch(book -> book.getTitle().equalsIgnoreCase(title));
    }

    //Retrieves the full list of books.
    @Override
    public List<BookDTO> getAllBooks() {
        return books;
    }

    //Retrieves a book by its title.
    @Override
    public BookDTO getBookByTitle(String title) {
        return books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }

    //Retrieves a book by its ID.
    @Override
    public BookDTO getBookById(int id) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElse(null);
    }

    //Increases the quantity of the specified book.
    @Override
    public boolean addQuantity(BookDTO book, int quantity) {
        if(book==null) {
            return false;
        }
        book.setQuantity(book.getQuantity() + quantity);
        return true;
    }
}