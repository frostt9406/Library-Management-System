package com.abes.lms.service;

import com.abes.lms.dto.BookDTO;

public interface LibrarianServices {
    void LibrarianLogin(String username, String password);
    boolean addBook(BookDTO book);
    boolean removeBook(String title);
    boolean isBookPresent(String title);
}
