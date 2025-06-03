package com.abes.lms.service;

/**
 * Interface defining the services available to a librarian in the LMS.
 * Contains methods related to authentication and book management.
 */
public interface LibrarianServices {
    boolean LibrarianLogin(String username, String password);
    boolean removeBook(String title);
    boolean isBookPresent(String title);
}
