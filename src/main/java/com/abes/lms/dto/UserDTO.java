package com.abes.lms.dto;
import java.util.HashSet;
import java.util.Set;


public class UserDTO extends UserBase {
    private Set<Integer> borrowedBookIds = new HashSet<>();

    public UserDTO(String username, String password, String email) {
        super(username, password, email);
    }

    public Set<Integer> getBorrowedBookIds() { return borrowedBookIds; }

    public void borrowBook(int bookId) { borrowedBookIds.add(bookId); }
    public void returnBook(int bookId) { borrowedBookIds.remove(bookId); }

    @Override
    public String toString() {
        return "User[" + username + ", Email=" + email + ", BorrowedBookIds=" + borrowedBookIds + "]";
    }
}
