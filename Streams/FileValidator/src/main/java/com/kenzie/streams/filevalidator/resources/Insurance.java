package com.kenzie.streams.filevalidator.resources;

public class Insurance {
    private final String name;

    public Insurance(String name) {
        this.name = name;
    }

    public Insurance() {
        this.name = null;
    }

    public String getName() {
        return name;
    }
}
