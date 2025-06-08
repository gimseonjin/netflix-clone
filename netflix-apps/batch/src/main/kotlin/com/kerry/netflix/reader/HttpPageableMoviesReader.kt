package com.kerry.netflix.reader

import com.kerry.netflix.movie.application.inp.dto.PageableMovies
import com.kerry.netflix.movie.domain.MovieClient
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader

class HttpPageableMoviesReader(
    private val movieClient: MovieClient
): AbstractItemCountingItemStreamItemReader<PageableMovies>() {

    private val READER_NAME = "HttpPageableMoviesReader"

    init {
        super.setName(READER_NAME)
    }

    private var currentPage = 1
    private var hasNext = true

    override fun doRead(): PageableMovies? {
        if (!hasNext) return null

        val pageableMovies = movieClient.fetchPageable(currentPage)
        hasNext = pageableMovies.hasNext
        currentPage++

        return pageableMovies
    }

    override fun doOpen() {
        currentPage = 1
        hasNext = true
    }

    override fun doClose() {
        // No specific close actions needed
    }
}