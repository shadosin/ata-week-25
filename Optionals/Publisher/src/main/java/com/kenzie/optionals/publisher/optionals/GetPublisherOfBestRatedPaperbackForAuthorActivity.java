package com.kenzie.optionals.publisher.optionals;

import com.kenzie.optionals.publisher.optionals.dao.AuthorDao;
import com.kenzie.optionals.publisher.optionals.models.Publisher;

import java.util.Optional;

public class GetPublisherOfBestRatedPaperbackForAuthorActivity {
    private final AuthorDao authorDao;

    public GetPublisherOfBestRatedPaperbackForAuthorActivity(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    /**
     * Finds the publisher of the latest paperback version of the
     * named author's highest-rated book. If there is no author by that name,
     * or they have no books, or none of their books have been rated, or there
     * is no paperback version, or the paperback was not published by a company,
     * returns empty.
     * @param authorName The name of the author to search for.
     * @return An Optional containing the publisher of the latest
     * paperback version of the named author's highest-rated book, if any.
     */
    public Optional<Publisher> handleRequest(String authorName) {
        if (authorName == null) {
            throw new IllegalArgumentException("Author must not be null!");
        }

        // Your code goes here

        return Optional.empty();
    }

}
