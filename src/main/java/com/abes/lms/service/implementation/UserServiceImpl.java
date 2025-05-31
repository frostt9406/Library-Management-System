package com.abes.lms.service.implementation;

import com.abes.lms.dto.BookDTO;
import com.abes.lms.service.BookServices;
import com.abes.lms.service.UserServices;

import java.util.*;

public class UserServiceImpl implements UserServices {
    private final BookServices bookService;
    private final Map<String, List<String>> userBorrowedBooks = new HashMap<>();

    public UserServiceImpl(BookServices bookService) {
        this.bookService = bookService;
    }


@Override
public void borrowBook(String username, String title) {
    BookDTO book = bookService.getBookByTitle(title);
    if (book != null && book.getQuantity() > 0) {
        book.decreaseQuantity(); // Use your DTO method
        userBorrowedBooks
                .computeIfAbsent(username, k -> new ArrayList<>())
                .add(title);
        System.out.println(username + " borrowed " + title);
    } else {
        System.out.println("Book not available or out of stock.");

    }
}
@Override
public void returnBook(String username, String title) {
    List<String> borrowed = userBorrowedBooks.get(username);
    if (borrowed != null && borrowed.remove(title)) {
        BookDTO book = bookService.getBookByTitle(title);
        if (book != null) {
            book.increaseQuantity(); // Use your DTO method
        }
        System.out.println(username + " returned " + title);
    } else {
        System.out.println("Book not found in " + username + "'s borrowed list.");
    }
}


    @Override
    public List<BookDTO> viewBooks() {
        return bookService.getAllBooks();
    }

    @Override
    public List<BookDTO> sortBooksById() {
        return bookService.sortBooksById();
    }

    @Override
    public List<BookDTO> sortBooksByRating() {
        return bookService.sortBooksByRating();
    }

    @Override
    public List<BookDTO> sortBooksByTitle() {
        return bookService.sortBooksByTitle();
    }
}
