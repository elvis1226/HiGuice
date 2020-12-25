package org.dgf;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {

    private final KIND kind;
    private final double price;
    private final UNIT unit;

    @JsonCreator
    public Product(
            @JsonProperty ("kind") KIND kind,
            @JsonProperty ("price") double price,
            @JsonProperty ("unit") UNIT unit) {
        this.price = price;
        this.kind = kind;
        this.unit = unit;
    }

    public KIND getKind() {
        return kind;
    }

    public double getPrice() {
        return price;
    }

    public UNIT getUnit() {
        return unit;
    }
}
