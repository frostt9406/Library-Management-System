package com.abes.lms.service;

import com.abes.lms.dao.UserDAO;
import com.abes.lms.dto.BookDTO;
import com.abes.lms.dto.UserDTO;
import com.abes.lms.exception.BookNotFoundException;
import com.abes.lms.exception.InvalidInputException;
import com.abes.lms.util.CollectionUtil;

import java.util.*;

/**
 * Implementation of the UserServices interface.
 * Handles user registration, login, and book borrowing/returning operations.
 */
public class UserServiceImpl implements UserServices {
    private BookServices bookService;
    private  UserDAO userDAO;

    public UserServiceImpl(BookServices bookService, UserDAO userDAO) {
        setBookService(bookService);
        setUserDAO(userDAO);
    }
    public void setBookService(BookServices bookService){
        this.bookService=bookService;
    }
    public void setUserDAO(UserDAO userDAO){
        this.userDAO=userDAO;
    }

    // Registers a new user.
@Override
public boolean userRegister(String username,String password,String email) {
    UserDTO user = new UserDTO(username, password, email);
    userDAO.userRegister(user);
    return true;
}

//Verifies user login credentials.
@Override
public boolean userLogin(String username,String password) {
        UserDTO user = userDAO.userLogin(username,password);
    return user!=null;
}

//Allows a user to borrow a book if available.
@Override
public void borrowBook(String username, String title) {
    BookDTO book = bookService.getBookByTitle(title);
    if (book != null && book.getQuantity() > 0) {
        book.decreaseQuantity();
        CollectionUtil.getUserBorrowedBooks()
                .computeIfAbsent(username, k -> new ArrayList<>())
                .add(book);
        System.out.println(username + " borrowed " + title);
    } else {
        System.out.println("Book not available or out of stock.");
    }
}

//Allows a user to return a previously borrowed book.
@Override
public void returnBook(String username, String title) throws InvalidInputException, BookNotFoundException {
        if(title.trim().isEmpty()){
            throw new InvalidInputException("Title cannot be empty");
        }

    List<BookDTO> borrowed = CollectionUtil.getUserBorrowedBooks().getOrDefault(username,null);
    if (borrowed == null || borrowed.isEmpty()) {
        throw new BookNotFoundException("No such Book Borrowed");
    }
    BookDTO findBook = borrowed.stream()
            .filter(book -> book.getTitle().equalsIgnoreCase(title.trim()))
            .findFirst()
            .orElse(null);

    if(findBook==null){
        throw new BookNotFoundException("No such Book borrowed");
    }
    findBook.increaseQuantity();
    borrowed.remove(findBook);
    System.out.println("Book Returned");
    CollectionUtil.getUserBorrowedBooks().put(username, borrowed);
}

    //Fetches a user by username.
    @Override
    public UserDTO getUser(String username) {
        return userDAO.getUser(username);
    }

    //Returns all registered users.
@Override
    public List<UserDTO> getAllUsers() {
        return new ArrayList<>(CollectionUtil.getUserList());
    }

    //Prints the books borrowed by each user.
    @Override
    public void borrowedBookByEachUser(){
        for (Map.Entry<String, List<BookDTO>> entry : CollectionUtil.getUserBorrowedBooks().entrySet()) {
            String username = entry.getKey();
            List<BookDTO> books = entry.getValue();

            System.out.println("User: " + username);
            for (BookDTO book : books) {
                System.out.println("  Title: " + book.getTitle() + ", Author: " + book.getAuthor());
            }
        }
    }

    //Returns the full list of books available.
    @Override
    public List<BookDTO> viewBooks() {
        return bookService.getAllBooks();
    }

    //Returns a list of books sorted by ID.
    @Override
    public List<BookDTO> sortBooksById() {
        return bookService.sortBooksById();
    }

    //Returns a list of books sorted by rating.
    @Override
    public List<BookDTO> sortBooksByRating() {
        return bookService.sortBooksByRating();
    }

    //Returns a list of books sorted by title.
    @Override
    public List<BookDTO> sortBooksByTitle() {
        return bookService.sortBooksByTitle();
    }

}
