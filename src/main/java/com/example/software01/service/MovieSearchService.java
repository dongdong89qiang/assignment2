package com.example.software01.service;

import com.example.software01.dto.movies.MovieDto;
import com.example.software01.dto.movies.MoviesBatchResult;
import com.example.software01.dto.movies.MoviesSearchResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Service
public class MovieSearchService {

    private final RestTemplate restTemplate;
    private final ExecutorService movieFetchExecutor;
    private final String baseUrl;

    public MovieSearchService(
            RestTemplate restTemplate,
            @Qualifier("movieFetchExecutor") ExecutorService movieFetchExecutor,
            @Value("${movies.api.base-url:https://jsonmock.hackerrank.com/api/moviesdata/search/}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.movieFetchExecutor = movieFetchExecutor;
        this.baseUrl = baseUrl;
    }

    /**
     * Fetches all pages from the remote API without Title/Year filters (only {@code page} is used).
     */
    public MoviesBatchResult fetchAllPagesWithoutQueryParams() {
        MoviesSearchResponse first = restTemplate.getForObject(
                buildSearchUri(1, null, null), MoviesSearchResponse.class);
        return mergeAllPages(first, null, null);
    }

    /**
     * When {@code page} is set, returns that single page from the API.
     * Otherwise fetches every page in parallel, optionally filtered by movie name ({@code Title}) and {@code year}.
     */
    public MoviesBatchResult fetchWithQueryParams(Integer page, String movieName, Integer year) {
        if (page != null) {
            URI uri = buildSearchUri(page, movieName, year);
            MoviesSearchResponse response = Objects.requireNonNull(
                    restTemplate.getForObject(uri, MoviesSearchResponse.class));
            List<MovieDto> data = response.getData() != null ? response.getData() : List.of();
            return new MoviesBatchResult(data, response.getTotal(), response.getTotalPages());
        }
        MoviesSearchResponse first = restTemplate.getForObject(
                buildSearchUri(1, movieName, year), MoviesSearchResponse.class);
        return mergeAllPages(first, movieName, year);
    }

    private MoviesBatchResult mergeAllPages(
            MoviesSearchResponse firstPage, String movieName, Integer year) {
        if (firstPage == null) {
            return new MoviesBatchResult(List.of(), 0, 0);
        }
        int totalPages = firstPage.getTotalPages() != null ? firstPage.getTotalPages() : 1;
        List<MovieDto> merged = new ArrayList<>();
        if (firstPage.getData() != null) {
            merged.addAll(firstPage.getData());
        }
        if (totalPages <= 1) {
            return new MoviesBatchResult(merged, firstPage.getTotal(), firstPage.getTotalPages());
        }

        List<CompletableFuture<MoviesSearchResponse>> futures = new ArrayList<>();
        for (int p = 2; p <= totalPages; p++) {
            final int pageNum = p;
            futures.add(CompletableFuture.supplyAsync(
                    () -> restTemplate.getForObject(
                            buildSearchUri(pageNum, movieName, year), MoviesSearchResponse.class),
                    movieFetchExecutor));
        }
        CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new)).join();

        for (CompletableFuture<MoviesSearchResponse> future : futures) {
            MoviesSearchResponse chunk = future.join();
            if (chunk != null && chunk.getData() != null) {
                merged.addAll(chunk.getData());
            }
        }
        return new MoviesBatchResult(merged, firstPage.getTotal(), firstPage.getTotalPages());
    }

    private URI buildSearchUri(int page, String movieName, Integer year) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl.trim())
                .queryParam("page", page);
        if (StringUtils.hasText(movieName)) {
            builder.queryParam("Title", movieName);
        }
        if (year != null) {
            builder.queryParam("Year", year);
        }
        return builder.build().encode().toUri();
    }
}
