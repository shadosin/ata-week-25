package com.kenzie.groupwork.productpage.types;

import java.util.Objects;
import java.util.Optional;

public class StyledMedia implements MediaType {
    private String physicalId;
    private String extension;
    private String styleCode;
    private Integer height;
    private Integer width;
    private String url;

    private StyledMedia(String url, Integer width, Integer height) {
        this.url = url;
        this.width = width;
        this.height = height;
    }

    @Override
    public String physicalId() {
        return physicalId;
    }

    @Override
    public String extension() {
        return extension;
    }

    @Override
    public String styleCode() {
        return styleCode;
    }

    @Override
    public Integer height() {
        return height;
    }

    @Override
    public Integer width() {
        return width;
    }

    public String url() {
        return url;
    }

    public Builder styleBuilder() {
        return new Builder(this.width, this.height);
    }

    public static final class Builder {
        private final StringBuilder rendering;
        private String cdn;
        private String extension;
        private String s3Bucket;
        private String physicalId;
        private String name;
        private String orgUnit;
        private String cacheBypassTag;
        private int width;
        private int height;

        public Builder(int width, int height) {
            rendering = new StringBuilder();
            this.width = width;
            this.height = height;
        }

        /**
         * Generates the URL for this Physical Id, including all preceding image manipulations.
         * Only the last method of a given rendering is used to generate the url
         * For example, <code>media.scaleToRectangle(50,50).scaleToWidth(1000).scaleToHeight(100)</code> will only use
         * the results of <code>scaleToHeight(100)</code>. The same goes for repeated commands such as
         * <code>media.crop(1,1,1,1).crop(5,5,5,5)</code>
         * <p>
         * Has the form:
         * https://CDN/images/I/PHYSICAL_ID.STYLE.EXTENSION
         *
         * @return Creates the StyledMedia with the provided configuration.
         */
        public StyledMedia build() {
            if (width <= 0 || height <= 0) {
                return new StyledMedia(null, width, height);
            }

            StringBuilder url = new StringBuilder(128)
                .append("https://")
                .append(deriveCdn());

            if (s3Bucket != null) {
                url.append("/images/S/")
                    .append(s3Bucket)
                    .append("/")
                    .append(physicalId);
                // TODO: use physicalId, or introduce a new field (name, resourcePath, s3object, s3Path, etc ?)
                if (rendering.length() > 0) {
                    url.append('.')
                        .append(rendering);
                }
            } else if (name == null) {
                url.append("/images/I/")
                    .append(physicalId);
                if (rendering.length() > 0) {
                    url.append('.')
                        .append(rendering);
                }
            } else {
                url.append("/images/G/")
                    .append(orgUnit)
                    .append('/')
                    .append(name)
                    .append("._CB")
                    .append(cacheBypassTag)
                    .append(rendering);
            }

            if (cacheBypassTag != null || rendering.length() > 0) {
                url.append('_');
            }

            url.append('.')
                .append(extension);

            return new StyledMedia(url.toString(), width, height);
        }

        /**
         * Sets the CDN hostname to use to generate this media objects URL.
         *
         * @param cdnHost The CDN host that has the image.
         * @return A Builder with the CDN host set.
         */
        public Builder cdn(String cdnHost) {
            this.cdn = cdnHost;
            return this;
        }

        /**
         * If you want to build a path-based URL like
         * https://g-ecx.images-amazon.com/images/G/01/kindle/merch/2017/919357422668451/VX-1020-ML_GW-Takeover-660px-1X_V3._CB515800720_.jpg
         * you can provide the <code>name</code>, the <code>orgUnit</code> and the <code>cacheBypassTag</code>
         * of the image.
         *
         * @param imgName name of the image.
         * @param org organization of the image.
         * @param bypassCacheTag tag for bypassing the cache.
         * @return A Builder that creates the StyledMedia.
         */
        public Builder withNameAndOU(String imgName, String org, String bypassCacheTag) {
            Objects.requireNonNull(imgName, "imgName is required");
            Objects.requireNonNull(org, "org is required");
            Objects.requireNonNull(bypassCacheTag, "cacheBypassTag is required");
            this.name = imgName.startsWith("/") ? imgName.substring(1) : imgName;
            this.orgUnit = org;
            this.cacheBypassTag = bypassCacheTag;
            return this;
        }

        /**
         * Crop this image.
         *
         * @param x      part of the x,y pair for the top left corner of the cropped area in pixels (0 is the top left)
         * @param y      part of the x,y pair for the top left corner of the cropped area in pixels (0 is the top left)
         * @param scaledWidth  width of the cropped area
         * @param scaledHeight height of the cropped area
         * @return a reference to this object modified to be cropped
         */
        public Builder crop(final int x, final int y, final int scaledWidth, final int scaledHeight) {
            if (x < 0 || y < 0) {
                throw new IllegalArgumentException("x,y coordinate of the top left corner must be >= 0");
            }
            if (scaledWidth <= 0 || scaledHeight <= 0) {
                throw new IllegalArgumentException("Width and height of cropped area must be > 0");
            }
            this.width = Math.min(scaledWidth, this.width - x);
            this.height = Math.min(scaledHeight, this.height - y);
            rendering.append("_CR")
                .append(x)
                .append(",")
                .append(y)
                .append(",")
                .append(scaledWidth)
                .append(",")
                .append(scaledHeight);
            return this;
        }

        /**
         * Removes as much white background as possible so the product will appear larger in size.
         *
         * It is important to note that {@link #autoCrop()} should be called before any other image altering methods.
         * If not ordered properly, your image may not render as expected,
         * such as cropping off whitespace that was added to make an image square.
         *
         * @return a reference to this object modified to remove extra white space
         */
        public Builder autoCrop() {
            rendering.append("_AC");
            return this;
        }

        /**
         * Apply a blurring algorithm to the image to make it less crisp.
         * <p>
         * Percent must be between 1 and 99, inclusive.
         * If the percent is out of this range, throws {@link IllegalArgumentException}.
         *
         * @param percent between 1 and 99, inclusive
         * @return a reference to this object modified to be blurred
         */
        public Builder blur(final int percent) {
            if (percent <= 0 || percent >= 100) {
                throw new IllegalArgumentException("Blur percent must be in the range [1-100]");
            } else {
                rendering.append("_BL")
                    .append(percent);
            }
            return this;
        }

        /**
         * Scale this image so that it is exactly <code>width</code> pixels by <code>height</code> pixels.
         * The aspect ratio of the original image is preserved,
         * so if the original image is not the aspect ratio of <code>width</code> to <code>height</code>,
         * whitespace will be added to pad the image.
         * <p>
         * Width and Height must be a positive number, or throws {@link IllegalArgumentException}.
         * If the target size is bigger than original, image will be padded with a border
         *
         * @param scaledWidth  size in pixels for width of the scaled image
         * @param scaledHeight size in pixels for height of the scaled image
         * @return a reference to this object modified to be scaled
         */
        public Builder scaleToRectangle(final int scaledWidth, final int scaledHeight) {
            if (scaledWidth <= 0) {
                throw new IllegalArgumentException("Width must be a positive number.");
            }
            if (scaledHeight <= 0) {
                throw new IllegalArgumentException("Height must be a positive number.");
            }
            this.width = scaledWidth;
            this.height = scaledHeight;
            rendering.append("_SR")
                .append(scaledWidth)
                .append(",")
                .append(scaledHeight);
            return this;
        }

        /**
         * Scale this image so that it is exactly <code>side</code> pixels by <code>side</code> pixels.
         * The aspect ratio of the original image is preserved,
         * so if the original image is not the aspect ratio of <code>side</code> to <code>side</code>,
         * whitespace will be added to pad the image.
         * <p>
         * Side must be a positive number, or throws {@link IllegalArgumentException}.
         * If the target size is bigger than original, image will be padded with a border
         *
         * @param side  size in pixels for width of the scaled image
         * @return a reference to this object modified to be scaled
         */
        public Builder scaleToSquare(final int side) {
            if (side <= 0) {
                throw new IllegalArgumentException("Side must be a positive number.");
            }
            this.width = side;
            this.height = side;
            rendering.append("_SS")
                .append(side);
            return this;
        }

        /**
         * Scale this image so that it is exactly <code>width</code> pixels in width.
         * The aspect ratio of the original image is preserved, and the height will be scaled accordingly.
         * It doesn't change the dimensions if the new width is larger than the old one.
         * <p>
         * Width must be a positive number, or throws {@link IllegalArgumentException}
         *
         * @param scaledWidth size in pixels for the width of the scaled image
         * @return a reference to this object modified to be scaled
         */
        public Builder scaleToWidth(final int scaledWidth) {
            if (scaledWidth <= 0) {
                throw new IllegalArgumentException("Cannot scale image to 0 pixels or less, got: " + scaledWidth);
            }
            if (this.width > scaledWidth) {
                this.height = scaledWidth * this.height / this.width;
                this.width = scaledWidth;
            }
            rendering.append("_SX")
                .append(scaledWidth);
            return this;
        }

        /**
         * Scale this image so that it is exactly <code>height</code> pixels in height.
         * The aspect ratio of the original image is preserved, and the width will be scaled accordingly.
         * It doesn't change the dimensions if the new height is larger than the old one.
         * <p>
         * Height must be a positive number, or throws {@link IllegalArgumentException}
         *
         * @param scaledHeight size in pixels for the height of the scaled image
         * @return a reference to this object modified to be scaled
         */
        public Builder scaleToHeight(final int scaledHeight) {
            if (scaledHeight <= 0) {
                throw new IllegalArgumentException("Cannot scale image to 0 pixels or less, got: " + scaledHeight);
            }
            if (this.height > scaledHeight) {
                this.width = scaledHeight * this.width / this.height;
                this.height = scaledHeight;
            }
            rendering.append("_SY")
                .append(scaledHeight);
            return this;
        }

        /**
         * Scale this image so that it is exactly <code>side</code> pixels on the longest side. The aspect ratio of the
         * original image is preserved, and the shortest side will be scaled accordingly. It doesn't enlarge the image.
         *
         * Side must be a positive number, or throws {@link IllegalArgumentException}
         *
         * @param side size in pixels for the longest side of the scaled image
         * @return a reference to this object modified to be scaled
         */
        public Builder scaleToLongest(final int side) {
            if (side <= 0) {
                throw new IllegalArgumentException("Cannot scale image to 0 pixels or less, got: " + side);
            }
            if (width > height) {
                if (side < width) {
                    height = side * height / width;
                    width = side;
                }
            } else if (side < height) {
                width = side * width / height;
                height = side;
            }
            rendering.append("_SL")
                .append(side);
            return this;
        }

        /**
         * Upscales the image if target height is bigger than original height
         * Scales this image so that it is exactly <code>side</code> pixels in height. The aspect ratio of the original
         * image is preserved, and the width will be scaled accordingly.
         *
         * Height must be a positive number, or throws {@link IllegalArgumentException}
         *
         * @param scaledHeight size in pixels for the height of the scaled image
         * @return a reference to this object modified to be scaled
         */
        public Builder upscaleToHeight(final int scaledHeight) {
            if (scaledHeight <= 0) {
                throw new IllegalArgumentException("Cannot scale image to 0 pixels or less, got: " + scaledHeight);
            }
            this.width = this.height > 0 ? scaledHeight * this.width / this.height : 0;
            this.height = scaledHeight;
            rendering.append("_UY")
                .append(scaledHeight);
            return this;
        }

        /**
         * Upscales the image if target longest side is bigger than original longest side
         *
         * Scale this image so that it is exactly <code>side</code> pixels on the longest side. The aspect ratio of the
         * original image is preserved, and the shortest side will be scaled accordingly.
         *
         * Side must be a positive number, or throws {@link IllegalArgumentException}
         *
         * @param side size in pixels for the longest side of the scaled image
         * @return a reference to this object modified to be scaled
         */
        public Builder upscaleToLongest(final int side) {
            if (side <= 0) {
                throw new IllegalArgumentException("Cannot scale image to 0 pixels or less, got: " + side);
            }
            if (width > height) {
                height = side * height / width;
                width = side;
            } else {
                width = height > 0 ? side * width / height : 0;
                height = side;
            }
            rendering.append("_UL")
                .append(side);
            return this;
        }

        /**
         * Upscales the image if target width is bigger than original width
         *
         * Scale this image so that it is exactly <code>side</code> pixels in width.
         * The aspect ratio of the original image is preserved, and the height will be scaled accordingly.
         *
         * Width must be a positive number, or throws {@link IllegalArgumentException}
         *
         *
         * @param scaledWidth size in pixels for the width of the scaled image
         * @return a reference to this object modified to be scaled
         */
        public Builder upscaleToWidth(final int scaledWidth) {
            if (scaledWidth <= 0) {
                throw new IllegalArgumentException("Cannot scale image to 0 pixels or less, got: " + scaledWidth);
            }
            this.height = this.width > 0 ? scaledWidth * this.height / this.width : 0;
            this.width = scaledWidth;
            rendering.append("_UX")
                .append(scaledWidth);
            return this;
        }

        /**
         * Adds custom rendering to this media object.
         *
         * Check out https://w.amazon.com/index.php/MSA/HowTo/ImageStyleCodes for other rendering codes
         *
         * @param custom the custom styling to apply
         * @return a reference to this object modified with the custom styling
         */
        public Builder customStyle(String custom) {
            rendering.append(custom);
            return this;
        }

        private String deriveCdn() {
            return Optional.ofNullable(cdn).orElse("PROD");
        }
    }
}
