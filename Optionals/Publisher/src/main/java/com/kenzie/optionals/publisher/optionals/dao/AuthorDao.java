package com.kenzie.optionals.publisher.optionals.dao;

import com.kenzie.optionals.publisher.optionals.models.Author;

import java.util.Map;
import java.util.Optional;

public class AuthorDao {
    private final Map<String, Author> authorByName;

    public AuthorDao(Map<String, Author> authorByName) {
        this.authorByName = authorByName;
    }

    /**
     * Does extremely convoluted calculations to find the single author
     * that corresponds to this name, whether it's their actual name or
     * one of their pseudonyms. If no author can be found using this
     * name, returns an empty Optional.
     *
     * Of course, this implementation just looks it up in the provided map.
     *
     * @param name The name to search for.
     * @return An Optional containing the Author using this name, if any.
     */
    public Optional<Author> findAuthorByName(String name) {
        return Optional.ofNullable(authorByName.get(name));
    }
}
