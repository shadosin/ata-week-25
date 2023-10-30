package com.kenzie.streams.drills;

import com.kenzie.streams.drills.resources.Car;
import com.kenzie.streams.drills.resources.Dish;
import com.kenzie.streams.drills.resources.Insurance;
import org.apache.logging.log4j.core.util.JsonUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class OptionalDrills {

    /**
     * Print out the name of a dish that is vegetarian. If there is no vegetarian dish, do nothing.
     * @param menu - the list of dishes to look through
     */
    public static void printOutExampleVegetarianDish(List<Dish> menu) {

        Optional.ofNullable(menu)
                .orElse(Collections.emptyList())
                .stream()
                .filter(Dish::isVegetarian)
                .map(Dish::getName)
                .findFirst()
                .ifPresent(System.out::println);
    }

    /**
     * Returns the menu if it is non-null, and an empty List otherwise.
     * @param menu - the list of dishes to look through
     * @return the List of dishes if it is non-null, and an empty List otherwise
     */
    public static List<Dish> nonNullMenu(List<Dish> menu) {

        return Optional.ofNullable(menu)
                .orElse(Collections.emptyList())
                .stream()
                .collect(Collectors.toList());
    }

    /**
     * Return the name of the dish. There is a good chance the Dish coming in is null.
     * @param dish A dish (maybe null...)
     * @return the name of the dish if it exists
     */
    public static Optional<String> getDishName(Dish dish) {
        return Optional.ofNullable(dish)
                .map(Dish::getName);
    }

    /**
     * Return the name of the insurance for this car.
     * @param car A car
     * @return The name of the insurance if it exists
     */
    public static Optional<String> getExistingInsuranceName(Car car) {
        return Optional.ofNullable(car)
                .map(Car::getInsurance)
                .flatMap(insurance -> insurance.map(Insurance::getName));
    }

    /**
     * Use the private 'otherService' method below to find the name of the
     * insurance that will be cheapest for me.  Be careful!
     * @param car A car
     * @return the name of the cheapest insurance if it exists
     */
    public static Optional<String> findCheapestInsuranceName(Car car) {

        return Optional.ofNullable(otherService(car))
                .map(Insurance::getName);
    }

    /**
     * Cool!  I wrote a new version of 'otherService' called
     * 'safeOtherService' that will never return null!  But now
     * the car being passed in is an Optional... what do I do???
     * @param car maybe a car?
     * @return the name of the car's cheapest insurance if it and the car exist
     */
    public static Optional<String> findCheapestInsuranceName(Optional<Car> car) {
        return car.map(OptionalDrills::safeOtherService)
                .map(Insurance::getName);
    }

    /**
     * Tries to find the cheapest insurance, may be null.
     */
    private static Insurance otherService(Car car) {
        return null;
    }

    /**
     * Tries to find the cheapest insurance, may not be null.
     */
    private static Insurance safeOtherService(Car car) {
        return new Insurance("Amazon Insurance");
    }
}
