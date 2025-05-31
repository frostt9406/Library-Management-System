package com.abes.lms.service;

import com.abes.lms.dao.UserDAO;
import com.abes.lms.dto.BookDTO;
import com.abes.lms.dto.UserDTO;
import com.abes.lms.util.CollectionUtil;

import java.util.*;

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

@Override
public boolean userRegister(String username,String password,String email) {
    UserDTO user = new UserDTO(username, password, email);
    userDAO.userRegister(user);
    return true;
}
@Override
public boolean userLogin(String username,String password) {
        UserDTO user = userDAO.userLogin(username,password);
    return user!=null;
}
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
@Override
public void returnBook(String username, String title) {
    List<BookDTO> borrowed = CollectionUtil.getUserBorrowedBooks().get(username);
    BookDTO findBook=null;
    for(BookDTO book :borrowed){
        if(book.getTitle().equals(title)){
            findBook=book;
            break;
            // Use your DTO method
        }
    }
    if(findBook==null) return;
    findBook.increaseQuantity();
    borrowed.remove(findBook);
    CollectionUtil.getUserBorrowedBooks().put(username, borrowed);


//    if (borrowed != null && borrowed.remove(title)) {
//        BookDTO book = bookService.getBookByTitle(title);
//        if (book != null) {
//            book.increaseQuantity(); // Use your DTO method
//        }
//        System.out.println(username + " returned " + title);
//    } else {
//        System.out.println("Book not found in " + username + "'s borrowed list.");
//    }
}

    @Override
    public UserDTO getUser(String username) {
        return userDAO.getUser(username);
    }
@Override
    public List<UserDTO> getAllUsers() {
        return new ArrayList<>(CollectionUtil.getUserList());
    }

    @Override
    public void borrowedBookByEachUser(){
        System.out.println(CollectionUtil.getUserBorrowedBooks());
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
