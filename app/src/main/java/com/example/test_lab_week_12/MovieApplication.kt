package com.example.test_lab_week_12 // Pastikan package ini sesuai dengan proyek Anda

import android.app.Application
import com.example.test_lab_week_12.api.MovieService // Import Interface MovieService
import retrofit2.Retrofit // Import library Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory // Import konverter Moshi

class MovieApplication : Application() {

    lateinit var movieRepository: MovieRepository

    override fun onCreate() {
        super.onCreate()

        // Membuat instance Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        // Membuat instance MovieService
        // dan menghubungkan interface MovieService ke instance Retrofit
        // ini memungkinkan kita untuk melakukan panggilan API
        val movieService = retrofit.create(
            MovieService::class.java
        )

        // Membuat instance MovieRepository
        movieRepository = MovieRepository(movieService)
    }
}