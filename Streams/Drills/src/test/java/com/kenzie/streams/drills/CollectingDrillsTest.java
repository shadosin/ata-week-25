package com.kenzie.streams.drills;

import com.kenzie.streams.drills.resources.Dish;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.exceptions.verification.NoInteractionsWanted;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.kenzie.streams.drills.resources.Dish.Type.FISH;
import static com.kenzie.streams.drills.resources.Dish.Type.MEAT;
import static com.kenzie.streams.drills.resources.Dish.Type.OTHER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CollectingDrillsTest {
    private static final Dish SUSHI = Dish.builder()
        .withCalories(400)
        .withDishType(FISH)
        .withIsVegetarian(false)
        .withName("Sushi Plate")
        .build();

    private static final Dish STEAK = Dish.builder()
        .withCalories(900)
        .withDishType(MEAT)
        .withIsVegetarian(false)
        .withName("Steak And Potatoes")
        .build();

    private static final Dish CHICKEN = Dish.builder()
        .withCalories(1100)
        .withDishType(MEAT)
        .withIsVegetarian(false)
        .withName("Fried Chicken")
        .build();

    private static final Dish SALAD = Dish.builder()
        .withCalories(300)
        .withDishType(OTHER)
        .withIsVegetarian(true)
        .withName("Harvest Salad")
        .build();

    private static final List<Dish> ALL_DISHES = ImmutableList.of(SUSHI, STEAK, CHICKEN, SALAD);
    private static final Map<Dish.Type, List<Dish>> EXPECTED_DISH_TYPE_MAP =
        //              KEY,  VALUE
        ImmutableMap.of(FISH, ImmutableList.of(SUSHI),
                        MEAT, ImmutableList.of(STEAK, CHICKEN),
                        OTHER, ImmutableList.of(SALAD));

    @Test
    public void collectDishesByType_allTypes_mapsDishToType() {
        Map<Dish.Type, List<Dish>> dishTypeMap = CollectingDrills.collectDishesByType(ALL_DISHES);

        for (Dish.Type type : Dish.Type.values()) {
            assertTrue(dishTypeMap.containsKey(type), String.format("Expected collectDishesByType " +
                "to return a map with %s dish type as a key. Found: %s", type, dishTypeMap));
            List<Dish> dishesForType = dishTypeMap.get(type);

            for (Dish expectedDish : EXPECTED_DISH_TYPE_MAP.get(type)) {
                assertTrue(dishesForType.contains(expectedDish), String.format("Expected collectDishesByType " +
                    "to map %s dish to the %s dish type key. Found: %s", expectedDish, type, dishTypeMap));
            }
        }
    }

    @Test
    public void collectDishesByType_nullList_returnsAnEmptyMap() {
        Map<Dish.Type, List<Dish>> dishTypeMap = CollectingDrills.collectDishesByType(null);

        assertNotNull(dishTypeMap,
            "Expected to return an non-null value if the provide if `dishes` is `null`.");

        assertTrue(dishTypeMap.isEmpty(),
            String.format("Expected to return an empty list if `dishes` is `null`. Instead returned %s",
                dishTypeMap));
    }

    @Test
    public void numberOfDishesByType_allTypes_mapsNumberOfDishToType() {
        Map<Dish.Type, Long> dishTypeMap = CollectingDrills.numberOfDishesByType(ALL_DISHES);

        for (Dish.Type type : Dish.Type.values()) {
            assertTrue(dishTypeMap.containsKey(type), String.format("Expected numberOfDishesByType " +
                "to return a map with %s dish type as a key. Found: %s", type, dishTypeMap));
            Long count = dishTypeMap.get(type);

            int expectedDishesCount = EXPECTED_DISH_TYPE_MAP.get(type).size();
            assertEquals(expectedDishesCount, count, String.format("Expected numberOfDishesByType " +
                    "to count %s dishes for the %s dish type key. Found: %s", expectedDishesCount, type, dishTypeMap));
        }
    }

    @Test
    public void numberOfDishesByType_nullList_returnsAnEmptyMap() {
        Map<Dish.Type, Long> dishTypeMap = CollectingDrills.numberOfDishesByType(null);

        assertNotNull(dishTypeMap,
            "Expected to return an non-null value if the provide if `dishes` is `null`.");

        assertTrue(dishTypeMap.isEmpty(),
            String.format("Expected to return an empty list if `dishes` is `null`. Instead returned %s",
                dishTypeMap));
    }

    @Test
    public void vegetarianDishes_allTypes_mapsVegetarianDishes() {
        Map<Boolean, List<Dish>> vegetarianDishMap = CollectingDrills.vegetarianDishes(ALL_DISHES);

        List<Dish> vegetarianDishes = vegetarianDishMap.get(true);
        List<Dish> nonVegetarianDishes = vegetarianDishMap.get(false);

        assertEquals(vegetarianDishes.size(), 1, String.format("Expected a single vegetarian dish " +
            "to be mapped for the input %s", ALL_DISHES));
        assertTrue(vegetarianDishes.contains(SALAD), String.format("Expected vegetarianDishes " +
            "to map %s as a vegetarian dish.", SALAD));

        assertTrue(nonVegetarianDishes.contains(SUSHI), String.format("Expected vegetarianDishes " +
            "to map %s as a non-vegetarian dish.", SUSHI));
        assertTrue(nonVegetarianDishes.contains(STEAK), String.format("Expected vegetarianDishes " +
            "to map %s as a non-vegetarian dish.", STEAK));
        assertTrue(nonVegetarianDishes.contains(CHICKEN), String.format("Expected vegetarianDishes " +
            "to map %s as a non-vegetarian dish.", CHICKEN));
    }

    @Test
    public void vegetarianDishes_nullList_returnsAnEmptyMap() {
        Map<Boolean, List<Dish>>  dishMap = CollectingDrills.vegetarianDishes(null);

        assertNotNull(dishMap,
            "Expected to return an non-null value if the provide if `dishes` is `null`.");

        assertTrue(dishMap.isEmpty(),
            String.format("Expected to return an empty list if `dishes` is `null`. Instead returned %s",
                dishMap));
    }

    @Test
    public void averageCalories_mixedItems_returnsAverage() {
        // GIVEN
        double totalCalories = 0;
        for (Dish dish : ALL_DISHES) {
            totalCalories += dish.getCalories();
        }
        Double expectedAverageCalories = totalCalories / ALL_DISHES.size();

        // WHEN
        Double result = CollectingDrills.getAverageCalories(ALL_DISHES);

        // THEN
        assertEquals(expectedAverageCalories, result,
            String.format("Expected to the get an average of %s calories. Got %s instead.",
                expectedAverageCalories, result));
    }

    @Test
    public void averageCalories_nullList_returnsZero() {
        Double result = CollectingDrills.getAverageCalories(null);

        assertNotNull(result,
            "Expected to return an non-null value if the provide if `dishes` is `null`.");

        assertEquals(0, result,
            String.format("Expected to return 0 list if `dishes` is `null`. Instead returned %s",
                result));
    }

    //<--------------------- Tests to make sure stream and collectors is used ------------------>

    @Test
    public void collectDishesByType_usesStreamAndCollectors(){
        List<Dish> spylist = spy(ALL_DISHES);

        try(MockedStatic<Collectors> collectors = Mockito.mockStatic(Collectors.class)){

            try {
                CollectingDrills.collectDishesByType(spylist);
            }catch(NullPointerException e){
                //catching the null pointer from the mock if collectors is used
                //I chose to not mock the interaction to not give away the answer
                //and trying to make a default answer that is a collector is way too much hassle
            }

            //verify that stream is being used
            assertDoesNotThrow(() -> verify(spylist).stream(),
                    ".stream() and stream methods should be called on the list.\n\n");
            //testing that a for-each loop is not being used
            assertDoesNotThrow(() -> verify(spylist, never()).iterator(),
                    "A for loop should not be used on the list,"
                            + ".stream() and stream methods should be used instead.\n\n");
            //verify that Collectors is being used
            assertThrows(NoInteractionsWanted.class,
                    collectors::verifyNoInteractions,
                    "Expected Collectors to be used.\n\n");
        }

    }

    @Test
    public void numberOfDishesByType_usesStreamAndCollectors(){
        List<Dish> spylist = spy(ALL_DISHES);

        try(MockedStatic<Collectors> collectors = Mockito.mockStatic(Collectors.class)){

            try {
                CollectingDrills.numberOfDishesByType(spylist);
            }catch(NullPointerException e){
                //catching the null pointer from the mock if collectors is used
                //I chose to not mock the interaction to not give away the answer
            }

            //verify that stream is being used
            assertDoesNotThrow(() -> verify(spylist).stream(),
                    ".stream() and stream methods should be called on the list.\n\n");
            //testing that a for-each loop is not being used
            assertDoesNotThrow(() -> verify(spylist, never()).iterator(),
                    "A for loop should not be used on the list,"
                            + ".stream() and stream methods should be used instead.\n\n");
            //verify that Collectors is being used
            assertThrows(NoInteractionsWanted.class,
                    () -> collectors.verifyNoInteractions(),
                    "Expected Collectors to be used.\n\n");
        }

    }



    @Test
    public void vegetarianDishes_usesStreamAndCollectors(){
        List<Dish> spylist = spy(ALL_DISHES);

        try(MockedStatic<Collectors> collectors = Mockito.mockStatic(Collectors.class)){

            try {
                CollectingDrills.vegetarianDishes(spylist);
            }catch(NullPointerException e){
                //catching the null pointer from the mock if collectors is used
                //I chose to not mock the interaction to not give away the answer
            }

            //verify that stream is being used
            assertDoesNotThrow(() -> verify(spylist).stream(),
                    ".stream() and stream methods should be called on the list.\n\n");
            //testing that a for-each loop is not being used
            assertDoesNotThrow(() -> verify(spylist, never()).iterator(),
                    "A for loop should not be used on the list,"
                            + ".stream() and stream methods should be used instead.\n\n");
            //verify that Collectors is being used
            assertThrows(NoInteractionsWanted.class,
                    () -> collectors.verifyNoInteractions(),
                    "Expected Collectors to be used.\n\n");
        }

    }

    @Test
    public void getAverageCalories_usesStreamAndCollectors(){
        List<Dish> spylist = spy(ALL_DISHES);

        try(MockedStatic<Collectors> collectors = Mockito.mockStatic(Collectors.class)){

            try {
                CollectingDrills.getAverageCalories(spylist);
            }catch(NullPointerException e){
                //catching the null pointer from the mock if collectors is used
                //I chose to not mock the interaction to not give away the answer
            }

            //verify that stream is being used
            assertDoesNotThrow(() -> verify(spylist).stream(),
                    ".stream() and stream methods should be called on the list.\n\n");
            //testing that a for-each loop is not being used
            assertDoesNotThrow(() -> verify(spylist, never()).iterator(),
                    "A for loop should not be used on the list,"
                            + ".stream() and stream methods should be used instead.\n\n");
            //verify that Collectors is being used
            assertThrows(NoInteractionsWanted.class,
                    () -> collectors.verifyNoInteractions(),
                    "Expected Collectors to be used.\n\n");
        }

    }

}
