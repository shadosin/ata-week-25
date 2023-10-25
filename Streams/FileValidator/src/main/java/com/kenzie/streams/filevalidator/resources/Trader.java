package com.kenzie.streams.filevalidator.resources;

import java.util.Objects;

public class Trader {
    private final String name;
    private final String city;
    public Trader(String n, String c) {
        this.name = n;
        this.city = c;
    }
    public String getName() {
        return this.name;
    }
    public String getCity() {
        return this.city;
    }
    public String toString() {
        return "Trader:" + this.name + " in " + this.city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Trader)) {
            return false;
        }
        Trader trader = (Trader) o;
        return Objects.equals(getName(), trader.getName()) &&
            Objects.equals(getCity(), trader.getCity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getCity());
    }
}
