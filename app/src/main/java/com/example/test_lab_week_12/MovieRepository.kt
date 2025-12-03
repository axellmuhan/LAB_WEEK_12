package com.example.test_lab_week_12 // Pastikan package ini sesuai dengan proyek Anda

// 1. IMPORT PENTING YANG HILANG
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.test_lab_week_12.api.MovieService
import com.example.test_lab_week_12.model.Movie // Import data class Movie Anda

class MovieRepository(private val movieService: MovieService) {
    // Masukkan API Key Anda di sini
    private val apiKey = "a9bb3a0a8cd884a600d77b5a5f171c7a"

    // LiveData that contains a list of movies
    private val movieLiveData = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = movieLiveData

    // LiveData that contains an error message
    private val errorLiveData = MutableLiveData<String>()
    val error: LiveData<String>
        get() = errorLiveData

    // fetch movies from the API
    suspend fun fetchMovies() {
        try {
            // get the list of popular movies from the API
            val popularMovies = movieService.getPopularMovies(apiKey)
            movieLiveData.postValue(popularMovies.results)
        } catch (exception: Exception) {
            // if an error occurs, post the error message to the errorLiveData
            errorLiveData.postValue("An error occurred: ${exception.message}")
        }
    }
}