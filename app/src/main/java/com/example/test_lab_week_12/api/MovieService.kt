package com.example.test_lab_week_12.api

// Import ini WAJIB ada agar @GET, @Query, dan PopularMoviesResponse dikenali
import com.example.test_lab_week_12.model.PopularMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("movie/popular")
    // Di sini kita menggunakan kata kunci 'suspend' untuk menandakan bahwa fungsi ini adalah coroutine.
    // Fungsi 'suspend' dapat dijeda dan dilanjutkan di lain waktu.
    // Ini berguna untuk panggilan jaringan (network calls) karena bisa memakan waktu lama,
    // dan kita tidak ingin memblokir main thread (agar aplikasi tidak macet).
    // Info selengkapnya: https://kotlinlang.org/docs/flow.html#suspending-functions
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String
    ): PopularMoviesResponse
}