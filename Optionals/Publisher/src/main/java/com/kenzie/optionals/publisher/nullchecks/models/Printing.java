package com.kenzie.optionals.publisher.nullchecks.models;

import java.util.Date;

/*

<-----------------------            DO NOT MODIFY THIS CLASS!            -------------------------------->

This class is the model to show how the original methods work.

The class to modify is in the "optionals" folder below, not this folder (nullchecks)

*/

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

    public Publisher getPublisher() {
        return publisher;
    }

    public Date getPrintDate() {
        return new Date(printDate.getTime());
    }
}
