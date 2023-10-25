package com.kenzie.groupwork.productpage;

import com.kenzie.groupwork.productpage.types.PriceRangeOption;
import com.kenzie.groupwork.productpage.types.PrimeOption;
import com.kenzie.groupwork.productpage.types.ProductImagesV2;
import com.kenzie.groupwork.productpage.types.ProductV2;
import com.kenzie.groupwork.productpage.types.ShippingProgramEnum;
import com.kenzie.groupwork.productpage.types.SortByEnum;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.kenzie.groupwork.productpage.types.SortByEnum.PRICE_HIGH_TO_LOW;
import static com.kenzie.groupwork.productpage.types.SortByEnum.PRICE_LOW_TO_HIGH;
import static com.kenzie.groupwork.productpage.types.SortByEnum.REWARD_HIGH_TO_LOW;
import static com.kenzie.groupwork.productpage.types.SortByEnum.REWARD_LOW_TO_HIGH;

public class ProductPage {

    private static final String LOOK_VARIANT = "LOOK";

    private final ProductV2 productV2;

    private final Map<SortByEnum, Comparator<ProductV2>> comparatorForSortBy = createSortComparatorMap();

    public ProductPage(ProductV2 productV2) {
        this.productV2 = productV2;
    }

    public ProductV2 getProduct() {
        return productV2;
    }

    /**
     * Returns the first (winning) buying option from ProductV2.
     *
     * Golf score: 10
     * Par: 4
     * Your score: (ENTER YOUR SCORE HERE)
     *
     * @return An Optional with the winning BuyingOption, or empty if none.
     */
    public Optional<ProductV2.BuyingOption> getFirstBuyingOption() {
        //3 points: one assignment for 2 points and one method call for 1 point
        List<ProductV2.BuyingOption> buyingOptions = productV2.buyingOptions();
        //2 points: one conditional ! for 1 point, and one method call for 1 point
        if (!buyingOptions.isEmpty()) {
            //2 points: one return for 1 point and one method call for 1 point
            return buyingOptions.stream()
                    //1 point: one method call for 1 point
                .findFirst();
        }
        //2 points: one return for 1 point and one method call for 1 point
        return Optional.empty();
    }

    /**
     * Extracts the main image URL from the list of product images.
     *
     * As per https://api.corp.amazon.com/operations/TPvRr1W3vu/productImages picks the first image from the list
     * as the main image instead of using the "MAIN" image variant" which apparently is not necessarily the main image.
     *
     * Golf score: 18
     * Par: 8
     * Your score: (ENTER YOUR SCORE HERE)
     *
     * @param longestDimension The size of the longest dimension of the image.
     * @return Optional containing the image URL, or empty if no image exists.
     */
    public Optional<String> extractMainImageUrl(Integer longestDimension) {
        //3 points: one assignment for 2 points and one method call for 1 point
        Optional<ProductImagesV2> productImagesOptional = productV2.productImages();
        //1 points: one method call for 1 point
        if (productImagesOptional.isPresent()) {
            //3 points: one assignment for 2 points and one method call for 1 point
            ProductImagesV2 productImages = productImagesOptional.get();
            //3 points: one assignment for 2 points and one method call for 1 point
            List<ProductImagesV2.Image> images = productImages.images();
            //0 points: for loops declarations are free and no other methods called in declaration
            for (ProductImagesV2.Image image : images) {
                //3 points: one assignment for 2 points and one method call for 1 point
                String url = extractImageUrl(image, longestDimension);
                //1 point: one comparison for 1 point
                if (url != null) {
                    //2 points: one return for 1 point and 1 method call for 1 point
                    return Optional.of(url);
                }
            }
        }
        //2 points: one return for 1 point and 1 method call for 1 point
        return Optional.empty();
    }

    /**
     * Extract image URL for LOOK variant if it exists.
     *
     * Golf score: 24
     * Par: 11
     * Your score: (ENTER YOUR SCORE HERE)
     *
     * @param longestDimension the size of the image's longest dimension.
     * @return An Optional containing the URL of the image, or empty if no image exists.
     */
    public Optional<String> extractLookImageUrl(Integer longestDimension) {
        //3 points: one assignment for 2 points and one method call for 1 point
        Optional<ProductImagesV2> productImages = productV2.productImages();
        //1 points: one method call for 1 point
        if (productImages.isPresent()) {
            //3 points: one assignment for 2 points and one method call for 1 point
            ProductImagesV2 productImagesV2 = productImages.get();
            //3 points: one assignment for 2 points and one method call for 1 point
            List<ProductImagesV2.Image> images = productImagesV2.images();
            //0 points: for loops declarations are free and no other methods called in declaration
            for (ProductImagesV2.Image image : images) {
                //3 points: one assignment for 2 points and one method call for 1 point
                String variant = image.variant();
                //3 points: one comparison for 1 point, one conditional for 1 point, and one method call for 1 point
                if (variant != null && variant.equals(LOOK_VARIANT)) {
                    //3 points: one assignment for 2 points and one method call for 1 point
                    String url = extractImageUrl(image, longestDimension);
                    //1 point: one comparison for 1 point
                    if (url != null) {
                        //2 points: one return for 1 point and 1 method call for 1 point
                        return Optional.of(url);
                    }
                }
            }
        }
        //2 points: one return for 1 point and 1 method call for 1 point
        return Optional.empty();
    }

    /**
     * Get products to display from AAPI.
     *
     * @param sortBy sort by parameter
     * @param priceRange price range filter
     * @param primeOption prime filter
     * @return list of products
     *
     * Golf score: 22
     * Par: 18
     * Your score: (ENTER YOUR SCORE HERE)
     */
    public List<ProductV2> getSimilarProducts(final SortByEnum sortBy,
                                              final PriceRangeOption priceRange,
                                              final PrimeOption primeOption) {

        //4 points: one assignment for 2 points, two method calls for 1 point each
        Comparator<ProductV2> sorter = comparatorForSortBy.getOrDefault(sortBy, passthroughComparator());
        //3 points: one assignment for 2 points and one method call for 1 point
        final List<ProductV2> unorderedProducts = productV2.getSimilarProducts();
        //3 points: one assignment for 2 points and one constructor for 1 point
        final List<ProductV2> matchingProducts = new ArrayList<>();
        //1 point: one comparison for 1 point
        if (unorderedProducts != null) {
            //0 points: one for loop declaration with no other methods in the declaration
            for (ProductV2 product : unorderedProducts) {
                //2 point: one method call for 1 point and one conditional for 1 point
                if (Objects.nonNull(product) &&
                        //2 point: one method call for 1 point and one conditional for 1 point
                    product.isValid() &&
                        //2 points: two method calls for 1 point each
                    priceRange.priceIsWithin(product.getPrice())) {
                    //1 point: one method call inside the for loop declaraion for 1 point
                    for (ShippingProgramEnum shippingProgram : product.getShippingPrograms()) {
                        //1 point: one method call for 1 point
                        if (primeOption.matches(shippingProgram)) {
                            //1 point: one method call for 1 point
                            matchingProducts.add(product);
                            break;
                        }
                    }
                }
            }
        }
        //1 point: one method call for 1 point
        matchingProducts.sort(sorter);
        //1 point: one return for 1 point
        return matchingProducts;
    }

    /**
     * Extracts the image URL from a ProductImageV2.Image.
     */
    private String extractImageUrl(ProductImagesV2.Image image, Integer longest) {
        // Looks like a Stream or Optional, but it's a Builder.
        return image.lowRes().styleBuilder()
            .scaleToLongest(longest)
            .build()
            .url();
    }

    /**
     * Returns a Comparator that does not change order.
     * @param <T> The type of item this Comparator will compare.
     * @return a Comparator that does not change order.
     */
    private <T> Comparator<T> passthroughComparator() {
        return Comparator.comparing(other -> 0);
    }

    private Map<SortByEnum, Comparator<ProductV2>> createSortComparatorMap() {
        Map<SortByEnum, Comparator<ProductV2>> comparatorMap = new HashMap<>();
        comparatorMap.put(REWARD_LOW_TO_HIGH, Comparator.comparing(ProductV2::getTotalBenefitAmount));
        comparatorMap.put(REWARD_HIGH_TO_LOW, Comparator.comparing(ProductV2::getTotalBenefitAmount).reversed());
        comparatorMap.put(PRICE_LOW_TO_HIGH, Comparator.comparing(ProductV2::getPrice));
        comparatorMap.put(PRICE_HIGH_TO_LOW, Comparator.comparing(ProductV2::getPrice).reversed());
        return comparatorMap;
    }
}
