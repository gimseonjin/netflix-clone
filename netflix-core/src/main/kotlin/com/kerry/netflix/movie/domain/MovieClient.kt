package com.kerry.netflix.movie.domain

import com.kerry.netflix.movie.application.inp.dto.PageableMovies

interface MovieClient {

    fun fetchPageable(page: Int): PageableMovies
}