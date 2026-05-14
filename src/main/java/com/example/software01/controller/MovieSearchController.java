package com.example.software01.controller;

import com.example.software01.dto.movies.MoviesBatchResult;
import com.example.software01.service.MovieSearchService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movies")
@PreAuthorize("hasRole('USER')")
public class MovieSearchController {

    private final MovieSearchService movieSearchService;

    public MovieSearchController(MovieSearchService movieSearchService) {
        this.movieSearchService = movieSearchService;
    }

    /**
     * All pages, no remote filters (parallel fetch after first page).
     */
    @GetMapping("/all")
    public MoviesBatchResult allPagesNoQuery() {
        return movieSearchService.fetchAllPagesWithoutQueryParams();
    }

    /**
     * Optional {@code page} (single page), {@code movieName} (maps to {@code Title}), {@code year}.
     * If {@code page} is omitted, all matching pages are fetched in parallel.
     */
    @GetMapping("/search")
    public MoviesBatchResult search(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) String movieName,
            @RequestParam(required = false) Integer year) {
        return movieSearchService.fetchWithQueryParams(page, movieName, year);
    }
}
