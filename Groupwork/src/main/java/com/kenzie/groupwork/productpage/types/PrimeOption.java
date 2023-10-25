package com.kenzie.groupwork.productpage.types;

import java.util.Arrays;
import java.util.List;

public class PrimeOption {

    public static final String KEY = "pcb-offers-prime";
    public static final PrimeOption ANY_SHIPPING = new PrimeOption(Arrays.asList(ShippingProgramEnum.values()));

    private List<ShippingProgramEnum> supportedShipping;
    private String url;

    public PrimeOption(final List<ShippingProgramEnum> supportedShipping) {
        this.supportedShipping = supportedShipping;
    }

    public boolean matches(ShippingProgramEnum program) {
        return supportedShipping.contains(program);
    }
}
