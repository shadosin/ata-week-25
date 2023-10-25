package com.kenzie.streams.listprocessing;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;


public class FileProcessorStreamTest {

    @Test
    public void filterDocs_usesStream() {
        //GIVEN
        FileProcessor processor = new FileProcessor();
        List<String> list = Arrays.asList("Readme.md", "sources.txt", "credits.txt");
        List<String> testList = spy(new ArrayList<>(list));

        //WHEN
        processor.filterDocs(testList);

        //THEN
        assertDoesNotThrow(() -> verify(testList).stream(),
                "FilterDocs should use .stream() and other stream methods to process the list.");
    }

    @Test
    public void filterJava_usesStream() {
        //GIVEN
        FileProcessor processor = new FileProcessor();
        List<String> list = Arrays.asList("Readme.md", "sources.txt", "credits.txt");
        List<String> testList = spy(new ArrayList<>(list));

        //WHEN
        processor.filterJava(testList);

        //THEN
        assertDoesNotThrow(() -> verify(testList).stream(),
                "FilterJava should use .stream() and other stream methods to process the list.");
    }

    @Test
    public void sortAndSubmitAll_usesStream() {
        //GIVEN
        FileProcessor processor = new FileProcessor();
        List<String> list = Arrays.asList("Readme.md", "sources.txt", "credits.txt");
        List<String> testList = spy(new ArrayList<>(list));

        //WHEN
        processor.sortAndSubmitAll(testList);

        //THEN
        assertDoesNotThrow(() -> verify(testList).stream(),
                "SortAndSubmit should use .stream() and other stream methods to process the list.");
    }
}
