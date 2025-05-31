package com.abes.lms.util;
import com.abes.lms.dto.BookDTO;
import com.abes.lms.dto.LibrarianDTO;
import com.abes.lms.dto.UserDTO;
import java.util.*;

public class CollectionUtil {
//    stores all the books
    private static List<BookDTO> books = new ArrayList<>();
//    stores all the users
    private static List<UserDTO> users = new ArrayList<>();
//    stores all the librarian
    private static List<LibrarianDTO> libarian = new ArrayList<>();

//    return the list of books
    public static List<BookDTO> getBooks() {
        return books;
    }
// static data
    static {
        users.add(new UserDTO("ayush","ayush","ayush@gmail.com"));
        users.add(new UserDTO("nayab","nayab","nayab@gmail.com"));
        users.add(new UserDTO("keshav","keshav","keshav@gmail.com"));
        libarian.add(new LibrarianDTO("lib","lib","lib@lib.com"));
        books.add(new BookDTO("Clean Code", "Robert C. Martin", 101, 4.8, 5));
        books.add(new BookDTO("Effective Java", "Joshua Bloch", 102, 4.7, 4));
        books.add(new BookDTO("Head First Java", "Kathy Sierra", 103, 4.5, 6));
        books.add(new BookDTO("Java: The Complete Reference", "Herbert Schildt", 104, 4.6, 3));
        books.add(new BookDTO("Design Patterns", "Erich Gamma", 105, 4.9, 2));
        books.add(new BookDTO("Introduction to Algorithms", "Thomas H. Cormen", 106, 4.4, 5));
        books.add(new BookDTO("The Pragmatic Programmer", "Andrew Hunt", 107, 4.8, 4));
        books.add(new BookDTO("Java Concurrency in Practice", "Brian Goetz", 108, 4.6, 3));
        books.add(new BookDTO("Spring in Action", "Craig Walls", 109, 4.3, 4));
        books.add(new BookDTO("Java Performance", "Charlie Hunt", 110, 4.2, 2));

    }

//    getter methods for book, user, librarian
    public static List<BookDTO> getBookList() { return books; }
    public static List<UserDTO> getUserList() { return users; }
    public static List<LibrarianDTO> getLibarianList() { return libarian; }
}


