package com.kerry.netflix.movie

import com.kerry.netflix.ApiResponse
import com.kerry.netflix.movie.application.inp.ReadMovies
import com.kerry.netflix.movie.application.inp.dto.PageableMovies
import jakarta.validation.constraints.Min
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v1/movie")
class MovieController(
    private val readMovies: ReadMovies
) {

    @PostMapping("/search")
    fun search(@RequestParam @Min(1) page: Int): ApiResponse<PageableMovies> {
        val fetch: PageableMovies = readMovies.readFromClient(page)
        return ApiResponse.ok(fetch)
    }
}