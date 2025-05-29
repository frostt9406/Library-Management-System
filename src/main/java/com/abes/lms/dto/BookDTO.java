package com.abes.lms.dto;

public class BookDTO {
    private String title;
    private String author;
    private int id;
    private double rating;

    public BookDTO(String title, String author, int id, double rating) {
        this.title = title;
        this.author = author;
        this.id = id;
        this.rating = rating;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getId() { return id; }
    public double getRating() { return rating; }

    @Override
    public String toString() {
        return String.format("Book[ID=%d, Title='%s', Author='%s', Rating=%.1f]",
                id, title, author, rating);
    }
}