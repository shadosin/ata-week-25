package com.kenzie.groupwork.productpage.types;

/**
 * Media DSL type.
 *
 * Will be converted to an ASTMediaType.
 * Represents a media object (image + video). Has a physicalId, extension, height, and width
 * @author ryice
 */
public interface MediaType extends Type {
    String physicalId();

    String extension();

    String styleCode();

    Integer height();

    Integer width();

}
