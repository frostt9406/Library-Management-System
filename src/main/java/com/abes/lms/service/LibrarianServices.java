package com.abes.lms.service;

import com.abes.lms.dto.BookDTO;

public interface LibrarianServices {
    boolean addBook(BookDTO book);
    boolean removeBook(String title);
    boolean isBookPresent(String title);
}
