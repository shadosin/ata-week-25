package com.kenzie.optionals.productinventory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import org.mockito.MockedStatic;
import org.mockito.exceptions.verification.WantedButNotInvoked;

public class ProductInventoryTest {

    ProductUtility productUtility;

    @BeforeEach
    public void setup() {
        productUtility = Mockito.mock(ProductUtility.class);
        when(productUtility.findProductName(anyInt())).thenReturn("");
        when(productUtility.isProductReady(anyInt())).thenReturn(false);
    }

    @Test
    public void findProductNames_productUtilityFindsNothing_returnsEmptyMap() {
        // GIVEN
        // All products have null names
        when(productUtility.findProductName(5)).thenReturn(null);
        when(productUtility.findProductName(6)).thenReturn(null);

        ProductInventory shipment = new ProductInventory(productUtility, Arrays.asList(5, 6));

        // WHEN
        // We get the product names
        Map<Integer, String> itemNames = shipment.findProductNames();


        // THEN
        // Nothing is included in the map
        Assertions.assertEquals(0, itemNames.size(), "Map wasn't empty");
    }

    @Test
    public void isProductReady_productUtilityFindsNothing_returnsEmptyOptional() {
        // GIVEN
        // The item has a null readiness
        when(productUtility.isProductReady(5)).thenReturn(null);

        ProductInventory shipment = new ProductInventory(productUtility, Arrays.asList(5));

        // WHEN
        // We check the shipment
        Optional<Boolean> itemReady = shipment.isProductReady(5);

        // THEN
        // The result is an empty optional
        Assertions.assertEquals(false, itemReady.isPresent(), "Optional wasn't empty");
    }

    @Test
    public void findProductNames_productUtilityFindsNames_returnsPopulatedMap() {
        // GIVEN
        // The items have valid names
        when(productUtility.findProductName(5)).thenReturn("Outlander");
        when(productUtility.findProductName(6)).thenReturn("Tossed Salad");

        ProductInventory shipment = new ProductInventory(productUtility, Arrays.asList(5, 6));

        // WHEN
        // We check the shipment
        Map<Integer, String> itemNames = shipment.findProductNames();

        // THEN
        // The map contains all the object names
        Assertions.assertAll("Checking to see if Map is populated correctly",
            () -> Assertions.assertEquals(2, itemNames.size(), "Map was wrong size"),
            () -> Assertions.assertEquals("Outlander", itemNames.get(5), "Map had wrong name"),
            () -> Assertions.assertEquals("Tossed Salad", itemNames.get(6), "Map had wrong name")
        );
    }

    @Test
    public void isProductReady_productIsReady_returnsPopulatedOptional() {
        // GIVEN
        // The items are ready to ship
        when(productUtility.isProductReady(6)).thenReturn(true);

        ProductInventory shipment = new ProductInventory(productUtility, Arrays.asList(6));

        // WHEN
        // We check readiness
        Optional<Boolean> itemReady = shipment.isProductReady(6);

        // THEN
        // The map contains the item
        Assertions.assertAll("Checking to see if Optional is populated correctly",
            () -> Assertions.assertEquals(true, itemReady.isPresent(), "Optional should not have been empty"),
            () -> Assertions.assertEquals(true, itemReady.get(), "Item should have been ready to ship")
        );
    }

    @Test
    public void findProductNames_nullProductUtility_throwsIllegalArgumentException() {
        // GIVEN
        // The default setup
        ProductInventory shipment = new ProductInventory(null, Arrays.asList(5, 6));

        // WHEN
        // We check shipment readiness
        Exception illegalArgument = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Map<Integer, String> itemNames = shipment.findProductNames();
        });

        // THEN
        // We throw an IllegalArgumentException with an expected message
        Assertions.assertEquals("productUtility is null", illegalArgument.getMessage(),
                "Exception had wrong message");
    }

    @Test
    public void findProductNames_nullProductIDs_throwsIllegalArgumentException() {
        // GIVEN
        // The default item setup
        ProductInventory shipment = new ProductInventory(productUtility, null);

        // WHEN
        // We check a null item list
        Exception illegalArgument = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Map<Integer, String> itemNames = shipment.findProductNames();
        });

        // THEN
        // We throw an IllegalArgumentException with an expected message
        Assertions.assertEquals("productID is null", illegalArgument.getMessage(),
                "Exception had wrong message");
    }

    @Test
    public void isProductReady_wrongItemNumber_throwsIllegalArgumentException() {
        // GIVEN
        // The called method will NPE
        when(productUtility.isProductReady(null)).thenThrow(NullPointerException.class);

        ProductInventory shipment = new ProductInventory(productUtility, Arrays.asList(5, 6));

        // WHEN
        // We check the shipment readiness
        Exception illegalArgument = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Optional itemReady = shipment.isProductReady(null);
        });

        // THEN
        // We throw an IllegalArgumentException with an expected message
        Assertions.assertEquals("The productID was null", illegalArgument.getMessage(),
                "Exception had wrong message");
    }


    //<------------------------- tests for Optionals --------------------------------->

    @Test
    public void findProductNames_usesOptional(){
        //GIVEN
        List<Integer> spyList = spy(List.of());

        Optional<ProductUtility> optionalUtility = Optional.of(productUtility);
        Optional<List<Integer>> optionalList = Optional.of(spyList);

        ProductInventory shipment = new ProductInventory(productUtility, spyList);

        try(MockedStatic<Optional> optional = Mockito.mockStatic(Optional.class)){
            optional.when(() -> Optional.ofNullable(productUtility)).thenReturn(optionalUtility);
            optional.when(() -> Optional.ofNullable(spyList)).thenReturn(optionalList);

            int temp;

            try{
                //for some reason most of the time verify counts the when call to be a use of the method
                optional.verify(() -> Optional.ofNullable(productUtility), atLeast(1));

                temp = 2;
            }catch(WantedButNotInvoked e){
                //rare occasion that verify is not counting the when call.
                temp = 1;
            }

            //hack because this needs to be final for the lambda call to work.
            final int numNeededUtil = temp;

            try{
                //for some reason most of the time verify counts the when call to be a use of the method
                optional.verify(() -> Optional.ofNullable(spyList), atLeast(1));

                temp = 2;
            }catch(WantedButNotInvoked e){
                //rare occasion that verify is not counting the when call.
                temp = 1;
            }

            //because this needs to be final for the lambda call to work.
            final int numNeededList = temp;


            //WHEN
            shipment.findProductNames();

            //THEN
            assertDoesNotThrow(() -> optional.verify(() -> Optional.ofNullable(productUtility), atLeast(numNeededUtil)),
                    "findProductNames did not use Optional.ofNullable to verify that productUtility exists");
            assertDoesNotThrow(() -> optional.verify(() -> Optional.ofNullable(spyList),atLeast(numNeededList)),
                    "findProductNames did not use Optional.ofNullable to verify that productID exists");

        }
    }

    @Test
    public void isProductReady_usesOptional(){
        //GIVEN
        List<Integer> spyList = spy(List.of());

        Optional<ProductUtility> optionalUtility = Optional.of(productUtility);

        ProductInventory shipment = new ProductInventory(productUtility, spyList);

        try(MockedStatic<Optional> optional = Mockito.mockStatic(Optional.class)){
            optional.when(() -> Optional.ofNullable(productUtility)).thenReturn(optionalUtility);

            int temp;

            try{
                //for some reason most of the time verify counts the when call to be a use of the method
                optional.verify(() -> Optional.ofNullable(productUtility), atLeast(1));

                temp = 2;
            }catch(WantedButNotInvoked e){
                //rare occasion that verify is not counting the when call.
                temp = 1;
            }

            //because this needs to be final for the lambda call to work.
            final int numNeeded = temp;

            //WHEN
            shipment.isProductReady(0);

            //THEN
            assertDoesNotThrow(() -> optional.verify(() -> Optional.ofNullable(productUtility), atLeast(numNeeded)),
                    "isProductReady did not use Optional.ofNullable to verify that productUtility exists");

        }
    }

}
