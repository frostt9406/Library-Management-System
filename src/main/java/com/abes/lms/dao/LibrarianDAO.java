package com.abes.lms.dao;

import com.abes.lms.dto.LibrarianDTO;
public interface LibrarianDAO {
//    abstract method for librarian login
    LibrarianDTO librarianLogin(String username, String password);
}
