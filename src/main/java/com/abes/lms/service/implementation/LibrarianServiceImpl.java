package com.abes.lms.service.implementation;

import com.abes.lms.dto.BookDTO;
import com.abes.lms.service.BookServices;
import com.abes.lms.service.LibrarianServices;

public class LibrarianServiceImpl implements LibrarianServices {
    private final BookServices bookService;

    public LibrarianServiceImpl(BookServices bookService) {
        this.bookService = bookService;
    }

    @Override
    public boolean addBook(BookDTO book) {
        return bookService.addBook(book);
    }

    @Override
    public boolean removeBook(String title) {
        return bookService.removeBook(title);
    }

    @Override
    public boolean isBookPresent(String title) {
        return bookService.isBookPresent(title);
    }
}
