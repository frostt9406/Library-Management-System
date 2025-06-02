package com.abes.lms.service;
import com.abes.lms.dto.BookDTO;
import com.abes.lms.dto.UserDTO;
import com.abes.lms.exception.BookNotFoundException;
import com.abes.lms.exception.InvalidInputException;

import java.util.List;

/**
 * Interface defining operations related to user management and user-book interactions.
 */
public interface UserServices {
    boolean userRegister(String username,String password,String email);
    boolean userLogin(String username,String password);
    void borrowBook(String username, String title);
    void returnBook(String username, String title) throws InvalidInputException, BookNotFoundException;
    UserDTO getUser(String username);
    List<UserDTO> getAllUsers();
    void borrowedBookByEachUser();
    List<BookDTO> viewBooks();
    List<BookDTO> sortBooksById();
    List<BookDTO> sortBooksByRating();
    List<BookDTO> sortBooksByTitle();

}
