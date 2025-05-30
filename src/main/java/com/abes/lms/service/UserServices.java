package com.abes.lms.service;
import com.abes.lms.dto.BookDTO;
import java.util.List;

public interface UserServices {
    void borrowBook(String username, String title);
    void returnBook(String username, String title);
    List<BookDTO> viewBooks();
    List<BookDTO> sortBooksById();
    List<BookDTO> sortBooksByRating();
    List<BookDTO> sortBooksByTitle();
}
