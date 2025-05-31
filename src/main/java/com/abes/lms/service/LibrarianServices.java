package com.abes.lms.service;

public interface LibrarianServices {
    void LibrarianLogin(String username, String password);
    boolean removeBook(String title);
    boolean isBookPresent(String title);
}
