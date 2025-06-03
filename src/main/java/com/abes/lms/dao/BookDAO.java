package com.abes.lms.dao;

import com.abes.lms.dto.BookDTO;
import java.util.List;

/**
 * Data Access Object interface for Book operations.
 * Defines methods for adding, removing, checking,
 * retrieving, and updating books in the data source.
 */
public interface BookDAO {
    boolean addBook(BookDTO book);
    boolean removeBook(String title);
    boolean isBookPresent(String title);
    List<BookDTO> getAllBooks();
    BookDTO getBookByTitle(String title);
    BookDTO getBookById(int id);
    boolean addQuantity(BookDTO book,int quantity);
}
