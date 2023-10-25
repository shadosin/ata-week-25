package com.kenzie.optionals.publisher.nullchecks.models;

import java.util.List;


/*

<-----------------------            DO NOT MODIFY THIS CLASS!            -------------------------------->

This class is the model to show how the original methods work.

The class to modify is in the "optionals" folder below, not this folder (nullchecks)

*/


/**
 * Represents an author's publishing history by name.
 * Only the books published under this author's name are included.
 * Books published under other pseudonyms will be in their own Authors.
 */
public class Author {
    private final String name;
    private final String id;
    private final List<Author> pseudonyms;
    private final List<Book> books;

    public Author(String name, String id, List<Author> pseudonyms, List<Book> books) {
        this.name = name;
        this.id = id;
        this.pseudonyms = pseudonyms;
        this.books = books;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public List<Author> getPseudonyms() {
        return pseudonyms;
    }

    /**
     * Finds the highest rated book this author has published.
     * @return The author's highest rated book, or null if no book has been rated.
     */
    public Book getBestRatedBook() {
        Book highestRatedBook = null;
        Double highestRating = -1.0;
        for (Book book : books) {
            Double rating = book.getWeightedRating();
            if (rating != null && rating > highestRating) {
                highestRating = rating;
                highestRatedBook = book;
            }
        }
        return highestRatedBook;
    }
}
