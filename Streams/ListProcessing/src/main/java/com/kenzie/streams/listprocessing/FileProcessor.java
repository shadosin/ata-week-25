package com.kenzie.streams.listprocessing;

import com.kenzie.streams.listprocessing.resources.ProjectServerManager;
import org.apache.commons.lang.WordUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
        return Optional.ofNullable(source)
                .orElse(Collections.emptyList())
                .stream()
                .filter(file -> file.endsWith(".txt") || file.endsWith(".md"))
                .map(String::toLowerCase)
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * Returns a {@code `Set<String>`} that contains only the file names for .java files, with each file capitalized.
     * PARTICIPANTS: Complete this method.
     * @param source Source List.
     * @return Processed Set.
     */
    public Set<String> filterJava(List<String> source) {

        return Optional.ofNullable(source)
                        .orElse(Collections.emptyList())
                                .stream().
                filter(file -> file.endsWith(".java"))
                .map(WordUtils::capitalize)
                .collect(Collectors.toSet());
    }

    /**
     * Sorts all file names in the list, and submits them in order to the project server via the method
     * `submitToProject()` of the `ProjectServerManager` class.
     * PARTICIPANTS: Complete this method.
     * @param source Source list.
     */
    public void sortAndSubmitAll(List<String> source) {
            source.stream()
                    .sorted()
                    .forEach(file -> Optional.ofNullable(file)
                            .ifPresent(serverManager::submitToProject));
    }
}
