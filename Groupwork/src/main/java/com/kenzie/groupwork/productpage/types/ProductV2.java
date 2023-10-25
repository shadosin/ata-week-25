package com.kenzie.groupwork.productpage.types;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductV2 {

    String internalDatapathEntity();

    String title();

    List<BuyingOption> buyingOptions();

    String asin();

    Optional<ProductImagesV2> productImages();

    BigDecimal getTotalBenefitAmount();

    BigDecimal getPrice();

    List<ProductV2> getSimilarProducts();

    List<ShippingProgramEnum> getShippingPrograms();

    boolean isValid();

    /**
     * <img src="https://drive-render.corp.amazon.com/view/Amazon%20API/DocumentationWebsite/Hosting/buying_option_example.png"
     *   alt="Example buying options" style="height:220px;margin-left:15px;float:right;">
     *
     * <p>
     * ProductV2 introduces the concept of a "Buying Option", which did not exist in V1. It replaces the abstraction
     * of an "offer" (though some Buying Options internally use offers) to represent a single mechanism to purchase a
     * product by a customer. More specifically, a Buying Option is a unique instance of a product or service with a
     * set of offer attributes that a shopper can take action to acquire or consume. A Buying Option today is commonly
     * displayed as a selection in the accordion Buy Box on the Detail Page where the shopper can select an option with
     * different prices, availability, seller, and other offer related information to help the shopper make purchase
     * decisions. Examples of Buying Options are "Buy New", "Buy Used", "Subscribe and Save" which an offer may
     * have multiple options for shoppers to choose. A Buying Option can also be displayed as part of Search results or
     * a program such as Fresh, Pantry, and Prime Wardrobe. A Buying Option is defined by having a combination of
     * transaction terms with an associated call-to-action.
     * </p>
     */
    interface BuyingOption {

        /**
         * The type of this buying option.
         *
         * @return the type of this buying option.
         */
        String type();

        String merchant();

        String freeReturnsPolicy();

        BigDecimal price();

    }

}
