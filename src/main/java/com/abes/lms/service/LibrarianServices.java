package com.abes.lms.service;

public interface LibrarianServices {
    boolean LibrarianLogin(String username, String password);
    boolean removeBook(String title);
    boolean isBookPresent(String title);
}
