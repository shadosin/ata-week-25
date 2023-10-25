package com.kenzie.streams.filevalidator.resources;

import java.util.Optional;

public class Car {
    private final Insurance insurance;

    public Car(Insurance insurance) {
        this.insurance = insurance;
    }

    public Optional<Insurance> getInsurance() {
        return Optional.ofNullable(insurance);
    }

}
