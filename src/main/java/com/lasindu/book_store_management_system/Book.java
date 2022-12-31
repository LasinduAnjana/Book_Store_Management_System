package com.lasindu.book_store_management_system;

public class Book {
    private String isbn;
    private String name;
    private String author;
    private String category;
    private int copies;
    private Double price;

    public Book(String isbn, String name, String author, String category, int copies, Double price) {
        this.isbn = isbn;
        this.name = name;
        this.author = author;
        this.category = category;
        this.copies = copies;
        this.price = price;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public int getCopies() {
        return copies;
    }

    public Double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

}
