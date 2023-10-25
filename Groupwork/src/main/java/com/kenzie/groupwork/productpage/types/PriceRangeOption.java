package com.kenzie.groupwork.productpage.types;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigDecimal;
import java.util.Map;

public class PriceRangeOption {

    public static final String KEY = "pcb-offers-price-range";

    public static final Map<PriceRangeEnum, String> DISPLAY_STRINGS = new ImmutableMap.Builder<PriceRangeEnum, String>()
        .put(PriceRangeEnum.ANY, StringUtils.EMPTY)
        .put(PriceRangeEnum.PRICE_0_TO_25, "Under $25")
        .put(PriceRangeEnum.PRICE_25_TO_50, "$25 to $50")
        .put(PriceRangeEnum.PRICE_50_TO_100, "$50 to $100")
        .put(PriceRangeEnum.PRICE_100_TO_200, "$100 to $200")
        .put(PriceRangeEnum.PRICE_200_TO_ANY, "$200 & Above")
        .build();

    @SuppressWarnings("checkstyle:magicnumber")
    public static final Map<PriceRangeEnum, Pair<BigDecimal, BigDecimal>> RANGES =
        new ImmutableMap.Builder<PriceRangeEnum, Pair<BigDecimal, BigDecimal>>()
            .put(PriceRangeEnum.ANY, Pair.of(BigDecimal.ZERO, null))
            .put(PriceRangeEnum.PRICE_0_TO_25, Pair.of(BigDecimal.valueOf(0), BigDecimal.valueOf(25)))
            .put(PriceRangeEnum.PRICE_25_TO_50, Pair.of(BigDecimal.valueOf(25), BigDecimal.valueOf(50)))
            .put(PriceRangeEnum.PRICE_50_TO_100, Pair.of(BigDecimal.valueOf(50), BigDecimal.valueOf(100)))
            .put(PriceRangeEnum.PRICE_100_TO_200, Pair.of(BigDecimal.valueOf(100), BigDecimal.valueOf(200)))
            .put(PriceRangeEnum.PRICE_200_TO_ANY, Pair.of(BigDecimal.valueOf(200), null))
            .build();

    private final PriceRangeEnum value;
    private final String displayString;

    private final BigDecimal min;
    private final BigDecimal max;

    public PriceRangeOption(final PriceRangeEnum value) {
        this.value = value;
        displayString = DISPLAY_STRINGS.get(value);
        final Pair<BigDecimal, BigDecimal> range = RANGES.get(value);
        min = range.getLeft();
        max = range.getRight();
    }

    /**
     * Checks if price is within price range.
     *
     * @param price to check
     * @return check result
     */
    public boolean priceIsWithin(final BigDecimal price) {
        return (min == null || min.compareTo(price) <= 0) && (max == null || max.compareTo(price) >= 0);
    }
}
