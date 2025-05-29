package com.abes.lms.dto;

import java.util.HashSet;
import java.util.Set;

public class User extends UserBase {
    private Set<Integer> borrowedBookIds = new HashSet<>();

    public User(String username, String password) {
        super(username, password);
    }

    public Set<Integer> getBorrowedBookIds() { return borrowedBookIds; }

    public void borrowBook(int bookId) { borrowedBookIds.add(bookId); }
    public void returnBook(int bookId) { borrowedBookIds.remove(bookId); }

    @Override
    public String toString() {
        return "User[" + username + ", BorrowedBookIds=" + borrowedBookIds + "]";
    }
}
