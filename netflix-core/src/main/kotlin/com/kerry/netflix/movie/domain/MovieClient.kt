package com.kerry.netflix.movie.domain

interface MovieClient {

    fun fetchPageable(page: Int): PageableMovies
}