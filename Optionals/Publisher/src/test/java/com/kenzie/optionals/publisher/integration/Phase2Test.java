package com.kenzie.optionals.publisher.integration;

import com.kenzie.optionals.publisher.optionals.GetPublisherOfBestRatedPaperbackForAuthorActivity;
import com.kenzie.optionals.publisher.optionals.dao.AuthorDao;
import com.kenzie.optionals.publisher.optionals.models.Author;
import com.kenzie.optionals.publisher.optionals.models.Book;
import com.kenzie.optionals.publisher.optionals.models.Printing;
import com.kenzie.optionals.publisher.optionals.models.PrintingType;
import com.kenzie.optionals.publisher.optionals.models.Publisher;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class Phase2Test {

    private Publisher testPublisher;
    private Printing testPrinting;
    private Book testBook;
    private Author testAuthor;
    private Map<String, Author> testAuthorMap;
    private AuthorDao testAuthorDao;

    @BeforeEach
    public void setup() {
        testPublisher = new Publisher("id", "name", "contact");
        testPrinting = new Printing(PrintingType.PAPERBACK, testPublisher, new Date());
        testBook = new Book("isbn", "title", ImmutableList.of(testPrinting), ImmutableList.of(1));
        testAuthor = new Author("name", "id", ImmutableList.of(), ImmutableList.of(testBook));
        testAuthorMap = ImmutableMap.of("name", testAuthor);
        testAuthorDao = new AuthorDao(testAuthorMap);
    }

    @Test
    public void getPublisherOfBestRatedPaperbackForAuthorActivity_handleRequest_noAuthor_returnsEmptyOptional() {
        // GIVEN
        testAuthorMap = ImmutableMap.of();
        testAuthorDao = new AuthorDao(testAuthorMap);

        GetPublisherOfBestRatedPaperbackForAuthorActivity activity =
            new GetPublisherOfBestRatedPaperbackForAuthorActivity(testAuthorDao);

        // WHEN + THEN
        assertFalse(activity.handleRequest("name").isPresent(),
            String.format("Expected handleRequest to return empty Optional when no author exists, but got [%s]!",
                activity.handleRequest("name")));
    }

    @Test
    public void getPublisherOfBestRatedPaperbackForAuthorActivity_handleRequest_noBook_returnsEmptyOptional() {
        // GIVEN
        testAuthor = new Author("name", "id", ImmutableList.of(), ImmutableList.of());
        testAuthorMap = ImmutableMap.of("name", testAuthor);
        testAuthorDao = new AuthorDao(testAuthorMap);

        GetPublisherOfBestRatedPaperbackForAuthorActivity activity =
            new GetPublisherOfBestRatedPaperbackForAuthorActivity(testAuthorDao);

        // WHEN + THEN
        assertFalse(activity.handleRequest("name").isPresent(),
            String.format("Expected handleRequest to return empty Optional when no best rated book exists, but got [%s]!",
                activity.handleRequest("name")));
    }

    @Test
    public void getPublisherOfBestRatedPaperbackForAuthorActivity_handleRequest_noPrinting_returnsEmptyOptional() {
        // GIVEN
        testBook = new Book("isbn", "title", ImmutableList.of(), ImmutableList.of(1));
        testAuthor = new Author("name", "id", ImmutableList.of(), ImmutableList.of(testBook));
        testAuthorMap = ImmutableMap.of("name", testAuthor);
        testAuthorDao = new AuthorDao(testAuthorMap);

        GetPublisherOfBestRatedPaperbackForAuthorActivity activity =
            new GetPublisherOfBestRatedPaperbackForAuthorActivity(testAuthorDao);

        // WHEN + THEN
        assertFalse(activity.handleRequest("name").isPresent(),
            String.format("Expected handleRequest to return empty Optional when no paperback printing exists, but got [%s]!",
                activity.handleRequest("name")));
    }

    @Test
    public void getPublisherOfBestRatedPaperbackForAuthorActivity_handleRequest_nullPublisher_returnsEmptyOptional() {
        // GIVEN
        testPublisher = null;
        testPrinting = new Printing(PrintingType.PAPERBACK, testPublisher, new Date());
        testBook = new Book("isbn", "title", ImmutableList.of(testPrinting), ImmutableList.of(1));
        testAuthor = new Author("name", "id", ImmutableList.of(), ImmutableList.of(testBook));
        testAuthorMap = ImmutableMap.of("name", testAuthor);
        testAuthorDao = new AuthorDao(testAuthorMap);

        GetPublisherOfBestRatedPaperbackForAuthorActivity activity =
            new GetPublisherOfBestRatedPaperbackForAuthorActivity(testAuthorDao);

        // WHEN + THEN
        assertFalse(activity.handleRequest("name").isPresent(),
            String.format("Expected handleRequest to return empty Optional when no publisher exists, but got [%s]!",
                activity.handleRequest("name")));
    }

    @Test
    public void getPublisherOfBestRatedPaperbackForAuthorActivity_handleRequest_allAttributesExist_returnsOptionalOfPublisher() {
        // GIVEN
        GetPublisherOfBestRatedPaperbackForAuthorActivity activity =
            new GetPublisherOfBestRatedPaperbackForAuthorActivity(testAuthorDao);

        // WHEN + THEN
        assertEquals(testPublisher, activity.handleRequest("name").get(),
            String.format("Expected handleRequest to return [%s] when all attributes exist, but got [%s]!",
                Optional.of(testPublisher), activity.handleRequest("name")));
    }
}
