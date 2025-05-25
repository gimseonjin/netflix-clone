package com.kerry.netflix.user.domain

import com.kerry.netflix.base.BaseDomain
import jakarta.persistence.*

@Entity
@Table(name = "user_movie_downloads")
class UserMovieDownload(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "MOVIE_ID")
    var movieId: Long
): BaseDomain(){

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", nullable = false)
    lateinit var user: User
}