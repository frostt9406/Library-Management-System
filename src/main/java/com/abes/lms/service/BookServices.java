package com.abes.lms.service;

import com.abes.lms.dto.BookDTO;
import java.util.List;

public interface BookServices {
    boolean addBook(BookDTO book);
    boolean removeBook(String title);
    boolean isBookPresent(String title);
    List<BookDTO> getAllBooks();
    BookDTO getBookByTitle(String title);
    BookDTO getBookById(int id);
    List<BookDTO> sortBooksById();
    List<BookDTO> sortBooksByRating();
    List<BookDTO> sortBooksByTitle();
}
