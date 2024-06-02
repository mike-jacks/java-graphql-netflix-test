package com.example.demo;

import java.util.UUID;

public record Show(UUID id, String title, int releaseYear, int rating, ParentalGuidance maturityRating) {
}
