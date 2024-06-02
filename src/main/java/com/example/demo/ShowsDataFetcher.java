package com.example.demo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.UUID;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;

@DgsComponent
public class ShowsDataFetcher {

    private final List<Show> shows = List.of(
            new Show(UUID.randomUUID(), "Stranger Things", 2016, 5, ParentalGuidance.PG13),
            new Show(UUID.randomUUID(), "Ozark", 2017, 4, ParentalGuidance.PG13),
            new Show(UUID.randomUUID(),"The Crown", 2016, 2, ParentalGuidance.PG13),
            new Show(UUID.randomUUID(),"Dead to Me", 2019,3, ParentalGuidance.R),
            new Show(UUID.randomUUID(),"Orange is the New Black", 2013, 5, ParentalGuidance.MA)
    );

    @DgsQuery
    public List<Show> shows(@InputArgument String title, @InputArgument Integer lowestRating, @InputArgument Integer highestRating, @InputArgument ParentalGuidance maxMaturityRating) {
        return shows.stream()
                .filter(s -> title == null || s.title().contains(title))
                .filter(s -> lowestRating == null || s.rating() > lowestRating)
                .filter(s -> highestRating == null || s.rating() < highestRating)
                .filter(s -> maxMaturityRating == null || s.maturityRating().getRating() <= maxMaturityRating.getRating())
                .collect(Collectors.toList());
    }

    @DgsQuery
    public Show showById(@InputArgument UUID id) {
        return shows
                .stream().filter(s -> s.id().equals(id))
                .findFirst()
                .orElse(null);
    }

    @DgsQuery
    public List<Show> allShows(@InputArgument Integer page, @InputArgument Integer limit) {
       if (page == null || limit == null) {
           return shows;
       }
       int start = Math.min(page * limit, shows.size());
       int end = Math.min(start + limit, shows.size());
       return shows.subList(start, end);
    }

    @DgsQuery
    public List<Show> showsByYear(@InputArgument Integer releaseYear) {
        return shows.stream()
                .filter(s -> s.releaseYear() == releaseYear)
                .collect(Collectors.toList());
    }

    @DgsQuery
    public List<Show> showsByRating(@InputArgument Integer minRating, @InputArgument Integer maxRating) {
        return shows.stream()
                .filter(s -> (minRating == null || s.rating() >= minRating) && (maxRating == null || s.rating() <= maxRating))
                .collect(Collectors.toList());
    }

    @DgsQuery
    public List<Show> showsByMaturityRating(@InputArgument ParentalGuidance maturityRating) {
        return shows.stream()
                .filter(s -> s.maturityRating() == maturityRating)
                .collect(Collectors.toList());
    }

}

