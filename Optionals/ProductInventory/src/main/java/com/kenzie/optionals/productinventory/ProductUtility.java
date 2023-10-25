package com.kenzie.optionals.productinventory;

public class ProductUtility {
    // This class will be mocked in the test cases.

    /**
     * Find the name of the item with the given ID.
     * @param itemID - Item ID to find name for
     * @return name of the item
     */
    public String findProductName(Integer itemID) {
        return "";
    }

    /**
     *
     * @param itemID - Item ID to determine readiness for
     * @return - is item ready to ship?
     */
    public Boolean isProductReady(Integer itemID) {
        return false;
    }
}
