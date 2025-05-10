package com.kerry.netflix.tmdb

import com.kerry.netflix.movie.domain.MovieGenre

object MovieGenreMapper {
    private val idToGenre = mapOf(
        0    to MovieGenre.UNKNOWN,
        28   to MovieGenre.ACTION,
        12   to MovieGenre.ADVENTURE,
        16   to MovieGenre.ANIMATION,
        35   to MovieGenre.COMEDY,
        80   to MovieGenre.CRIME,
        99   to MovieGenre.DOCUMENTARY,
        18   to MovieGenre.DRAMA,
        10751 to MovieGenre.FAMILY,
        14   to MovieGenre.FANTASY,
        36   to MovieGenre.HISTORY,
        27   to MovieGenre.HORROR,
        10402 to MovieGenre.MUSIC,
        9648 to MovieGenre.MYSTERY,
        10749 to MovieGenre.ROMANCE,
        878  to MovieGenre.SCIENCE_FICTION,
        10770 to MovieGenre.TV_MOVIE,
        53   to MovieGenre.THRILLER,
        10752 to MovieGenre.WAR,
        37   to MovieGenre.WESTERN
    )

    fun fromId(id: Int): MovieGenre =
        idToGenre[id] ?: MovieGenre.UNKNOWN
}