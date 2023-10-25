package com.kenzie.streams.listprocessing.resources;

public class Dish {
    private final String name;
    private final boolean isVegetarian;
    private final int calories;
    private final Type dishType;

    public Dish(String name, boolean isVegetarian, int calories, Type dishType) {
        this.name = name;
        this.isVegetarian = isVegetarian;
        this.calories = calories;
        this.dishType = dishType;
    }

    public String getName() {
        return name;
    }

    public boolean isVegetarian() {
        return isVegetarian;
    }

    public int getCalories() {
        return calories;
    }

    public Type getDishType() {
        return dishType;
    }

    public String getCountryOfOrigin() throws UnknownCountryOfOriginException {
        throw new UnknownCountryOfOriginException();
    }

    public static Builder builder() {
        return new Builder();
    }

    public void cook() {
        try {
            Thread.sleep((long) (Math.random() * 10000));
        } catch (InterruptedException e) {
            return;
        }
    }

    public static class Builder {
        private String name;
        private boolean isVegetarian;
        private int calories;
        private Type dishType;

        public Builder withName(String withName) {
            this.name = withName;
            return this;
        }

        public Builder withIsVegetarian(boolean withIsVegetarian) {
            this.isVegetarian = withIsVegetarian;
            return this;
        }

        public Builder withCalories(int withCalories) {
            this.calories = withCalories;
            return this;
        }

        public Builder withDishType(Type withDishType) {
            this.dishType = withDishType;
            return this;
        }

        public Dish build() {
            return new Dish(name, isVegetarian, calories, dishType);
        }
    }

    public enum Type { FISH, MEAT, OTHER }
}
