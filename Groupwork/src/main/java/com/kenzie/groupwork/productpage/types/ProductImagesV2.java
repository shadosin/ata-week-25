package com.kenzie.groupwork.productpage.types;

import java.util.List;

public interface ProductImagesV2 {

    /**
     * A sorted list of product images based on WDG (website display group) specific business logic, where
     * the first image is used for the detail page primary image.
     *
     * The detail page primary image is <b>not</b> necessarily the "MAIN" variant.
     *
     * @return The images representing the product.
     */
    List<Image> images();

    /**
     * Text that appears in place of an image on a webpage if the image fails to load.
     * i.e, BTFBM Women's 2019 Casual Crew Neck Ruched Stretchy Bodycon T Shirt Short Mini Dress (104Navy, Medium)
     *
     * @return The alternate text for the image.
     */
    String altText();

    interface Image {

        /**
         * The Media object containing the physicalId, height, width, and extension from which the media
         * url can be fetched for lowRes image (upto 500px in height and width), Used to render product Images on
         * Detail Pages.
         * It corresponds to LG sized image in Media Services and Applications (MSA).
         *
         * @return StyledMedia with the low resolution image details.
         */
        StyledMedia lowRes();

        /**
         * The Media object containing the physicalId, height, width, and extension from which the media
         * url can be fetched for hiRes image. Used to render hoverZoom for Product Images on Detail Pages.
         * It corresponds to RM sized image in Media Services and Applications (MSA).
         *
         * Exercise caution as using hiRes image could cause both server side and client side latency
         * depending on the usage
         *
         * @return StyledMedia with the high resolution image details.
         */
        StyledMedia hiRes();

        /**
         * <p>The variant is a 4-character code that indicates what type of image it is.
         * The common variants for images are MAIN, PT01, PT02, etc. up to PT99.
         * Footwear uses FRNT, BACK, TOPP, BOTT, RGHT, LEFT.</p>
         *
         * The detail page primary image is <b>not</b> necessarily the "MAIN" variant.
         * You should use the first image in the sorted list as the primary display image of the product.
         *
         * The variant is a 4-character code that indicates what type of image it is, i.e. MAIN
         *
         * @return String with the variant.
         */
        String variant();
    }
}
