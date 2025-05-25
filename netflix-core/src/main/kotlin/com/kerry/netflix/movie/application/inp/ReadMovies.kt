package com.kerry.netflix.movie.application.inp

import com.kerry.netflix.movie.application.inp.dto.PageableMovies

interface ReadMovies {

    fun readFromClient(page: Int): PageableMovies
    fun readFromDb(page: Int): PageableMovies
}