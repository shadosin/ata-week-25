package com.kenzie.optionals.publisher.optionals.models;

import java.util.Date;
import java.util.Optional;

/**
 * Represents a printing of a Book. Might be paperback, hardcover, ebook,
 * audio... who knows what the future will hold?
 */
public class Printing {
    private final PrintingType printingType;
    private final Publisher publisher;
    private final Date printDate;

    public Printing(PrintingType printingType, Publisher publisher, Date printDate) {
        this.printingType = printingType;
        this.publisher = publisher;
        this.printDate = new Date(printDate.getTime());
    }

    public PrintingType getPrintingType() {
        return printingType;
    }

    public Optional<Publisher> getPublisher() {
        return Optional.ofNullable(publisher);
    }

    public Date getPrintDate() {
        return new Date(printDate.getTime());
    }
}
