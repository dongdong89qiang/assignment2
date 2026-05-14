package com.example.software01.dto.movies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoviesBatchResult {

    private List<MovieDto> data;

    private Integer total;

    private Integer totalPages;
}
