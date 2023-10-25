package com.kenzie.streams.listprocessing.resources;

public class User {

    private final String name;
    private final int age;

    private User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private int age;

        public Builder withName(String withName) {
            this.name = withName;
            return this;
        }

        public Builder withAge(int withAge) {
            this.age = withAge;
            return this;
        }

        public User build() {
            return new User(name, age);
        }
    }

}
