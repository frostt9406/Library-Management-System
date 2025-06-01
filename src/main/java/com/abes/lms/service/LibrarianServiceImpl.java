package com.abes.lms.service;

import com.abes.lms.dao.LibrarianDAO;
import com.abes.lms.dto.LibrarianDTO;

/**
 * Implementation of LibrarianServices that provides
 * login authentication and book-related operations delegated to BookServices.
 */

public class LibrarianServiceImpl implements LibrarianServices {

    // Service to delegate book-related operations
    private final BookServices bookService;

    // DAO for librarian-related database interactions
    private LibrarianDAO librarianDAO;

    //Constructor to initialize BookServices and LibrarianDAO.
    public LibrarianServiceImpl(BookServices bookService, LibrarianDAO librarianDAO) {
        this.bookService = bookService;
        this.librarianDAO = librarianDAO;
    }

    //Validates librarian login using username and password.
    @Override
    public boolean LibrarianLogin(String username, String password) {
        LibrarianDTO librarian = librarianDAO.librarianLogin(username, password);
        return librarian != null;
    }

    //Removes a book by title through the BookServices.
    @Override
    public boolean removeBook(String title) {
        return bookService.removeBook(title);
    }

    //Checks if a book exists in the system by title.
    @Override
    public boolean isBookPresent(String title) {
        return bookService.isBookPresent(title);
    }
}
