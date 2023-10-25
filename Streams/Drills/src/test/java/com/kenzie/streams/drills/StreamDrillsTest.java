package com.kenzie.streams.drills;

import com.kenzie.streams.drills.resources.Dish;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StreamDrillsTest {

    private static final String BLAND_MEAL = "Bland Meal";
    private static final String BETTER_MEAL = "Better Meal";
    private static final String BEST_MEAL = "Best Meal";
    private static final String THIS_IS_NOT_A_MEAL_ITS_A_SNACK = "Snack";

    @Test
    public void vegetarianDishes_emptyList_returnsEmptyList() {
        assumeVegDishes(Collections.emptyList(), Collections.emptyList());
    }

    @Test
    public void vegetarianDishes_nullList_returnsEmptyList() {
        assumeVegDishes(null, Collections.emptyList());
    }

    @Test
    public void vegetarianDishes_oneNonVeg_returnsEmptyList() {
        List<Dish> nonVeg = Collections.singletonList(Dish.builder().withIsVegetarian(false).build());
        assumeVegDishes(nonVeg, Collections.emptyList());
    }

    @Test
    public void vegetarianDishes_oneVeg_returnsVeg() {
        List<Dish> veg = Collections.singletonList(Dish.builder().withIsVegetarian(true).build());
        assumeVegDishes(veg, veg);
    }

    @Test
    public void vegetarianDishes_mixed_returnsVegs() {
        List<Dish> mixed = Lists.newArrayList(Dish.builder().withIsVegetarian(true).build(),
            Dish.builder().withIsVegetarian(false).build());
        List<Dish> results = Lists.newArrayList(mixed);
        results.remove(1);
        assumeVegDishes(mixed, results);
    }

    @Test
    public void uniqueEvenNumbers_noEvens_returnsEmptyList() {
        List<Integer> odds = Lists.newArrayList(1, 3, 5);
        List<Integer> results = Lists.newArrayList();
        assumeUniqueEvens(odds, results);
    }

    @Test
    public void uniqueEvenNumbers_allEvens_returnsAllNumbers() {
        List<Integer> odds = Lists.newArrayList(2, 2, 4, 4, 6, 8);
        List<Integer> results = Lists.newArrayList(2, 4, 6, 8);
        assumeUniqueEvens(odds, results);
    }

    @Test
    public void uniqueEvenNumbers_mixed_returnsEvens() {
        List<Integer> odds = Lists.newArrayList(1, 3, 2, 2, 4, 4, 5, 8);
        List<Integer> results = Lists.newArrayList(2, 4, 8);
        assumeUniqueEvens(odds, results);
    }

    @Test
    public void uniqueEvenNumbers_null_returnsEmptyList() {
        assumeUniqueEvens(null, Collections.emptyList());
    }

    @Test
    public void uniqueEvenNumbers_emptyList_returnsEmptyList() {
        assumeUniqueEvens(Collections.emptyList(), Collections.emptyList());
    }

    @Test
    public void threeHighCaloricDishNames_empty_returnsEmptyList() {
        assumeThreeHighCaloricDishNames(Collections.emptyList(), Collections.emptyList());
    }

    @Test
    public void threeHighCaloricDishNames_oneOver_returnsOneDish() {
        List<Dish> dishes = Collections.singletonList(Dish.builder().withName(BLAND_MEAL).withCalories(500).build());
        assumeThreeHighCaloricDishNames(dishes, Collections.singletonList(BLAND_MEAL));
    }

    @Test
    public void threeHighCaloricDishNames_twoOver_returnsTwoDishes() {
        List<Dish> dishes = Lists.newArrayList(Dish.builder().withName(BLAND_MEAL).withCalories(500).build(),
            Dish.builder().withName(BETTER_MEAL).withCalories(700).build());
        assumeThreeHighCaloricDishNames(dishes, Lists.newArrayList(BLAND_MEAL, BETTER_MEAL));
    }

    @Test
    public void threeHighCaloricDishNames_threeOver_returnsThreeDishes() {
        List<Dish> dishes = Lists.newArrayList(Dish.builder().withName(BLAND_MEAL).withCalories(500).build(),
            Dish.builder().withName(BETTER_MEAL).withCalories(700).build(),
            Dish.builder().withName(BEST_MEAL).withCalories(1200).build());
        assumeThreeHighCaloricDishNames(dishes, Lists.newArrayList(BLAND_MEAL, BETTER_MEAL, BEST_MEAL));
    }

    @Test
    public void threeHighCaloricDishNames_fourDishesThreeOver_returnsThreeDishes() {
        List<Dish> dishes = Lists.newArrayList(Dish.builder()
                .withName(THIS_IS_NOT_A_MEAL_ITS_A_SNACK)
                .withCalories(250)
                .build(),
            Dish.builder().withName(BLAND_MEAL).withCalories(500).build(),
            Dish.builder().withName(BETTER_MEAL).withCalories(700).build(),
            Dish.builder().withName(BEST_MEAL).withCalories(1200).build());
        assumeThreeHighCaloricDishNames(dishes, Lists.newArrayList(BLAND_MEAL, BETTER_MEAL, BEST_MEAL));
    }

    @Test
    public void threeHighCaloricDishNames_fourOver_returnsThreeDishes() {
        List<Dish> dishes = Lists.newArrayList(Dish.builder()
                .withName(THIS_IS_NOT_A_MEAL_ITS_A_SNACK)
                .withCalories(300)
                .build(),
            Dish.builder().withName(BLAND_MEAL).withCalories(500).build(),
            Dish.builder().withName(BETTER_MEAL).withCalories(700).build(),
            Dish.builder().withName(BEST_MEAL).withCalories(1200).build());
        assumeThreeHighCaloricDishNamesSize(dishes, Lists.newArrayList(BLAND_MEAL, BETTER_MEAL, BEST_MEAL));
    }

    @Test
    public void threeHighCaloricDishNames_null_returnsEmptyList() {
        assumeThreeHighCaloricDishNamesSize(null, Collections.emptyList());
    }

    @Test
    public void howManyDishes_empty_returnsEmptyList() {
        assumeHowManyDishes(Collections.emptyList(), 0);
    }

    @Test
    public void howManyDishes_null_returnsEmptyList() {
        assumeHowManyDishes(null, 0);
    }

    @Test
    public void howManyDishes_one_returnsOneDish() {
        List<Dish> dishes = Collections.singletonList(Dish.builder().withName(BLAND_MEAL).withCalories(500).build());
        assumeHowManyDishes(dishes, 1);
    }

    @Test
    public void howManyDishes_two_returnsTwoDishes() {
        List<Dish> dishes = Lists.newArrayList(Dish.builder().withName(BLAND_MEAL).withCalories(500).build(),
            Dish.builder().withName(BETTER_MEAL).withCalories(700).build());
        assumeHowManyDishes(dishes, 2);
    }

    @Test
    public void howManyDishes_three_returnsThreeDishes() {
        List<Dish> dishes = Lists.newArrayList(Dish.builder().withName(BLAND_MEAL).withCalories(500).build(),
            Dish.builder().withName(BETTER_MEAL).withCalories(700).build(),
            Dish.builder().withName(BEST_MEAL).withCalories(1200).build());
        assumeHowManyDishes(dishes, 3);
    }

    @Test
    public void lengthOfDishNames_emptyList_returnsEmptyList() {
        assumeLengthOfDishNames(Collections.emptyList(), Collections.emptyList());
    }

    @Test
    public void lengthOfDishNames_nullList_returnsEmptyList() {
        assumeLengthOfDishNames(null, Collections.emptyList());
    }

    @Test
    public void lengthOfDishNames_one_returnsLength() {
        List<Dish> dishes = Collections.singletonList(Dish.builder().withName(BLAND_MEAL).build());
        assumeLengthOfDishNames(dishes, Lists.newArrayList(10));
    }

    @Test
    public void lengthOfDishNames_two_returnsLengths() {
        List<Dish> dishes = Lists.newArrayList(Dish.builder().withName(BLAND_MEAL).build(),
            Dish.builder().withName(BETTER_MEAL).build());
        assumeLengthOfDishNames(dishes, Lists.newArrayList(10, 11));
    }

    @Test
    public void lengthOfDishNames_three_returnsLengths() {
        List<Dish> dishes = Lists.newArrayList(Dish.builder().withName(BLAND_MEAL).build(),
            Dish.builder().withName(BETTER_MEAL).build(),
            Dish.builder().withName(BEST_MEAL).build());
        assumeLengthOfDishNames(dishes, Lists.newArrayList(10, 11, 9));
    }

    @Test
    public void isEverythingUnder100Calories_emptyList_returnsTrue() {
        assumeIsEverythingUnder1000Calories(Collections.emptyList(), true);
    }

    @Test
    public void isEverythingUnder100Calories_nullList_returnsTrue() {
        assumeIsEverythingUnder1000Calories(null, true);
    }

    @Test
    public void isEverythingUnder100Calories_true_returnsTrue() {
        List<Dish> dishes = Lists.newArrayList(Dish.builder().withName(BLAND_MEAL).withCalories(500).build(),
            Dish.builder().withName(BETTER_MEAL).withCalories(700).build(),
            Dish.builder().withName(BEST_MEAL).withCalories(900).build());
        assumeIsEverythingUnder1000Calories(dishes, true);
    }

    @Test
    public void isEverythingUnder100Calories_false_returnsFalse() {
        List<Dish> dishes = Lists.newArrayList(Dish.builder().withName(BLAND_MEAL).withCalories(500).build(),
            Dish.builder().withName(BETTER_MEAL).withCalories(700).build(),
            Dish.builder().withName(BEST_MEAL).withCalories(1200).build());
        assumeIsEverythingUnder1000Calories(dishes, false);
    }

    @Test
    public void isNothingOver1000Calories_true_returnsTrue() {
        List<Dish> dishes = Lists.newArrayList(Dish.builder().withName(BLAND_MEAL).withCalories(500).build(),
            Dish.builder().withName(BETTER_MEAL).withCalories(700).build(),
            Dish.builder().withName(BEST_MEAL).withCalories(1000).build());
        assumeIsNothingOver1000Calories(dishes, true);
    }

    @Test
    public void isNothingOver1000Calories_false_returnsFalse() {
        List<Dish> dishes = Lists.newArrayList(Dish.builder().withName(BLAND_MEAL).withCalories(500).build(),
            Dish.builder().withName(BETTER_MEAL).withCalories(700).build(),
            Dish.builder().withName(BEST_MEAL).withCalories(1200).build());
        assumeIsNothingOver1000Calories(dishes, false);
    }

    @Test
    public void isMenuVegetarianFriendly_true_returnsOnlyItem() {
        List<Dish> mixed = Lists.newArrayList(Dish.builder().withIsVegetarian(true).build());
        assumeIsMenuVegetarianFriendly(mixed, true);
    }

    @Test
    public void isMenuVegetarianFriendly_true_returnsTrue() {
        List<Dish> mixed = Lists.newArrayList(Dish.builder().withIsVegetarian(true).build(),
            Dish.builder().withIsVegetarian(false).build());
        assumeIsMenuVegetarianFriendly(mixed, true);
    }

    @Test
    public void isMenuVegetarianFriendly_false_returnsFalse() {
        List<Dish> mixed = Lists.newArrayList(Dish.builder().withIsVegetarian(false).build(),
            Dish.builder().withIsVegetarian(false).build());
        assumeIsMenuVegetarianFriendly(mixed, false);
    }

    @Test
    public void isMenuVegetarianFriendly_nullList_returnsFalse() {
        assumeIsMenuVegetarianFriendly(null, false);
    }

    @Test
    public void isMenuVegetarianFriendly_emptyList_returnsFalse() {
        assumeIsMenuVegetarianFriendly(Collections.emptyList(), false);
    }

    @Test
    public void vegetarianDish_noVegDishes_isEmpty() {
        List<Dish> mixed = Lists.newArrayList(Dish.builder().withIsVegetarian(false).build(),
            Dish.builder().withIsVegetarian(false).build());
        assumeVegetarianDish(mixed, Optional.empty());
    }

    @Test
    public void vegetarianDish_vegDishes_isNonEmpty() {
        Dish vegDish = Dish.builder().withIsVegetarian(true).build();
        List<Dish> mixed = Lists.newArrayList(vegDish);
        assumeVegetarianDish(mixed, Optional.of(vegDish));
    }

    @Test
    public void vegetarianDish_nullList_isEmpty() {
        assumeVegetarianDish(null, Optional.empty());
    }

    @Test
    public void vegetarianDish_emptyList_isEmpty() {
        assumeVegetarianDish(Collections.emptyList(), Optional.empty());
    }

    @Test
    public void listCountriesOfOrigin_emptyList_returnsEmptyList() {
        List<Dish> dishes = Collections.emptyList();
        Set<String> countries = StreamDrills.listCountriesOfOrigin(dishes);
        assertTrue(countries.isEmpty());
    }

    @Test
    public void listCountriesOfOrigin_nullList_returnsEmptyList() {
        Set<String> countries = StreamDrills.listCountriesOfOrigin(null);
        assertNotNull(countries);
        assertTrue(countries.isEmpty());
    }

    @Test
    public void listCountriesOfOrigin_throwsException() {
        List<Dish> dishes =  Collections.singletonList(Dish.builder().withIsVegetarian(true).build());
        assertThrows(Exception.class, () -> StreamDrills.listCountriesOfOrigin(dishes));
    }

    private void assumeVegDishes(List<Dish> initial, List<Dish> expected) {
        List<Dish> actual = StreamDrills.vegetarianDishes(initial);
        assertEquals(expected,
            actual,
            String.format("When calling vegetarianDishes on %s, expected response of %s, but received %s.",
                initial,
                expected,
                actual));
    }

    private void assumeThreeHighCaloricDishNames(List<Dish> initial, List<String> expected) {

        List<String> actual = StreamDrills.threeHighCaloricDishNames(initial);
        assertEquals(expected,
            actual,
            String.format("When calling threeHighCaloricDishNames on %s, expected response of %s, but received %s.",
                initial,
                expected,
                actual));
    }

    private void assumeHowManyDishes(List<Dish> initial, long expected) {
        long actual = StreamDrills.howManyDishes(initial);
        assertEquals(expected,
            actual,
            String.format("When calling howManyDishes on %s, expected response of %d, but received %d.",
                initial,
                expected,
                actual));
    }

    private void assumeThreeHighCaloricDishNamesSize(List<Dish> initial, List<String> expected) {
        List<String> actual = StreamDrills.threeHighCaloricDishNames(initial);
        assertEquals(expected.size(),
            actual.size(),
            String.format("When calling threeHighCaloricDishNames on %s, expected 3 responses, but received %d.",
                initial,
                actual.size()));
    }

    private void assumeUniqueEvens(List<Integer> initial, List<Integer> expected) {
        List<Integer> actual = StreamDrills.uniqueEvenNumbers(initial);
        assertEquals(expected,
            actual,
            String.format("When calling uniqueEvenNumbers on %s, expected response of %s, but received %s.",
                initial,
                expected,
                actual));
    }

    private void assumeLengthOfDishNames(List<Dish> initial, List<Integer> expected) {

        List<Integer> actual = StreamDrills.lengthOfDishNames(initial);
        assertEquals(expected,
            actual,
            String.format("When calling lengthOfDishNames on %s, expected response of %s, but received %s.",
                initial,
                expected,
                actual));

    }

    private void assumeIsMenuVegetarianFriendly(List<Dish> initial, boolean expected) {
        boolean actual = StreamDrills.isMenuVegetarianFriendly(initial);
        assertEquals(expected,
            actual,
            String.format("When calling isMenuVegetarianFriendly on %s, expected response of %s, but received %s.",
                initial,
                expected,
                actual));
    }

    private void assumeIsEverythingUnder1000Calories(List<Dish> initial, boolean expected) {

        boolean actual = StreamDrills.isEverythingUnder1000Calories(initial);
        assertEquals(expected,
            actual,
            String.format("When calling isEverythingUnder1000Calories on %s, expected response of %s, but received %s.",
                initial,
                expected,
                actual));
    }

    private void assumeIsNothingOver1000Calories(List<Dish> initial, boolean expected) {
        boolean actual = StreamDrills.isNothingOver1000Calories(initial);
        assertEquals(expected,
            actual,
            String.format("When calling isNothingOver100Calories on %s, expected response of %s, but received %s.",
                initial,
                expected,
                actual));
    }

    private void assumeVegetarianDish(List<Dish> initial, Optional<Dish> expected) {
        Optional<Dish> actual = StreamDrills.vegetarianDish(initial);
        assertEquals(expected,
            actual,
            String.format("When calling vegetarianDish on %s, expected response of %s, but received %s.",
                initial,
                expected,
                actual));
    }

    //<-------------------------- tests for stream implementation -------------------------->

    @Test
    public void vegetarianDishes_usesStream(){
        List<Dish> dishes = Lists.newArrayList();
        List<Dish> spylist = spy(dishes);

        StreamDrills.vegetarianDishes(spylist);

        assertDoesNotThrow(() -> verify(spylist).stream(),
                ".stream() and stream methods should be called on the list.\n\n");
        //testing that a for-each loop is not being used
        assertDoesNotThrow(() -> verify(spylist, never()).iterator(),
                "A for loop should not be used on the list,"
                        + " .stream() and stream methods should be used instead.\n\n");
    }

    @Test
    public void uniqueEvenNumbers_usesStream(){
        List<Integer> ints = Lists.newArrayList();
        List<Integer> spylist = spy(ints);

        StreamDrills.uniqueEvenNumbers(spylist);

        assertDoesNotThrow(() -> verify(spylist).stream(),
                ".stream() and stream methods should be called on the list.\n\n");
        //testing that a for-each loop is not being used
        assertDoesNotThrow(() -> verify(spylist, never()).iterator(),
                "A for loop should not be used on the list,"
                        + " .stream() and stream methods should be used instead.\n\n");
    }

    @Test
    public void lengthOfDishNames_usesStream(){
        List<Dish> dishes = Lists.newArrayList();
        List<Dish> spylist = spy(dishes);

        StreamDrills.lengthOfDishNames(spylist);

        assertDoesNotThrow(() -> verify(spylist).stream(),
                ".stream() and stream methods should be called on the list.\n\n");
        //testing that a for-each loop is not being used
        assertDoesNotThrow(() -> verify(spylist, never()).iterator(),
                "A for loop should not be used on the list,"
                        + " .stream() and stream methods should be used instead.\n\n");
    }

    @Test
    public void isMenuVegetarianFriendly_usesStream(){
        List<Dish> dishes = Lists.newArrayList();
        List<Dish> spylist = spy(dishes);

        StreamDrills.isMenuVegetarianFriendly(spylist);

        assertDoesNotThrow(() -> verify(spylist).stream(),
                ".stream() and stream methods should be called on the list.\n\n");
        //testing that a for-each loop is not being used
        assertDoesNotThrow(() -> verify(spylist, never()).iterator(),
                "A for loop should not be used on the list,"
                        + " .stream() and stream methods should be used instead.\n\n");
    }

    @Test
    public void vegetarianDish_usesStream(){
        List<Dish> dishes = Lists.newArrayList();
        List<Dish> spylist = spy(dishes);

        StreamDrills.vegetarianDish(spylist);

        assertDoesNotThrow(() -> verify(spylist).stream(),
                ".stream() and stream methods should be called on the list.\n\n");
        //testing that a for-each loop is not being used
        assertDoesNotThrow(() -> verify(spylist, never()).iterator(),
                "A for loop should not be used on the list,"
                        + " .stream() and stream methods should be used instead.\n\n");
    }

    @Test
    public void isEverythingUnder1000Calories_usesStream(){
        List<Dish> dishes = Lists.newArrayList();
        List<Dish> spylist = spy(dishes);

        StreamDrills.isEverythingUnder1000Calories(spylist);

        assertDoesNotThrow(() -> verify(spylist).stream(),
                ".stream() and stream methods should be called on the list.\n\n");
        //testing that a for-each loop is not being used
        assertDoesNotThrow(() -> verify(spylist, never()).iterator(),
                "A for loop should not be used on the list,"
                        + " .stream() and stream methods should be used instead.\n\n");
    }

    @Test
    public void isNothingOver1000Calories_usesStream(){
        List<Dish> dishes = Lists.newArrayList();
        List<Dish> spylist = spy(dishes);

        StreamDrills.isNothingOver1000Calories(spylist);

        assertDoesNotThrow(() -> verify(spylist).stream(),
                ".stream() and stream methods should be called on the list.\n\n");
        //testing that a for-each loop is not being used
        assertDoesNotThrow(() -> verify(spylist, never()).iterator(),
                "A for loop should not be used on the list,"
                        + " .stream() and stream methods should be used instead.\n\n");
    }

    @Test
    public void threeHighCaloricDishNames_usesStream(){
        List<Dish> dishes = Lists.newArrayList();
        List<Dish> spylist = spy(dishes);

        StreamDrills.threeHighCaloricDishNames(spylist);

        assertDoesNotThrow(() -> verify(spylist).stream(),
                ".stream() and stream methods should be called on the list.\n\n");
        //testing that a for-each loop is not being used
        assertDoesNotThrow(() -> verify(spylist, never()).iterator(),
                "A for loop should not be used on the list,"
                        + " .stream() and stream methods should be used instead.\n\n");
    }

    @Test
    public void howManyDishes_usesStream(){
        List<Dish> dishes = Lists.newArrayList();
        List<Dish> spylist = spy(dishes);

        StreamDrills.howManyDishes(spylist);

        assertDoesNotThrow(() -> verify(spylist).stream(),
                ".stream() and stream methods should be called on the list.\n\n");
        //testing that a for-each loop is not being used
        assertDoesNotThrow(() -> verify(spylist, never()).iterator(),
                "A for loop should not be used on the list,"
                        + " .stream() and stream methods should be used instead.\n\n");
    }

    @Test
    public void listCountriesOfOriginhowManyDishes_usesStream(){
        List<Dish> dishes = Lists.newArrayList();
        List<Dish> spylist = spy(dishes);

        StreamDrills.listCountriesOfOrigin(spylist);

        assertDoesNotThrow(() -> verify(spylist).stream(),
                ".stream() and stream methods should be called on the list.\n\n");
        //testing that a for-each loop is not being used
        assertDoesNotThrow(() -> verify(spylist, never()).iterator(),
                "A for loop should not be used on the list,"
                        + " .stream() and stream methods should be used instead.\n\n");
    }
}
