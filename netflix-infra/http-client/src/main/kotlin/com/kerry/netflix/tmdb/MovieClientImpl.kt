package com.kerry.netflix.tmdb

import com.kerry.netflix.movie.domain.MovieClient
import com.kerry.netflix.movie.application.inp.dto.PageableMovies
import com.kerry.netflix.util.toObject
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component

@Component
class MovieClientImpl(
    private val tmdbHttpClient: TmdbHttpClient,

    @Value("\${tmdb.api.movie-lists.now-playing}")
    private val nowPlayingUri: String,
): MovieClient {

    override fun fetchPageable(page: Int): PageableMovies {

        val url = "$nowPlayingUri?language=ko-KR&page=$page"
        val json = tmdbHttpClient.request(url, HttpMethod.GET)
        val tmdbResponse: TmdbResponse = json.toObject<TmdbResponse>()

        val movies = tmdbResponse.results
            .map(TmdbMovieNowPlaying::toDomain)

        val currentPage = tmdbResponse.page.toInt()
        val totalPages  = tmdbResponse.total_pages.toInt()
        val hasNext     = currentPage < totalPages

        return PageableMovies(
            movies      = movies,
            page        = currentPage,
            hasNext     = hasNext
        )
    }
}