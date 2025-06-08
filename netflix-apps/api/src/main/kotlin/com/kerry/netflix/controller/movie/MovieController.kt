package com.kerry.netflix.controller.movie

import com.kerry.netflix.ApiResponse
import com.kerry.netflix.movie.application.inp.ReadMovies
import com.kerry.netflix.movie.application.inp.dto.PageableMovies
import jakarta.validation.constraints.Min
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
class MovieController(
    private val readMovies: ReadMovies
) {

    @GetMapping("/api/v1/movies")
    fun search(@RequestParam @Min(1) page: Int): ApiResponse<PageableMovies> {
        val fetch: PageableMovies = readMovies.readFromDb(page)
        return ApiResponse.ok(fetch)
    }
}