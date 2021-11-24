package ru.skillbox;

public final class Book {

    private final String name;
    private final String author;
    private final int countPages;
    private final int numberISBN;


    public Book(String name, String author, int countPages, int numberISBN) {
        this.name = name;
        this.author = author;
        this.countPages = countPages;
        this.numberISBN = numberISBN;
    }
}
