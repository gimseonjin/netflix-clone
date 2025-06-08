package com.kerry.netflix.config

import com.kerry.netflix.movie.application.inp.dto.PageableMovies
import com.kerry.netflix.movie.domain.Movie
import com.kerry.netflix.movie.domain.MovieClient
import com.kerry.netflix.movie.domain.MovieRepository
import com.kerry.netflix.reader.HttpPageableMoviesReader
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemWriter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class ImportMoviesFromTmdb(
    private val movieRepository: MovieRepository,
    private val movieClient: MovieClient
) {

    private val BATCH_JOB_NAME = "importMoviesFromTmdb"
    private val BATCH_STEP_NAME = "importMoviesFromTmdbStep"

    @Bean
    fun importMvoiesFromTmdbJob(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager,
        importMoviesFromTmdbStep: Step
    ): Job = JobBuilder(BATCH_JOB_NAME, jobRepository)
        .incrementer(RunIdIncrementer())
        .start(importMoviesFromTmdbStep)
        .build()

    @Bean
    fun importMoviesFromTmdbStep(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager,
    ): Step = StepBuilder(BATCH_STEP_NAME, jobRepository)
        .chunk<PageableMovies, PageableMovies>(1, transactionManager)
        .reader(httpPageableMoviesReader())
        .writer(pageableMoviesWriter())
        .build()

    @Bean
    fun httpPageableMoviesReader(): HttpPageableMoviesReader = HttpPageableMoviesReader(movieClient)

    @Bean
    fun pageableMoviesWriter(): ItemWriter<PageableMovies> = ItemWriter { pages ->
        pages.forEach { page ->
            movieRepository.saveAll(page.movies)
        }
    }
}