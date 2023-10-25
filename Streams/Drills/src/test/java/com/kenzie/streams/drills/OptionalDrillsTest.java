package com.kenzie.streams.drills;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.kenzie.streams.drills.resources.Car;
import com.kenzie.streams.drills.resources.Dish;
import com.kenzie.streams.drills.resources.Insurance;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.exceptions.verification.WantedButNotInvoked;

public class OptionalDrillsTest {

    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Test
    public void printOutExampleVegetarianDish_withVeggieDishes_PrintsDishes(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        String veggieDishName = "I has a name";
        Dish veggieDish = Dish.builder()
                .withName(veggieDishName)
                .withIsVegetarian(true)
                .build();

        OptionalDrills.printOutExampleVegetarianDish(ImmutableList.of(veggieDish));

        assertTrue(outContent.toString().contains(veggieDishName), "The veggie dish's name should be printed when included");

        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void printOutExampleVegetarianDish_withNonVeggieDishes_PrintsOnlyVeggieDishes(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        String veggieDishName = "I has a name";
        Dish veggieDish = Dish.builder()
                .withName(veggieDishName)
                .withIsVegetarian(true)
                .build();

        String nonVeggieDishName = "I has non veggie name";
        Dish nonVeggieDish = Dish.builder()
                .withName(nonVeggieDishName)
                .withIsVegetarian(false)
                .build();

        OptionalDrills.printOutExampleVegetarianDish(ImmutableList.of(veggieDish, nonVeggieDish));

        assertTrue(outContent.toString().contains(veggieDishName), "The veggie dish's name should be printed when included");
        assertFalse(outContent.toString().contains(nonVeggieDishName), "The non veggie dish's name should not be printed when included");

        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void printOutExampleVegetarianDish_withNoVeggieDishes_DoesNotPrintNonVeggieDishes(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        String nonVeggieDishName = "I has non veggie name";
        Dish nonVeggieDish = Dish.builder()
                .withName(nonVeggieDishName)
                .withIsVegetarian(false)
                .build();

        OptionalDrills.printOutExampleVegetarianDish(ImmutableList.of(nonVeggieDish));

        assertFalse(outContent.toString().contains(nonVeggieDishName), "The non veggie dish's name should not be printed when included");

        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void nonNullMenu_nonNullMenu_returnsMenu() {
        Dish dish = Dish.builder().withName("I has a name").build();
        List<Dish> dishes = OptionalDrills.nonNullMenu(ImmutableList.of(dish));

        assertNotNull(dishes,
            String.format("Expected returnNullDishList() with dish list %s to return a non-null result.",
                ImmutableList.of(dish)));

        assertFalse(dishes.isEmpty(),
            String.format("Expected returnNullDishList() with dish list %s to return a non-empty result.",
                ImmutableList.of(dish)));

        assertTrue(dishes.contains(dish),
                String.format("Expected returnNullDishList() with dish list %s to contain dish %s.",
                    ImmutableList.of(dish),
                    dish));
    }

    @Test
    public void nonNullMenu_nullMenu_returnsEmptyMenu() {
        List<Dish> dishes = OptionalDrills.nonNullMenu(null);

        assertNotNull(dishes,
            "Expected returnNullDishList() with a null dish list to return a non-null result.");

        assertTrue(dishes.isEmpty(),
            "Expected returnNullDishList() with a null dish list to return a non-null result.");
    }

    @Test
    public void getDishName_withDish_returnsName() {
        Optional<String> optional = OptionalDrills.getDishName(Dish.builder().withName("I has a name").build());
        assertTrue(optional.isPresent());
        assertEquals("I has a name", optional.get());
    }

    @Test
    public void getDishName_noDish_returnsEmpty() {
        Optional<String> optional = OptionalDrills.getDishName(null);
        assertFalse(optional.isPresent());
    }

    @Test
    public void getExistingInsuranceName_withName_returnsName() {
        Optional<String> optional = OptionalDrills.getExistingInsuranceName(new Car(new Insurance("insurance")));
        assertTrue(optional.isPresent());
        assertEquals("insurance", optional.get());
    }

    @Test
    public void getExistingInsuranceName_noInsuranceName_returnsEmpty() {
        //GIVEN/WHEN
        Optional<String> optional = OptionalDrills.getExistingInsuranceName(new Car(new Insurance()));

        //THEN
        assertFalse(optional.isPresent());
    }

    @Test
    public void findCheapestInsuanceName_noInsuranceName_returnsEmpty() {
        //GIVEN/WHEN
        Optional<String> optional = OptionalDrills.findCheapestInsuranceName(new Car(new Insurance()));

        //THEN
        assertFalse(optional.isPresent());
    }

    @Test
    public void findCheapestInsuranceName_noCar_returnsEmpty() {
        //given
        Optional<Car> car = Optional.empty();

        //WHEN
        Optional<String> optional = OptionalDrills.findCheapestInsuranceName(car);

        //THEN
        assertFalse(optional.isPresent());
    }

    @Test
    public void findCheapestInsuranceName_withCar_returnsCheapestInsuranceName() {
        //WHEN
        //default setup

        //THEN
        Optional<String> optional = OptionalDrills.findCheapestInsuranceName(Optional.of(new Car(new Insurance())));

        //THEN
        assertTrue(optional.isPresent());
        assertEquals("Amazon Insurance", optional.get());
    }

    //<----------------- Tests verifying optional was used (non-standard tests) ----------------->

    @Test
    public void getExistingInsuranceName_usesOptional(){
        Car car = new Car(new Insurance("insurance"));

        //anything using the thing being mocked has to happen outside the try
        Optional<Car> empty = Optional.of(car);

        try(MockedStatic<Optional> optional = Mockito.mockStatic(Optional.class)){
            optional.when(() -> Optional.ofNullable(car))
                    .thenReturn(empty);

            int temp;

            try{
                //for some reason most of the time verify counts the when call to be a use of the method
                optional.verify(() -> Optional.ofNullable(car), atLeast(1));

                temp = 2;
            }catch(WantedButNotInvoked e){
                //rare occasion that verify is not counting the when call.
                temp = 1;
            }

            //this needs to be final for the lambda call to work.
            final int numNeeded = temp;

            //WHEN
            try {
                OptionalDrills.getExistingInsuranceName(car);
            }catch(Exception e){
                //Do nothing because we expect the error and it doesn't matter for this test
            }

            //THEN
            //have to verify one more time than wanted because somehow using it in when() counts as once...
            assertDoesNotThrow(() -> optional.verify(() -> Optional.ofNullable(car),times(numNeeded)),
                    "getExistingInsuranceName should use Optional.ofNullable on the input.\n\n");
        }
    }


    @Test
    public void findCheapestInsuranceNameOptional_usesMap(){
        //GIVEN
        Car car = new Car(new Insurance("insurance"));

        Optional<Car> optional = spy(Optional.of(car));

        //WHEN
        OptionalDrills.findCheapestInsuranceName(optional);

        //THEN
        assertDoesNotThrow(() -> verify(optional).map(any()),
                "findCheapestInsuranceNameOptional should use .map on the optional. \n\n");
    }

}
