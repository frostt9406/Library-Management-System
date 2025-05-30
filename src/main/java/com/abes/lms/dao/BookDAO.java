package com.abes.lms.dao;

import com.abes.lms.dto.BookDTO;
import java.util.List;
public interface BookDAO {
    boolean addBook(BookDTO book);
    boolean removeBook(String title);
    boolean isBookPresent(String title);
    List<BookDTO> getAllBooks();
    BookDTO getBookByTitle(String title);
    BookDTO getBookById(int id);
}
