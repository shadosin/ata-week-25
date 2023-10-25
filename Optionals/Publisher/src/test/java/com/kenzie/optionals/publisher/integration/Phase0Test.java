package com.kenzie.optionals.publisher.integration;

import com.kenzie.optionals.publisher.nullchecks.GetPublisherOfBestRatedPaperbackForAuthorActivity;
import com.kenzie.optionals.publisher.nullchecks.dao.AuthorDao;
import com.kenzie.optionals.publisher.nullchecks.models.Author;
import com.kenzie.optionals.publisher.nullchecks.models.Book;
import com.kenzie.optionals.publisher.nullchecks.models.Printing;
import com.kenzie.optionals.publisher.nullchecks.models.PrintingType;
import com.kenzie.optionals.publisher.nullchecks.models.Publisher;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class Phase0Test {

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
    public void authorDao_findAuthorByName_authorExists_returnsAuthor() {
        // GIVEN + WHEN + THEN
        assertNotNull(testAuthorDao.findAuthorByName("name"),
            "Expected AuthorDao findAuthorByName with \"name\" to return an author, but got null!");
    }

    @Test
    public void authorDao_findAuthorByName_authorDoesNotExist_returnsNull() {
        // GIVEN
        testAuthorMap = ImmutableMap.of();
        testAuthorDao = new AuthorDao(testAuthorMap);

        // WHEN + THEN
        assertNull(testAuthorDao.findAuthorByName("name"),
            String.format("Expected AuthorDao findAuthorByName with \"name\" to return null, but got [%s]!",
                testAuthorDao.findAuthorByName("name")));
    }

    @Test
    public void author_getBestRatedBook_bookExists_returnsBook() {
        // GIVEN + WHEN + THEN
        assertNotNull(testAuthor.getBestRatedBook(),
            String.format("Expected Author [%s] getBestRatedBook to return best rated book, but got null!",
                testAuthor));
    }

    @Test
    public void author_getBestRatedBook_bookDoesNotExist_returnsNull() {
        // GIVEN
        testAuthor = new Author("name", "id", ImmutableList.of(), ImmutableList.of());

        // WHEN + THEN
        assertNull(testAuthor.getBestRatedBook(),
            String.format("Expected Author [%s] getBestRatedBook to return null, but got [%s]!",
                testAuthor, testAuthor.getBestRatedBook()));
    }

    @Test
    public void book_getWeightedRating_ratingsExist_returnsWeightedRating() {
        // GIVEN + WHEN + THEN
        assertNotNull(testBook.getWeightedRating(),
            String.format("Expected Book [%s] getWeightedRating to return weighted rating, but got null!",
                testBook));
    }

    @Test
    public void book_getWeightedRating_ratingsDoNotExist_returnsNull() {
        // GIVEN
        testBook = new Book("isbn", "title", ImmutableList.of(testPrinting), ImmutableList.of());

        // WHEN + THEN
        assertNull(testBook.getWeightedRating(),
            String.format("Expected Book [%s] getWeightedRating to return null, but got [%s]!",
                testBook, testBook.getWeightedRating()));
    }

    @Test
    public void book_getPaperback_paperbackEditionExists_returnsPaperbackPrinting() {
        // GIVEN + WHEN + THEN
        assertNotNull(testBook.getPaperback(),
            String.format("Expected Book [%s] getPaperback to return paperback printing, but got null!",
                testBook));
    }

    @Test
    public void book_getPaperback_paperbackEditionDoesNotExist_returnsNull() {
        // GIVEN
        testBook = new Book("isbn", "title", ImmutableList.of(), ImmutableList.of(1));

        // WHEN + THEN
        assertNull(testBook.getPaperback(),
            String.format("Expected Book [%s] getPaperback to return null, but got [%s]!",
                testBook, testBook.getPaperback()));
    }

    @Test
    public void printing_getPublisher_publisherExists_returnsPublisher() {
        // GIVEN + WHEN + THEN
        assertNotNull(testPrinting.getPublisher(),
            String.format("Expected Printing [%s] getPublisher to return publisher, but got null!",
                testPrinting));
    }

    @Test
    public void printing_getPublisher_publisherDoesNotExist_returnsNull() {
        // GIVEN
        testPrinting = new Printing(PrintingType.PAPERBACK, null, new Date());

        // WHEN + THEN
        assertNull(testPrinting.getPublisher(),
            String.format("Expected Printing [%s] getPublisher to return null, but got [%s]!",
                testPrinting, testPrinting.getPublisher()));
    }

    @Test
    public void getPublisherOfBestRatedPaperbackForAuthorActivity_handleRequest_noAuthor_returnsNull() {
        // GIVEN
        testAuthorMap = ImmutableMap.of();
        testAuthorDao = new AuthorDao(testAuthorMap);

        GetPublisherOfBestRatedPaperbackForAuthorActivity activity =
            new GetPublisherOfBestRatedPaperbackForAuthorActivity(testAuthorDao);

        // WHEN + THEN
        assertNull(activity.handleRequest("name"),
            "Expected handleRequest to return null when no author exists!");
    }

    @Test
    public void getPublisherOfBestRatedPaperbackForAuthorActivity_handleRequest_noBook_returnsNull() {
        // GIVEN
        testAuthor = new Author("name", "id", ImmutableList.of(), ImmutableList.of());
        testAuthorMap = ImmutableMap.of("name", testAuthor);
        testAuthorDao = new AuthorDao(testAuthorMap);

        GetPublisherOfBestRatedPaperbackForAuthorActivity activity =
            new GetPublisherOfBestRatedPaperbackForAuthorActivity(testAuthorDao);

        // WHEN + THEN
        assertNull(activity.handleRequest("name"),
            "Expected handleRequest to return null when no best rated book exists!");
    }

    @Test
    public void getPublisherOfBestRatedPaperbackForAuthorActivity_handleRequest_noPrinting_returnsNull() {
        // GIVEN
        testBook = new Book("isbn", "title", ImmutableList.of(), ImmutableList.of(1));
        testAuthor = new Author("name", "id", ImmutableList.of(), ImmutableList.of(testBook));
        testAuthorMap = ImmutableMap.of("name", testAuthor);
        testAuthorDao = new AuthorDao(testAuthorMap);

        GetPublisherOfBestRatedPaperbackForAuthorActivity activity =
            new GetPublisherOfBestRatedPaperbackForAuthorActivity(testAuthorDao);

        // WHEN + THEN
        assertNull(activity.handleRequest("name"),
            "Expected handleRequest to return null when no paperback printing exists!");
    }

    @Test
    public void getPublisherOfBestRatedPaperbackForAuthorActivity_handleRequest_nullPublisher_returnsNull() {
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
        assertNull(activity.handleRequest("name"),
            "Expected handleRequest to return null when Publisher is null!");
    }

    @Test
    public void getPublisherOfBestRatedPaperbackForAuthorActivity_handleRequest_allAttributesExist_returnsPublisher() {
        // GIVEN
        GetPublisherOfBestRatedPaperbackForAuthorActivity activity =
            new GetPublisherOfBestRatedPaperbackForAuthorActivity(testAuthorDao);

        // WHEN + THEN
        assertNotNull(activity.handleRequest("name"),
            "Expected handleRequest to return Publisher when all attributes exist!");
    }
}
