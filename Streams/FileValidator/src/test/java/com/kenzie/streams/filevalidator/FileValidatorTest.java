package com.kenzie.streams.filevalidator;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileValidatorTest {

    @Test
    public void validateFilesStream_processedInStream_correctResults() {
        //GIVEN
        FileValidator validator = new FileValidator();
        List<String> expectedResults = validator.validateFiles();

        //WHEN
        List<String> results = validator.validateFilesStream();

        //THEN
        assertEquals(expectedResults, results, "validateFilesStream does not have expected results!");
    }

    @Test
    public void createStream_arrayListOfItems_returnsStreamOfItems() {
        //GIVEN
        FileValidator validator = new FileValidator();
        List<String> testList = Arrays.asList("abc", "efg");

        //WHEN
        List<String> results = validator.createStream(new ArrayList<>(testList)).collect(Collectors.toList());

        //THEN
        assertEquals(testList, results, "createStream should return a Stream with all elements of the given list.");
    }

    @Test
    public void makeLowerCaseStream_performOperation_correctResults() {
        //GIVEN
        FileValidator validator = new FileValidator();
        Stream<String> testStream = Stream.of("ABC", "EFG");
        List<String> expectedResults = Arrays.asList("abc", "efg");

        //WHEN
        List<String> results = validator.makeLowerCaseStream(testStream).collect(Collectors.toList());

        //THEN
        assertEquals(expectedResults, results, "makeLowerCaseStream does not make elements lower case!");
    }

    @Test
    public void removeDraftFilesStream_performOperation_correctResults() {
        //GIVEN
        FileValidator validator = new FileValidator();
        Stream<String> testStream = Stream.of("abc", "_draft");
        List<String> expectedResults = Collections.singletonList("abc");

        //WHEN
        List<String> results = validator.removeDraftFilesStream(testStream).collect(Collectors.toList());

        //THEN
        assertEquals(expectedResults, results, "deleteDraftFilesStream does not delete draft files from list!");
    }

    @Test
    public void removeHiddenFilesStream_performOperation_correctResults() {
        //GIVEN
        FileValidator validator = new FileValidator();
        Stream<String> testStream = Stream.of("abc", ".");
        List<String> expectedResults = Collections.singletonList("abc");

        //WHEN
        List<String> results = validator.removeHiddenFilesStream(testStream).collect(Collectors.toList());

        //THEN
        assertEquals(expectedResults, results,
                "deleteUnnamedFilesStream does not delete unnamed files from list!");
    }

    @Test
    public void sortListStream_performOperation_correctResults() {
        //GIVEN
        FileValidator validator = new FileValidator();
        Stream<String> testStream = Stream.of("efg", "abc");
        List<String> expectedResults = Arrays.asList("abc", "efg");

        //WHEN
        List<String> results = validator.sortListStream(testStream).collect(Collectors.toList());

        //THEN
        assertEquals(expectedResults, results, "sortListStream does not sort elements as expected!");
    }

    @Test
    public void collectStreamResults_performOperation_returnList() {
        //GIVEN
        FileValidator validator = new FileValidator();
        Stream<String> testStream = Stream.of("ABC", "efg");
        List<String> expectedResults = Arrays.asList("ABC", "efg");

        //WHEN
        List<String> results = validator.collectStreamResults(testStream);

        //THEN
        assertEquals(expectedResults, results, "collectStreamResults does not return a list!");
    }

    @Test
    public void validateFiles_processedInList_correctResults() {
        //GIVEN
        FileValidator validator = new FileValidator();
        List<String> expectedResults = Arrays.asList(
                "imports.java",
                "main.java",
                "readme.md",
                "sources.txt");

        //WHEN
        List<String> results = validator.validateFiles();

        //THEN
        assertEquals(expectedResults, results, "validateFiles does not have expected results!");
    }

    @Test
    public void makeLowerCase_performOperation_correctResults() {
        //GIVEN
        FileValidator validator = new FileValidator();
        List<String> testList = Arrays.asList("ABC", "EFG");
        List<String> expectedResults = Arrays.asList("abc", "efg");

        //WHEN
        List<String> results = validator.makeLowerCase(testList);

        //THEN
        assertEquals(expectedResults, results, "makeLowerCase does not make elements lower case!");
    }

    @Test
    public void removeDraftFiles_performOperation_correctResults() {
        //GIVEN
        FileValidator validator = new FileValidator();
        List<String> testList = Arrays.asList("abc", "_Draft");
        List<String> expectedResults = Collections.singletonList("abc");

        //WHEN
        List<String> results = validator.removeDraftFiles(testList);

        //THEN
        assertEquals(expectedResults, results, "deleteDraftFiles does not delete draft files from list!");
    }

    @Test
    public void removeHiddenFiles_performOperation_correctResults() {
        //GIVEN
        FileValidator validator = new FileValidator();
        List<String> testList = Arrays.asList("abc", ".");
        List<String> expectedResults = Collections.singletonList("abc");

        //WHEN
        List<String> results = validator.removeHiddenFiles(testList);

        //THEN
        assertEquals(expectedResults, results,
                "deleteUnnamedFiles does not delete unnamed files from list!");
    }

    @Test
    public void sortList_performOperation_correctResults() {
        //GIVEN
        FileValidator validator = new FileValidator();
        List<String> testList = Arrays.asList("efg", "abc");
        List<String> expectedResults = Arrays.asList("abc", "efg");

        //WHEN
        validator.sortList(testList);

        //THEN
        assertEquals(expectedResults, testList, "sortList does not sort elements as expected!");
    }
}
