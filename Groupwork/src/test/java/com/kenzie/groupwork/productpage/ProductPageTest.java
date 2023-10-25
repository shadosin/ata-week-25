package com.kenzie.groupwork.productpage;

import com.kenzie.groupwork.productpage.types.PriceRangeOption;
import com.kenzie.groupwork.productpage.types.PrimeOption;
import com.kenzie.groupwork.productpage.types.ProductImagesV2;
import com.kenzie.groupwork.productpage.types.ProductV2;
import com.kenzie.groupwork.productpage.types.ShippingProgramEnum;
import com.kenzie.groupwork.productpage.types.SortByEnum;
import com.kenzie.groupwork.productpage.types.StyledMedia;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.kenzie.groupwork.productpage.types.ShippingProgramEnum.ADDON;
import static com.kenzie.groupwork.productpage.types.ShippingProgramEnum.FRESH;
import static com.kenzie.groupwork.productpage.types.ShippingProgramEnum.NONPRIME;
import static com.kenzie.groupwork.productpage.types.ShippingProgramEnum.PANTRY;
import static com.kenzie.groupwork.productpage.types.ShippingProgramEnum.PRIME;
import static com.kenzie.groupwork.productpage.types.ShippingProgramEnum.PRIMENOW;
import static com.kenzie.groupwork.productpage.types.ShippingProgramEnum.UNKNOWN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ProductPageTest {

    private static final Integer TEST_LONG_DIMENSION = 120;
    private static final Integer TEST_WIDTH = 200;
    private static final Integer TEST_HEIGHT = 200;
    private static final Integer TEST_LOOK_WIDTH = 100;
    private static final Integer TEST_LOOK_HEIGHT = 200;

    @Mock
    ProductV2 productV2;

    @Mock
    ProductV2 similarProduct1;

    @Mock
    ProductV2 similarProduct2;

    @Mock
    ProductV2 similarProduct3;

    @Mock
    ProductImagesV2.Image mockMainImage;

    @Mock
    ProductImagesV2.Image mockLookImage1;

    @Mock
    ProductImagesV2.Image mockLookImage2;

    @Mock
    PriceRangeOption priceFilter;

    @Mock
    PrimeOption shippingFilter;

    private final StyledMedia mainMedia = new StyledMedia.Builder(TEST_WIDTH, TEST_HEIGHT).build();

    private final StyledMedia lookMedia = new StyledMedia.Builder(TEST_LOOK_WIDTH, TEST_LOOK_HEIGHT).build();

    private ProductPage productPage;

    @BeforeEach
    public void setup() {
        initMocks(this);
        productPage = new ProductPage(productV2);
        when(mockMainImage.lowRes()).thenReturn(mainMedia);
        when(mockMainImage.variant()).thenReturn(null);
        when(mockLookImage1.lowRes()).thenReturn(lookMedia);
        when(mockLookImage1.variant()).thenReturn("LOOK");
        when(mockLookImage2.lowRes()).thenReturn(lookMedia);
        when(mockLookImage2.variant()).thenReturn("LOOK");
        similarProduct1 = mockProduct(BigDecimal.TEN, BigDecimal.ONE, Collections.singletonList(PRIME));
        similarProduct2 = mockProduct(BigDecimal.TEN, BigDecimal.ONE, Collections.singletonList(NONPRIME));
        similarProduct3 = mockProduct(BigDecimal.TEN, BigDecimal.ONE, Arrays.asList(PRIMENOW, PRIME));
        when(productV2.getSimilarProducts()).thenReturn(
            Arrays.asList(similarProduct1, similarProduct2, similarProduct3));
        when(priceFilter.priceIsWithin(any())).thenReturn(true);
        when(shippingFilter.matches(any())).thenReturn(true);
    }

    @Test
    public void getFirstBuyingOption_noOption_returnsEmpty() {
        // GIVEN
        when(productV2.buyingOptions()).thenReturn(Collections.emptyList());

        // WHEN
        Optional<ProductV2.BuyingOption> firstBuyingOption = productPage.getFirstBuyingOption();

        // THEN
        assertFalse(firstBuyingOption.isPresent(), "Empty buying options should return empty Optional!");
    }

    @Test
    public void getFirstBuyingOption_populatedOptions_returnsFirst() {
        // GIVEN
        ProductV2.BuyingOption buyingOption1 = mock(ProductV2.BuyingOption.class);
        ProductV2.BuyingOption buyingOption2 = mock(ProductV2.BuyingOption.class);
        when(productV2.buyingOptions()).thenReturn(Arrays.asList(buyingOption1, buyingOption2));

        // WHEN
        Optional<ProductV2.BuyingOption> firstBuyingOption = productPage.getFirstBuyingOption();

        // THEN
        assertTrue(firstBuyingOption.isPresent(), "Populated buying options should return populated Optional!");
        assertEquals(buyingOption1, firstBuyingOption.get(), "Optional should have first valid BuyingOption!");
    }

    @Test
    public void extractMainImageUrl_withMainImage_scalesMainImageUrl() {
        // GIVEN
        ProductImagesV2 mockImagesV2 = mock(ProductImagesV2.class);
        List<ProductImagesV2.Image> mockImages = Arrays.asList(mockMainImage, mockLookImage1);
        Optional<ProductImagesV2> givenImages = Optional.of(mockImagesV2);
        when(productV2.productImages()).thenReturn(givenImages);
        when(mockImagesV2.images()).thenReturn(mockImages);

        // WHEN
        Optional<String> mainImageUrl = productPage.extractMainImageUrl(TEST_LONG_DIMENSION);

        // THEN
        assertTrue(mainImageUrl.isPresent(), "Valid main image should return populated Optional!");
        String actualUrl = mainImageUrl.get();
        String expectedUrl = mainMedia.styleBuilder()
            .scaleToLongest(TEST_LONG_DIMENSION)
            .build()
            .url();
        assertEquals(expectedUrl, actualUrl, "extractMainImageUrl should return URL for main image!");
    }

    @Test
    public void extractMainImageUrl_withEmptyImageList_returnsEmptyOptional() {
        // GIVEN
        ProductImagesV2 mockImagesV2 = mock(ProductImagesV2.class);
        List<ProductImagesV2.Image> emptyImages = Collections.emptyList();
        Optional<ProductImagesV2> givenImages = Optional.of(mockImagesV2);
        when(productV2.productImages()).thenReturn(givenImages);
        when(mockImagesV2.images()).thenReturn(emptyImages);

        // WHEN
        Optional<String> mainImageUrl = productPage.extractMainImageUrl(TEST_LONG_DIMENSION);

        // THEN
        assertFalse(mainImageUrl.isPresent(), "Empty image list should return empty Optional!");
    }

    @Test
    public void extractMainImageUrl_withNoImageV2_returnsEmptyOptional() {
        // GIVEN
        Optional<ProductImagesV2> givenImages = Optional.empty();
        when(productV2.productImages()).thenReturn(givenImages);

        // WHEN
        Optional<String> mainImageUrl = productPage.extractMainImageUrl(TEST_LONG_DIMENSION);

        // THEN
        assertFalse(mainImageUrl.isPresent(), "Missing image container should return empty Optional!");
    }

    @Test
    public void extractMainImageUrl_withAllNullImageUrls_returnsEmptyOptional() {
        // GIVEN
        ProductImagesV2 mockImagesV2 = mock(ProductImagesV2.class);
        List<ProductImagesV2.Image> mockImages = Collections.singletonList(mockMainImage);
        Optional<ProductImagesV2> givenImages = Optional.of(mockImagesV2);
        when(mockMainImage.lowRes()).thenReturn(new StyledMedia.Builder(0, 0).build());
        when(productV2.productImages()).thenReturn(givenImages);
        when(mockImagesV2.images()).thenReturn(mockImages);

        // WHEN
        Optional<String> mainImageUrl = productPage.extractMainImageUrl(TEST_LONG_DIMENSION);

        // THEN
        assertFalse(mainImageUrl.isPresent(), "Invalid main image URL should return empty Optional!");
    }

    @Test
    public void extractMainImageUrl_withOneValidImageUrl_returnsScaledImageUrl() {
        // GIVEN
        ProductImagesV2 mockImagesV2 = mock(ProductImagesV2.class);
        List<ProductImagesV2.Image> mockImages = Arrays.asList(mockMainImage, mockLookImage1);
        Optional<ProductImagesV2> givenImages = Optional.of(mockImagesV2);
        when(mockMainImage.lowRes()).thenReturn(new StyledMedia.Builder(0, 0).build());
        when(productV2.productImages()).thenReturn(givenImages);
        when(mockImagesV2.images()).thenReturn(mockImages);

        // WHEN
        Optional<String> mainImageUrl = productPage.extractMainImageUrl(TEST_LONG_DIMENSION);

        // THEN
        assertTrue(mainImageUrl.isPresent(), "Valid secondary image should return populated Optional!");
        String actualUrl = mainImageUrl.get();
        String expectedUrl = lookMedia.styleBuilder()
            .scaleToLongest(TEST_LONG_DIMENSION)
            .build()
            .url();
        assertEquals(expectedUrl, actualUrl, "Invalid main image url should fall back to secondary image!");
    }

    @Test
    public void extractLookImageUrl_withLookImage_scalesLookImageUrl() {
        // GIVEN
        ProductImagesV2 mockImagesV2 = mock(ProductImagesV2.class);
        List<ProductImagesV2.Image> mockImages = Arrays.asList(mockMainImage, mockLookImage1);
        Optional<ProductImagesV2> givenImages = Optional.of(mockImagesV2);
        when(productV2.productImages()).thenReturn(givenImages);
        when(mockImagesV2.images()).thenReturn(mockImages);

        // WHEN
        Optional<String> lookImageUrl = productPage.extractLookImageUrl(TEST_LONG_DIMENSION);

        // THEN
        assertTrue(lookImageUrl.isPresent(), "Valid look image should return populated Optional!");
        String actualUrl = lookImageUrl.get();
        String expectedUrl = lookMedia.styleBuilder()
            .scaleToLongest(TEST_LONG_DIMENSION)
            .build()
            .url();
        assertEquals(expectedUrl, actualUrl, "Should ignore main URL for valid look URL!");
    }

    @Test
    public void extractLookImageUrl_withEmptyImageList_returnsEmptyOptional() {
        // GIVEN
        ProductImagesV2 mockImagesV2 = mock(ProductImagesV2.class);
        List<ProductImagesV2.Image> mockImages = Collections.emptyList();
        Optional<ProductImagesV2> givenImages = Optional.of(mockImagesV2);
        when(productV2.productImages()).thenReturn(givenImages);
        when(mockImagesV2.images()).thenReturn(mockImages);

        // WHEN
        Optional<String> lookImageUrl = productPage.extractLookImageUrl(TEST_LONG_DIMENSION);

        // THEN
        assertFalse(lookImageUrl.isPresent(), "Empty look images should return empty Optional!");
    }

    @Test
    public void extractLookImageUrl_withNoImageV2_returnsEmptyOptional() {
        // GIVEN
        Optional<ProductImagesV2> givenImages = Optional.empty();
        when(productV2.productImages()).thenReturn(givenImages);

        // WHEN
        Optional<String> lookImageUrl = productPage.extractLookImageUrl(TEST_LONG_DIMENSION);

        // THEN
        assertFalse(lookImageUrl.isPresent(), "Empty (look) image container should return empty Optional!");
    }

    @Test
    public void extractLookImageUrl_withAllNullImageUrls_returnsEmptyOptional() {
        // GIVEN
        ProductImagesV2 mockImagesV2 = mock(ProductImagesV2.class);
        List<ProductImagesV2.Image> mockImages = Collections.singletonList(mockMainImage);
        Optional<ProductImagesV2> givenImages = Optional.of(mockImagesV2);
        when(mockMainImage.lowRes()).thenReturn(new StyledMedia.Builder(0, 0).build());
        when(productV2.productImages()).thenReturn(givenImages);
        when(mockImagesV2.images()).thenReturn(mockImages);

        // WHEN
        Optional<String> lookImageUrl = productPage.extractLookImageUrl(TEST_LONG_DIMENSION);

        // THEN
        assertFalse(lookImageUrl.isPresent(), "Invalid look images should return empty Optional!");
    }

    @Test
    public void extractMainImageUrl_withOneValidLookImageUrl_returnsScaledImageUrl() {
        // GIVEN
        ProductImagesV2 mockImagesV2 = mock(ProductImagesV2.class);
        List<ProductImagesV2.Image> mockImages = Arrays.asList(mockMainImage, mockLookImage1, mockLookImage2);
        Optional<ProductImagesV2> givenImages = Optional.of(mockImagesV2);
        when(mockLookImage1.lowRes()).thenReturn(new StyledMedia.Builder(0, 0).build());
        when(productV2.productImages()).thenReturn(givenImages);
        when(mockImagesV2.images()).thenReturn(mockImages);

        // WHEN
        Optional<String> lookImageUrl = productPage.extractLookImageUrl(TEST_LONG_DIMENSION);

        // THEN
        assertTrue(lookImageUrl.isPresent(), "Valid look URL should return populated Optional!");
        String actualUrl = lookImageUrl.get();
        String expectedUrl = lookMedia.styleBuilder()
            .scaleToLongest(TEST_LONG_DIMENSION)
            .build()
            .url();
        assertEquals(expectedUrl, actualUrl, "Valid look URL should ignore invalid URLs!");
    }

    @Test
    public void getSimilarProducts_withInvalidProduct_removesInvalidProduct() {
        // GIVEN
        when(similarProduct2.isValid()).thenReturn(false);

        // WHEN
        List<ProductV2> similarProducts = productPage.getSimilarProducts(SortByEnum.RELEVANCE,
            priceFilter,
            shippingFilter);

        // THEN
        assertEquals(2, similarProducts.size(), "Similar products should return all valid products!");
        assertEquals(similarProduct1, similarProducts.get(0), "Similar product order should be unchanged!");
        assertEquals(similarProduct3, similarProducts.get(1), "Similar product order should be unchanged!");
    }

    @Test
    public void getSimilarProducts_withPriceLowToHighSort_sortsAsExpected() {
        // GIVEN
        when(similarProduct1.getPrice()).thenReturn(BigDecimal.valueOf(2.0));
        when(similarProduct2.getPrice()).thenReturn(BigDecimal.valueOf(1.0));
        when(similarProduct3.getPrice()).thenReturn(BigDecimal.valueOf(3.0));

        // WHEN
        List<ProductV2> similarProducts = productPage.getSimilarProducts(SortByEnum.PRICE_LOW_TO_HIGH,
            priceFilter,
            shippingFilter);

        // THEN
        assertEquals(3, similarProducts.size(), "Sort should not exclude any items!");
        assertEquals(similarProduct2, similarProducts.get(0), "Similar products should be sorted low to high!");
        assertEquals(similarProduct1, similarProducts.get(1), "Similar products should be sorted low to high!");
        assertEquals(similarProduct3, similarProducts.get(2), "Similar products should be sorted low to high!");
    }

    @Test
    public void getSimilarProducts_withPriceHighToLowSort_sortsAsExpected() {
        // GIVEN
        when(similarProduct1.getPrice()).thenReturn(BigDecimal.valueOf(2.0));
        when(similarProduct2.getPrice()).thenReturn(BigDecimal.valueOf(1.0));
        when(similarProduct3.getPrice()).thenReturn(BigDecimal.valueOf(3.0));

        // WHEN
        List<ProductV2> similarProducts = productPage.getSimilarProducts(SortByEnum.PRICE_HIGH_TO_LOW,
            priceFilter,
            shippingFilter);

        // THEN
        assertEquals(3, similarProducts.size(), "Similar products should return all valid items!");
        assertEquals(similarProduct3, similarProducts.get(0), "Similar products should be sorted high to low!");
        assertEquals(similarProduct1, similarProducts.get(1), "Similar products should be sorted high to low!");
        assertEquals(similarProduct2, similarProducts.get(2), "Similar products should be sorted high to low!");
    }

    @Test
    public void getSimilarProducts_withRewardHighToLowSort_sortsAsExpected() {
        // GIVEN
        when(similarProduct1.getTotalBenefitAmount()).thenReturn(BigDecimal.valueOf(2.0));
        when(similarProduct1.getPrice()).thenReturn(BigDecimal.valueOf(10.0));
        when(similarProduct2.getTotalBenefitAmount()).thenReturn(BigDecimal.valueOf(1.0));
        when(similarProduct2.getPrice()).thenReturn(BigDecimal.valueOf(10.0));
        when(similarProduct3.getTotalBenefitAmount()).thenReturn(BigDecimal.valueOf(3.0));
        when(similarProduct3.getPrice()).thenReturn(BigDecimal.valueOf(10.0));

        // WHEN
        List<ProductV2> similarProducts = productPage.getSimilarProducts(SortByEnum.REWARD_HIGH_TO_LOW,
            priceFilter,
            shippingFilter);

        // THEN
        assertEquals(3, similarProducts.size(), "Sort should return all valid products!");
        assertEquals(similarProduct3, similarProducts.get(0), "Similar products should be sorted high to low reward!");
        assertEquals(similarProduct1, similarProducts.get(1), "Similar products should be sorted high to low reward!");
        assertEquals(similarProduct2, similarProducts.get(2), "Similar products should be sorted high to low reward!");
    }

    @Test
    public void getSimilarProducts_withRewardLowToHighSort_sortsAsExpected() {
        // GIVEN
        when(similarProduct1.getTotalBenefitAmount()).thenReturn(BigDecimal.valueOf(2.0));
        when(similarProduct2.getTotalBenefitAmount()).thenReturn(BigDecimal.valueOf(1.0));
        when(similarProduct3.getTotalBenefitAmount()).thenReturn(BigDecimal.valueOf(3.0));

        // WHEN
        List<ProductV2> similarProducts = productPage.getSimilarProducts(SortByEnum.REWARD_LOW_TO_HIGH,
            priceFilter,
            shippingFilter);

        // THEN
        assertEquals(3, similarProducts.size(), "Sort should return all valid products!");
        assertEquals(similarProduct2, similarProducts.get(0), "Similar products should be sorted low to high reward!");
        assertEquals(similarProduct1, similarProducts.get(1), "Similar products should be sorted low to high reward!");
        assertEquals(similarProduct3, similarProducts.get(2), "Similar products should be sorted low to high reward!");
    }

    @Test
    public void getSimilarProducts_withNoFilters_returnsOriginalOrder() {
        // GIVEN
        // Similar products in an arbitrary order
        when(productV2.getSimilarProducts()).thenReturn(
            Arrays.asList(similarProduct2, similarProduct3, similarProduct1));

        // WHEN
        List<ProductV2> similarProducts = productPage.getSimilarProducts(SortByEnum.RELEVANCE,
            priceFilter,
            shippingFilter);

        // THEN
        assertEquals(3, similarProducts.size(), "Sort should return all valid products!");
        assertEquals(similarProduct2, similarProducts.get(0), "Similar products should retain original order!");
        assertEquals(similarProduct3, similarProducts.get(1), "Similar products should retain original order!");
        assertEquals(similarProduct1, similarProducts.get(2), "Similar products should retain original order!");
    }

    @Test
    public void getSimilarProducts_whenSimilarProductsEmpty_returnsEmptyList() {
        // GIVEN
        when(productV2.getSimilarProducts()).thenReturn(Collections.emptyList());

        // WHEN
        List<ProductV2> similarProducts = productPage.getSimilarProducts(SortByEnum.REWARD_LOW_TO_HIGH,
            priceFilter,
            shippingFilter);

        // THEN
        assertTrue(similarProducts.isEmpty(), "Empty similar products should return empty List!");
    }

    @Test
    public void getSimilarProducts_whenSimilarProductsNull_returnsEmptyList() {
        // GIVEN
        when(productV2.getSimilarProducts()).thenReturn(null);

        // WHEN
        List<ProductV2> similarProducts = productPage.getSimilarProducts(SortByEnum.REWARD_LOW_TO_HIGH,
            priceFilter,
            shippingFilter);

        // THEN
        assertTrue(similarProducts.isEmpty(), "Null similar products should return empty List!");
    }

    @Test
    public void getSimilarProducts_withPriceFilter_removesIneligibleItems() {
        // GIVEN
        // The filter will eliminate some items
        when(priceFilter.priceIsWithin(any())).thenReturn(false, true, true);

        // WHEN
        List<ProductV2> similarProducts = productPage.getSimilarProducts(SortByEnum.RELEVANCE,
            priceFilter,
            shippingFilter);

        // THEN
        assertEquals(2, similarProducts.size(), "Price filter should include all eligible products!");
        assertEquals(similarProduct2, similarProducts.get(0), "Product order should be unchanged!");
        assertEquals(similarProduct3, similarProducts.get(1), "Product order should be unchanged!");
    }

    @Test
    public void getSimilarProducts_withNoEligiblePrices_returnsEmptyList() {
        // GIVEN
        // The filter will eliminate all items
        when(priceFilter.priceIsWithin(any())).thenReturn(false);

        // WHEN
        List<ProductV2> similarProducts = productPage.getSimilarProducts(SortByEnum.RELEVANCE,
            priceFilter,
            shippingFilter);

        // THEN
        assertTrue(similarProducts.isEmpty(), "No eligible prices should return empty List!");
    }

    @Test
    public void getSimilarProducts_withShippingFilter_removesIneligibleProducts() {
        // GIVEN
        // The shipping filter will remove some items
        when(shippingFilter.matches(any())).thenReturn(true, false, true);

        // WHEN
        List<ProductV2> similarProducts = productPage.getSimilarProducts(SortByEnum.RELEVANCE,
            priceFilter,
            shippingFilter);

        // THEN
        assertEquals(2, similarProducts.size(), "Shipping filter should include all eligible products!");
        assertEquals(similarProduct1, similarProducts.get(0), "Product order should be unchanged!");
        assertEquals(similarProduct3, similarProducts.get(1), "Product order should be unchanged!");
    }

    @Test
    public void getSimilarProducts_withShippingFilterAndProductsWithMultipleOptions_removesIneligibleProducts() {
        // GIVEN
        // Similar items with multiple shipping options
        when(similarProduct1.getShippingPrograms()).thenReturn(Arrays.asList(PRIME, PRIMENOW));
        when(similarProduct2.getShippingPrograms()).thenReturn(Arrays.asList(PRIMENOW, PANTRY, FRESH));
        when(similarProduct3.getShippingPrograms()).thenReturn(Arrays.asList(UNKNOWN, PRIME));
        // The shipping filter will remove some items
        when(shippingFilter.matches(any())).thenReturn(false);
        when(shippingFilter.matches(PRIMENOW)).thenReturn(true);

        // WHEN
        List<ProductV2> similarProducts = productPage.getSimilarProducts(SortByEnum.RELEVANCE,
            priceFilter,
            shippingFilter);

        // THEN
        assertEquals(2, similarProducts.size(), "Shipping filter should remove all ineligible products!");
        assertEquals(similarProduct1, similarProducts.get(0), "Product order should be unchanged!");
        assertEquals(similarProduct2, similarProducts.get(1), "Product order should be unchanged!");
    }

    @Test
    public void getSimilarProducts_withNoEligibleShippingProducts_returnsEmptyList() {
        // GIVEN
        // The shipping filter will remove all items
        when(shippingFilter.matches(any())).thenReturn(false);

        // WHEN
        List<ProductV2> similarProducts = productPage.getSimilarProducts(SortByEnum.RELEVANCE,
            priceFilter,
            shippingFilter);

        // THEN
        assertTrue(similarProducts.isEmpty(), "Should return empty List when all products ineligible for shipping!");
    }

    @Test
    public void getSimilarProducts_multipleOptions_returnsSortedFilteredList() {
        // GIVEN
        // Items that will be filtered by price and shipping, and sorted by reward
        BigDecimal inPrice1 = BigDecimal.valueOf(25.0);
        BigDecimal inPrice2 = BigDecimal.valueOf(30.0);
        BigDecimal inPrice3 = BigDecimal.valueOf(45.0);
        BigDecimal inPrice4 = BigDecimal.valueOf(50.0);
        BigDecimal outPrice1 = BigDecimal.valueOf(55.0);
        BigDecimal outPrice2 = BigDecimal.valueOf(100.0);
        similarProduct1 = mockProduct(inPrice1, BigDecimal.ZERO, Arrays.asList(NONPRIME, UNKNOWN));
        similarProduct2 = mockProduct(inPrice2, BigDecimal.ONE, Arrays.asList(PRIME, ADDON));
        similarProduct3 = mockProduct(outPrice2, BigDecimal.TEN, Arrays.asList(PANTRY, FRESH));
        ProductV2 product4 = mockProduct(inPrice3, BigDecimal.TEN, Collections.singletonList(PRIMENOW));
        ProductV2 product5 = mockProduct(outPrice1, BigDecimal.ONE, Arrays.asList(PRIME, PRIMENOW));
        ProductV2 product6 = mockProduct(inPrice4, BigDecimal.valueOf(5.0), Arrays.asList(PANTRY, FRESH));
        when(productV2.getSimilarProducts()).thenReturn(
            Arrays.asList(similarProduct1, similarProduct2, similarProduct3, product4, product5, product6));
        // The price filter will only accept the in-prices
        when(priceFilter.priceIsWithin(any())).thenReturn(false);
        when(priceFilter.priceIsWithin(inPrice1)).thenReturn(true);
        when(priceFilter.priceIsWithin(inPrice2)).thenReturn(true);
        when(priceFilter.priceIsWithin(inPrice3)).thenReturn(true);
        when(priceFilter.priceIsWithin(inPrice4)).thenReturn(true);
        // The shipping filter will only accept PRIME and PRIMENOW
        when(shippingFilter.matches(any())).thenReturn(false);
        when(shippingFilter.matches(PRIME)).thenReturn(true);
        when(shippingFilter.matches(PRIMENOW)).thenReturn(true);

        // WHEN
        List<ProductV2> similarProducts = productPage.getSimilarProducts(SortByEnum.REWARD_HIGH_TO_LOW,
            priceFilter,
            shippingFilter);

        // THEN
        assertNotNull(similarProducts);
        assertEquals(2, similarProducts.size(), "Only price and shipping eligible options should be included!");
        assertEquals(product4, similarProducts.get(0), "Products should be orderd by high to low reward!");
        assertEquals(similarProduct2, similarProducts.get(1), "Products should be orderd by high to low reward!");
    }

    /**
     * Helper method to mock all the calls required for a product.
     * @param price The value returned by getPrice()
     * @param benefit The value returned by getTotalBenefitAmount()
     * @param shipping The value returned by getShippingPrograms()
     * @return A mock ProductV2 useful in any ProductPage call.
     */
    private ProductV2 mockProduct(BigDecimal price, BigDecimal benefit, List<ShippingProgramEnum> shipping) {
        ProductV2 product = mock(ProductV2.class);
        when(product.isValid()).thenReturn(true);
        when(product.getPrice()).thenReturn(price);
        when(product.getTotalBenefitAmount()).thenReturn(benefit);
        when(product.getShippingPrograms()).thenReturn(shipping);
        return product;
    }
}
