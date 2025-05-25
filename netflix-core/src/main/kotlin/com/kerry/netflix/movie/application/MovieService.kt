package com.kerry.netflix.movie.application

import com.kerry.netflix.movie.application.inp.ReadMovies
import com.kerry.netflix.movie.domain.MovieClient
import com.kerry.netflix.movie.domain.MovieRepository
import com.kerry.netflix.movie.application.inp.dto.PageableMovies
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class MovieService(
    private val movieClient: MovieClient,
    private val movieRepository: MovieRepository
): ReadMovies {
    private val PAGE_SIZE = 10

    override fun readFromClient(page: Int): PageableMovies {
        return movieClient.fetchPageable(page)
    }

    override fun readFromDb(page: Int): PageableMovies {
        val pageable: Pageable = Pageable.ofSize(PAGE_SIZE).withPage(page)
        val moviePage = movieRepository.findAllBy(pageable)
        val hasNextPage = moviePage.hasNext()

        return PageableMovies(
            movies = moviePage.content,
            page = page,
            hasNext = hasNextPage
        )
    }
}