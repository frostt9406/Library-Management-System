package com.abes.lms.service.implementation;

import com.abes.lms.dto.BookDTO;
import com.abes.lms.dto.LibrarianDTO;
import com.abes.lms.dao.LibrarianDAO;
import com.abes.lms.service.BookServices;
import com.abes.lms.service.LibrarianServices;

public class LibrarianServiceImpl implements LibrarianServices {
    private final BookServices bookService;
    private LibrarianDAO librarianDAO;
    public LibrarianServiceImpl(BookServices bookService, LibrarianDAO librarianDAO) {
        this.bookService = bookService;
        this.librarianDAO = librarianDAO;
    }

    @Override
    public void LibrarianLogin(String username, String password) {
        LibrarianDTO librarian = librarianDAO.librarianLogin(username, password);
        if (librarian == null) {
            System.out.println("Invalid credentials.");
            return;
        }
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
