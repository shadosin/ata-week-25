package com.kenzie.streams.listprocessing.resources;

import java.util.Objects;

public class Transaction {
    private final Trader trader;
    private final int year;
    private final int value;

    public Transaction(Trader trader, int year, int value) {
        this.trader = trader;
        this.year = year;
        this.value = value;
    }

    public Trader getTrader() {
        return this.trader;
    }

    public int getYear() {
        return this.year;
    }

    public int getValue() {
        return this.value;
    }

    public String toString() {
        return "{" + this.trader + ", " +
            "year: " + this.year + ", " +
            "value:" + this.value + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transaction)) {
            return false;
        }
        Transaction that = (Transaction) o;
        return getYear() == that.getYear() &&
            getValue() == that.getValue() &&
            Objects.equals(getTrader(), that.getTrader());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTrader(), getYear(), getValue());
    }
}

