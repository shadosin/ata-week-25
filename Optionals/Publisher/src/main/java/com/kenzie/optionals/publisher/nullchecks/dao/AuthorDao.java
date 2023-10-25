package com.kenzie.optionals.publisher.nullchecks.dao;

import com.kenzie.optionals.publisher.nullchecks.models.Author;

import java.util.Map;

public class AuthorDao {
    private final Map<String, Author> authorByName;

    public AuthorDao(Map<String, Author> authorByName) {
        this.authorByName = authorByName;
    }

    /**
     * Does extremely convoluted calculations to find the single author
     * that corresponds to this name, whether it's their actual name or
     * one of their pseudonyms. If no author can be found using this
     * name, returns null.
     *
     * Of course, this implementation just looks it up in the provided map.
     *
     * @param name The name to search for.
     * @return The Author using this name, if any; null otherwise.
     */
    public Author findAuthorByName(String name) {
        return authorByName.get(name);
    }
}
