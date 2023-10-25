package com.kenzie.streams.filevalidator.resources;

import java.util.ArrayList;
import java.util.List;

public class ImporterManager {

    /**
     * Creates a dummy list of file names.
     * @return The list of files.
     */
    public List<String> importFiles() {

        List<String> files = new ArrayList<>();
        files.add("Readme.MD");
        files.add("readme_Draft.md");
        files.add("sources.TXT");
        files.add("sources_Draft.TXT");
        files.add("MAIN.JAVA");
        files.add("IMPORTS.JAVA");
        files.add(".git");
        files.add(".txt");
        files.add(".txt");

        return files;
    }

}
