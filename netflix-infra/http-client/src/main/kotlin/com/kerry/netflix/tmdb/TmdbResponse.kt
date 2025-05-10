package com.kerry.netflix.tmdb

import com.fasterxml.jackson.annotation.JsonProperty
import com.kerry.netflix.movie.domain.Movie

data class TmdbResponse (
    val dates: TmdbDateResponse,
    val page: String,
    val total_pages: String,
    val total_results: String,
    val results: List<TmdbMovieNowPlaying>,
)

data class TmdbMovieNowPlaying (
    val adult: Boolean,

    @JsonProperty("backdrop_path")
    val backdropPath: String,

    @JsonProperty("genre_ids")
    val genreIds: List<String>,

    val id: Int,

    @JsonProperty("original_language")
    val originalLanguage: String,

    @JsonProperty("original_title")
    val originalTitle: String,

    val overview: String,

    val popularity: String,

    @JsonProperty("poster_path")
    val posterPath: String,

    @JsonProperty("release_date")
    val releaseDate: String,

    val title: String,

    val video: String,

    @JsonProperty("vote_average")
    val voteAverage: String,

    @JsonProperty("vote_count")
    val voteCount: String,
) {
    fun toDomain(): Movie {
        return Movie(
            title = title,
            isAdult = adult,
            overview = overview,
            releasedAt = releaseDate,
            genre = MovieGenreMapper.fromId(genreIds.first().toInt()),
        )
    }
}

data class TmdbDateResponse (
    val maximum: String,
    val minimum: String,
)
