package com.example.demo;

public enum ParentalGuidance {
    G(1),
    PG(2),
    PG13(3),
    R(4),
    MA(5);

    private final int rating;

    ParentalGuidance(int rating) {
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }
}
