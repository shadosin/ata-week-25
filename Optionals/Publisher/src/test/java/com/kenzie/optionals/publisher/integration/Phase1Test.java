package com.kenzie.optionals.publisher.integration;

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

public class Phase1Test {

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
    public void authorDao_findAuthorByName_authorExists_returnsOptionalWithAuthor() {
        // GIVEN + WHEN + THEN
        assertEquals(testAuthor, testAuthorDao.findAuthorByName("name").get(),
            String.format("Expected AuthorDao findAuthorByName with \"name\" to return [%s], but got [%s]!",
                Optional.of(testAuthor), testAuthorDao.findAuthorByName("name")));
    }

    @Test
    public void authorDao_findAuthorByName_authorDoesNotExist_returnsEmptyOptional() {
        // GIVEN
        testAuthorMap = ImmutableMap.of();
        testAuthorDao = new AuthorDao(testAuthorMap);

        // WHEN + THEN
        assertFalse(testAuthorDao.findAuthorByName("name").isPresent(),
            String.format("Expected AuthorDao findAuthorByName with \"name\" to return empty Optional, but got [%s]!",
                testAuthorDao.findAuthorByName("name")));
    }

    @Test
    public void author_getBestRatedBook_bookExists_returnsOptionalWithBook() {
        // GIVEN + WHEN + THEN
        assertEquals(testBook, testAuthor.getBestRatedBook().get(),
            String.format("Expected Author [%s] getBestRatedBook to return [%s], but got [%s]!",
                testAuthor, Optional.of(testBook), testAuthor.getBestRatedBook()));
    }

    @Test
    public void author_getBestRatedBook_bookDoesNotExist_returnsEmptyOptional() {
        // GIVEN
        testAuthor = new Author("name", "id", ImmutableList.of(), ImmutableList.of());

        // WHEN + THEN
        assertFalse(testAuthor.getBestRatedBook().isPresent(),
            String.format("Expected Author [%s] getBestRatedBook to return empty Optional, but got [%s]!",
                testAuthor, testAuthor.getBestRatedBook()));
    }

    @Test
    public void book_getWeightedRating_ratingsExist_returnsOptionalWithWeightedRating() {
        // GIVEN + WHEN + THEN
        assertEquals(1d, testBook.getWeightedRating().get(),
            String.format("Expected Book [%s] getWeightedRating to return [%s], but got [%s]!",
                testBook, Optional.of(1d), testBook.getWeightedRating()));
    }

    @Test
    public void book_getWeightedRating_ratingsDoNotExist_returnsEmptyOptional() {
        // GIVEN
        testBook = new Book("isbn", "title", ImmutableList.of(testPrinting), ImmutableList.of());

        // WHEN + THEN
        assertFalse(testBook.getWeightedRating().isPresent(),
            String.format("Expected Book [%s] getWeightedRating to return empty Optional, but got [%s]!",
                testBook, testBook.getWeightedRating()));
    }

    @Test
    public void book_getPaperback_paperbackEditionExists_returnsOptionalWithPaperbackPrinting() {
        // GIVEN + WHEN + THEN
        assertEquals(testPrinting, testBook.getPaperback().get(),
            String.format("Expected Book [%s] getPaperback to return [%s], but got [%s]!",
                testBook, Optional.of(testPrinting), testBook.getPaperback()));
    }

    @Test
    public void book_getPaperback_paperbackEditionDoesNotExist_returnsEmptyOptional() {
        // GIVEN
        testBook = new Book("isbn", "title", ImmutableList.of(), ImmutableList.of(1));

        // WHEN + THEN
        assertFalse(testBook.getPaperback().isPresent(),
            String.format("Expected Book [%s] getPaperback to return empty Optional, but got [%s]!",
                testBook, testBook.getPaperback()));
    }

    @Test
    public void printing_getPublisher_publisherExists_returnsOptionalWithPublisher() {
        // GIVEN + WHEN + THEN
        assertEquals(testPublisher, testPrinting.getPublisher().get(),
            String.format("Expected Printing [%s] getPublisher to [%s], but got [%s]!",
                testPrinting, Optional.of(testPublisher), testPrinting.getPublisher()));
    }

    @Test
    public void printing_getPublisher_publisherDoesNotExist_returnsEmptyOptional() {
        // GIVEN
        testPrinting = new Printing(PrintingType.PAPERBACK, null, new Date());

        // WHEN + THEN
        assertFalse(testPrinting.getPublisher().isPresent(),
            String.format("Expected Printing [%s] getPublisher to return empty Optional, but got [%s]!",
                testPrinting, testPrinting.getPublisher()));
    }
}
