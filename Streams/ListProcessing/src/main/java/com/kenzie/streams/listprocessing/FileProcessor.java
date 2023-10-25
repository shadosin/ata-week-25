package com.kenzie.streams.listprocessing;

import com.kenzie.streams.listprocessing.resources.ProjectServerManager;

import java.util.List;
import java.util.Set;

public class FileProcessor {

    private ProjectServerManager serverManager;

    /**
     * Constructor for FileProcessor.
     */
    public FileProcessor() {
        serverManager = new ProjectServerManager();
    }

    /**
     * Returns a {@code `List<String>`} that contains only the file names for .txt or .md files, all lowercase and
     * sorted alphabetically.
     * PARTICIPANTS: Complete this method.
     * @param source Source list.
     * @return Processed list.
     */
    public List<String> filterDocs(List<String> source) {
        return null;
    }

    /**
     * Returns a {@code `Set<String>`} that contains only the file names for .java files, with each file capitalized.
     * PARTICIPANTS: Complete this method.
     * @param source Source List.
     * @return Processed Set.
     */
    public Set<String> filterJava(List<String> source) {
        return null;
    }

    /**
     * Sorts all file names in the list, and submits them in order to the project server via the method
     * `submitToProject()` of the `ProjectServerManager` class.
     * PARTICIPANTS: Complete this method.
     * @param source Source list.
     */
    public void sortAndSubmitAll(List<String> source) {

    }
}
