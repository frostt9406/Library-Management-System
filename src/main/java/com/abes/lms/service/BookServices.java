package com.abes.lms.service;

import com.abes.lms.dto.BookDTO;
import java.util.List;

/**
 * Interface defining operations related to book management
 * in the Library Management System.
 */
public interface BookServices {
    boolean addBook(String title,String author,int id,double rating,int quantity);
    boolean removeBook(String title);
    boolean isBookPresent(String title);
    boolean isBookPresentById(int id);
    boolean addQuantity(BookDTO book,int quantity);
    List<BookDTO> getAllBooks();
    BookDTO getBookByTitle(String title);
    BookDTO getBookById(int id);
    List<BookDTO> sortBooksById();
    List<BookDTO> sortBooksByRating();
    List<BookDTO> sortBooksByTitle();
}
