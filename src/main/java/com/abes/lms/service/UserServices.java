package com.abes.lms.service;
import com.abes.lms.dto.BookDTO;
import com.abes.lms.dto.UserDTO;

import java.util.List;

public interface UserServices {
    boolean userRegister(String username,String password,String email);
    void userLogin(String username,String password);
    void borrowBook(String username, String title);
    void returnBook(String username, String title);
    UserDTO getUser(String username);
    List<BookDTO> viewBooks();
    List<BookDTO> sortBooksById();
    List<BookDTO> sortBooksByRating();
    List<BookDTO> sortBooksByTitle();

}
