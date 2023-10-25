package com.kenzie.streams.drills;

import com.kenzie.streams.drills.resources.Dish;

import java.util.List;
import java.util.Map;

/**
 * You may need to explore the Collectors javadoc to solve these problems.
 * https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collectors.html
 */
public class CollectingDrills {

    /**
     * Create a map of the dishes, where the key is the DishType and the value is a list of all dishes of that type.
     * @param menu every dish on the menu
     * @return A list of dishes of each type
     */
    public static Map<Dish.Type, List<Dish>> collectDishesByType(List<Dish> menu) {
        throw new UnsupportedOperationException();
    }

    /**
     * Creates a map of all of the dish types mapped to the number of dishes of that type.
     * @param menu every dish on the menu
     * @return The number of dishes of each type
     */
    public static Map<Dish.Type, Long> numberOfDishesByType(List<Dish> menu) {
        throw new UnsupportedOperationException();
    }

    /**
     * Create a map where I can quickly lookup all of the vegetarian dishes and all of the non-vegetarian dishes.
     * @param menu every dish on the menu
     * @return a map of a vegetarian flag to all of the dishes that are or aren't vegetarian
     */
    public static Map<Boolean, List<Dish>> vegetarianDishes(List<Dish> menu) {
        throw new UnsupportedOperationException();
    }

    /**
     * Get the average (arithmetic mean) calorie count across all the dishes.
     * @param menu every dish on the menu
     * @return the average calorie count across all the dishes.
     */
    public static Double getAverageCalories(List<Dish> menu) {
        throw new UnsupportedOperationException();
    }
}
