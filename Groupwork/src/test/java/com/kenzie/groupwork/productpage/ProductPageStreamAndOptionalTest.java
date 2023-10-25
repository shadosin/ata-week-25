package com.kenzie.groupwork.productpage;

import com.kenzie.groupwork.productpage.types.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.*;

import static com.kenzie.groupwork.productpage.types.ShippingProgramEnum.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class ProductPageStreamAndOptionalTest {
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
    public void getFirstBuyingOption_usesStream() {

        List<ProductV2.BuyingOption> spyList = spy(new ArrayList<>());
        // GIVEN
        when(productV2.buyingOptions()).thenReturn(spyList);

        // WHEN
        productPage.getFirstBuyingOption();

        // THEN
        assertDoesNotThrow(() -> verify(spyList).stream(),
                "GetFirstBuyingOption should use .stream() and stream methods");
    }

    @Test
    public void extractMainImageUrl_UsesStreamAndOptional() {
        // GIVEN
        ProductImagesV2 mockImagesV2 = mock(ProductImagesV2.class);

        Optional<ProductImagesV2> givenImages = spy(Optional.of(mockImagesV2));
        when(productV2.productImages()).thenReturn(givenImages);

        List<ProductImagesV2.Image> images = new ArrayList<>(Arrays.asList(mockMainImage, mockLookImage1));
        List<ProductImagesV2.Image> spyImages = spy(images);
        when(mockImagesV2.images()).thenReturn(spyImages);

        // WHEN
        productPage.extractMainImageUrl(TEST_LONG_DIMENSION);

        // THEN
        assertDoesNotThrow(() -> verify(givenImages).map(any()),
                "Optional of images should use .map() to transform the images");
        assertDoesNotThrow(() -> verify(spyImages).stream(),
                ".stream() should be used on the list of images");
    }

    @Test
    public void extractLookImageUrl_usesStreamAndOptional() {
        // GIVEN
        ProductImagesV2 mockImagesV2 = mock(ProductImagesV2.class);

        Optional<ProductImagesV2> givenImages = spy(Optional.of(mockImagesV2));
        when(productV2.productImages()).thenReturn(givenImages);

        List<ProductImagesV2.Image> mockImages = new ArrayList<>(Arrays.asList(mockMainImage, mockLookImage1));
        List<ProductImagesV2.Image> spyImages = spy(mockImages);
        when(mockImagesV2.images()).thenReturn(spyImages);

        // WHEN
        productPage.extractLookImageUrl(TEST_LONG_DIMENSION);

        // THEN
        assertDoesNotThrow(() -> verify(givenImages).map(any()),
                "Optional of images should use .map() to transform the images");
        assertDoesNotThrow(() -> verify(spyImages).stream(),
                ".stream() should be used on the list of images");
    }


    @Test
    public void getSimilarProducts_usesStream() {
        // GIVEN
        List<ProductV2> productList = new ArrayList<>(
                Arrays.asList(similarProduct1, similarProduct2, similarProduct3));
        List<ProductV2> spyList = spy(productList);
        when(productV2.getSimilarProducts()).thenReturn(spyList);

        // WHEN
        productPage.getSimilarProducts(SortByEnum.RELEVANCE,
                priceFilter,
                shippingFilter);

        // THEN
        assertDoesNotThrow(() -> verify(spyList).stream(),
                ".stream() should be used on the list");
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
