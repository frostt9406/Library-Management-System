package com.abes.lms.util;
import com.abes.lms.dto.BookDTO;
import com.abes.lms.dto.LibrarianDTO;
import com.abes.lms.dto.UserDTO;
import java.util.*;

public class CollectionUtil {
    private static List<BookDTO> books = new ArrayList<>();
    private static List<UserDTO> users = new ArrayList<>();
    private static List<LibrarianDTO> libarian = new ArrayList<>();

    public static List<BookDTO> getBookList() { return books; }
    public static List<UserDTO> getUserList() { return users; }
    public static List<LibrarianDTO> getLibarianList() { return libarian; }
}


