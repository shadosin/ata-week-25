package com.kenzie.streams.filevalidator;

import com.kenzie.streams.filevalidator.resources.ImporterManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class FileValidator {

    private List<String> sourceFileNames;

    /**
     * Constructor for FileValidator.
     */
    public FileValidator() {
        ImporterManager importManager = new ImporterManager();
        sourceFileNames = importManager.importFiles();
        validateFiles();
        validateFilesStream();
    }

    /**
     * Iterates through all file names in sourceFileNames and validates them.
     * @return The validated list of file names.
     */
    public List<String> validateFiles() {
        /*
        DO NOT MODIFY THIS METHOD OR ANY OF THE METHODS IT CALLS

        The methods to modify are found below validateFilesStream
        */
        List<String> validFileNames = sourceFileNames;

        validFileNames = removeDraftFiles(validFileNames);
        validFileNames = removeHiddenFiles(validFileNames);
        validFileNames = makeLowerCase(validFileNames);
        sortList(validFileNames);

        return validFileNames;
    }

    /**
     * Makes all elements in a list lowercase.
     * @param list Source list.
     * @return Processed list.
     */
    public List<String> makeLowerCase(List<String> list) {
        List<String> lowerList = new ArrayList<>();
        for (String s : list) {
            lowerList.add(s.toLowerCase());
        }
        return lowerList;
    }

    /**
     * Filters out all draft files in a list.
     * @param list Source list.
     * @return Processed list.
     */
    public List<String> removeDraftFiles(List<String> list) {
        List<String> filteredList = new ArrayList<>();
        for (String s : list) {
            if (!s.contains("_Draft")) {
                filteredList.add(s);
            }
        }
        return filteredList;
    }

    /**
     * Filters out all hidden files in a list.
     * @param list Source list.
     * @return Processed list.
     */
    public List<String> removeHiddenFiles(List<String> list) {
        List<String> filteredList = new ArrayList<>();
        for (String s : list) {
            if (!s.startsWith(".")) {
                filteredList.add(s);
            }
        }
        return filteredList;
    }

    /**
     * Sorts all elements in a list in the natural order.
     * @param list Source list.
     */
    public void sortList(List<String> list) {
        Collections.sort(list);
    }

    /**
     * Uses a Stream to process all file names in sourceFileNames and validates them.
     * PARTICIPANTS: Complete this method.
     * @return The validated list of file names.
     */
    public List<String> validateFilesStream() {
        Stream<String> fileNameStream = createStream(sourceFileNames);
        fileNameStream = makeLowerCaseStream(fileNameStream);
        fileNameStream = removeDraftFilesStream(fileNameStream);
        fileNameStream = removeHiddenFilesStream(fileNameStream);
        fileNameStream = sortListStream(fileNameStream);
        return collectStreamResults(fileNameStream);
    }

    /**
     * Create a stream from the given List.
     * PARTICIPANTS: Complete this method.
     * @param files Source List.
     * @return Created stream.
     */
    public Stream<String> createStream(List<String> files) {
        return null;
    }

    /**
     * Makes all elements in stream lowercase.
     * PARTICIPANTS: Complete this method.
     * @param stream Source stream.
     * @return Processed stream.
     */
    public Stream<String> makeLowerCaseStream(Stream<String> stream) {
        return stream;
    }

    /**
     * Filters out all files in a stream with "draft" in their name.
     * PARTICIPANTS: Complete this method.
     * @param stream Source stream.
     * @return Processed stream.
     */
    public Stream<String> removeDraftFilesStream(Stream<String> stream) {
        return stream;
    }

    /**
     * Filters out all hidden files in a stream. Hidden files are files that start with a period.
     * PARTICIPANTS: Complete this method.
     * @param stream Source stream.
     * @return Processed stream.
     */
    public Stream<String> removeHiddenFilesStream(Stream<String> stream) {
        return stream;
    }

    /**
     * Sorts all elements in a stream in the natural order.
     * PARTICIPANTS: Complete this method.
     * @param stream Source stream.
     * @return Processed stream.
     */
    public Stream<String> sortListStream(Stream<String> stream) {
        return stream;
    }

    /**
     * Collects the stream as a List.
     * PARTICIPANTS: Complete this method.
     * @param stream Source stream.
     * @return List of results.
     */
    public List<String> collectStreamResults(Stream<String> stream) {
        return null;
    }

}
