package com.abes.lms.dao;

import com.abes.lms.dto.LibrarianDTO;
public interface LibrarianDAO {
    LibrarianDTO librarianLogin(String username, String password);
}
