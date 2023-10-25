package com.kenzie.streams.listprocessing;

import com.kenzie.streams.listprocessing.resources.ProjectServerManager;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class FileProcessorTest {

    @Test
    public void filterDocs_processStream_correctResults() {
        //GIVEN
        FileProcessor processor = new FileProcessor();
        List<String> testList = Arrays.asList("Readme.md", "sources.txt", "credits.txt");
        List<String> expectedResult = Arrays.asList("credits.txt", "readme.md", "sources.txt");

        //WHEN
        List<String> result = processor.filterDocs(testList);

        //THEN
        assertEquals(expectedResult, result, "filterDocs does not have expected results!");
    }

    @Test
    public void filterDocs_nullList_returnsEmptyList() {
        //GIVEN
        FileProcessor processor = new FileProcessor();
        //WHEN
        List<String> result = processor.filterDocs(null);

        //THEN
        assertEquals(Collections.emptyList(), result, "filterDocs does not have expected results!");
    }

    @Test
    public void filterDocs_emptyList_returnsEmptyList() {
        //GIVEN
        FileProcessor processor = new FileProcessor();

        //WHEN
        List<String> result = processor.filterDocs(Collections.emptyList());

        //THEN
        assertEquals(Collections.emptyList(), result, "filterDocs does not have expected results!");
    }

    @Test
    public void filterJava_processStream_correctResults() {
        //GIVEN
        FileProcessor processor = new FileProcessor();
        List<String> testList = Arrays.asList("main.java", "userInput.java", "imports.java");
        Set<String> expectedResult = new HashSet<>(Arrays.asList("Imports.java", "Main.java", "UserInput.java"));

        //WHEN
        Set<String> result = processor.filterJava(testList);

        //THEN
        assertEquals(expectedResult, result, "filterJava does not have expected results!");
    }

    @Test
    public void filterJava_nullList_returnsEmptySet() {
        //GIVEN
        FileProcessor processor = new FileProcessor();

        //WHEN
        Set<String> result = processor.filterJava(null);

        //THEN
        assertEquals(Collections.emptySet(), result, "filterJava does not have expected results!");
    }

    @Test
    public void filterJava_emptyList_returnsEmptySet() {
        //GIVEN
        FileProcessor processor = new FileProcessor();

        //WHEN
        Set<String> result = processor.filterJava(Collections.emptyList());

        //THEN
        assertEquals(Collections.emptySet(), result, "filterJava does not have expected results!");
    }

    @Test
    public void sortAndSubmitAll_processStream_returnsCorrectResults()
            throws NoSuchFieldException, IllegalAccessException {
        //GIVEN
        FileProcessor processorObj = new FileProcessor();
        Class<?> processorClass = processorObj.getClass();
        Field managerField = processorClass.getDeclaredField("serverManager");
        managerField.setAccessible(true);
        ProjectServerManager serverManager = (ProjectServerManager) managerField.get(processorObj);

        List<String> testList = Arrays.asList("main.java", "userInput.java", "imports.java");
        List<String> expectedResult = Arrays.asList("imports.java", "main.java", "userInput.java");

        //WHEN
        processorObj.sortAndSubmitAll(testList);
        List<String> results = serverManager.results;

        //THEN
        assertEquals(expectedResult, results, "sortAndSubmitAll does not submit expected results!");
    }
}
