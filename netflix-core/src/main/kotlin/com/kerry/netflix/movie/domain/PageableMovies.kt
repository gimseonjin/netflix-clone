package com.kerry.netflix.movie.domain

class PageableMovies(
    val movies: List<Movie>,
    val page: Int,
    val hasNext: Boolean
) {
}