package com.kerry.netflix.movie.application.inp.dto

import com.kerry.netflix.movie.domain.Movie

class PageableMovies(
    val movies: List<Movie>,
    val page: Int,
    val hasNext: Boolean
) {
}