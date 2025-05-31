package com.abes.lms.service;

import com.abes.lms.dao.LibrarianDAO;
import com.abes.lms.dto.LibrarianDTO;

public class LibrarianServiceImpl implements LibrarianServices {
    private final BookServices bookService;
    private LibrarianDAO librarianDAO;
    public LibrarianServiceImpl(BookServices bookService, LibrarianDAO librarianDAO) {
        this.bookService = bookService;
        this.librarianDAO = librarianDAO;
    }

    @Override
    public boolean LibrarianLogin(String username, String password) {
        LibrarianDTO librarian = librarianDAO.librarianLogin(username, password);
        return librarian != null;
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
