package com.kerry.netflix.tmdb

import com.fasterxml.jackson.annotation.JsonProperty
import com.kerry.netflix.movie.domain.Movie
import com.kerry.netflix.movie.domain.MovieGenre

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
    val backdropPath: String? = "NONE",

    @JsonProperty("genre_ids")
    val genreIds: List<String>,

    val id: Int,

    @JsonProperty("original_language")
    val originalLanguage: String? = "en",

    @JsonProperty("original_title")
    val originalTitle: String? = "",

    val overview: String,

    val popularity: String? = "0.0",

    @JsonProperty("poster_path")
    val posterPath: String? = "NONE",

    @JsonProperty("release_date")
    val releaseDate: String,

    val title: String,

    val video: String? = "false",

    @JsonProperty("vote_average")
    val voteAverage: String? = "0.0",

    @JsonProperty("vote_count")
    val voteCount: String? = "0",
) {
    fun toDomain(): Movie {
        return Movie(
            title = title,
            isAdult = adult,
            overview = overview,
            releasedAt = releaseDate,
            genre = if(genreIds.isEmpty()) MovieGenre.UNKNOWN else MovieGenreMapper.fromId(genreIds.first().toInt()),
        )
    }
}

data class TmdbDateResponse (
    val maximum: String,
    val minimum: String,
)
