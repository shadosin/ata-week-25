package com.kenzie.streams.listprocessing.resources;

import java.util.ArrayList;
import java.util.List;

public class ProjectServerManager {

    public List<String> results;

    /**
     * Constructor for ProjectServerManager.
     */
    public ProjectServerManager() {
        results = new ArrayList<>();
    }

    /**
     * Dummy method for unit test. Prints a submit message when called.
     * @param name Source string.
     */
    public void submitToProject(String name) {
        System.out.println(name + " submitted to project server!");
        results.add(name);
    }
}
