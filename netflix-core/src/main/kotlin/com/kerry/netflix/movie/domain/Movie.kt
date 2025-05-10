package com.kerry.netflix.movie.domain

import jakarta.persistence.*

@Entity
@Table(name = "movies")
class Movie(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var movieId: Long? = null,

    @Column(name = "MOVIE_NAME")
    var movieName: String,

    @Column(name = "IS_ADULT")
    var isAdult: Boolean,

    @Column(name = "GENRE")
    var genre: String,

    @Column(name = "OVERVIEW", columnDefinition = "TEXT")
    var overview: String,

    @Column(name = "RELEASED_AT")
    var releasedAt: String
)