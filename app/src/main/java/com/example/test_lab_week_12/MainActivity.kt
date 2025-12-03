package com.example.test_lab_week_12

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.test_lab_week_12.model.Movie
import com.google.android.material.snackbar.Snackbar
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    // Inisialisasi adapter dengan Click Listener agar bisa pindah ke halaman detail
    private val movieAdapter by lazy {
        // Kita membuat objek anonymous yang mengimplementasikan interface MovieClickListener
        MovieAdapter(object : MovieAdapter.MovieClickListener {
            override fun onMovieClick(movie: Movie) {
                openMovieDetails(movie)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.movie_list)
        recyclerView.adapter = movieAdapter

        // 1. Mengambil repository dari Application class
        val movieRepository = (application as MovieApplication).movieRepository

        // 2. Inisialisasi ViewModel menggunakan Factory
        // Kita butuh Factory karena MovieViewModel memiliki parameter constructor (movieRepository)
        val movieViewModel = ViewModelProvider(
            this, object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MovieViewModel(movieRepository) as T
                }
            }
        )[MovieViewModel::class.java]

        // 3. Mengamati data popularMovies
        movieViewModel.popularMovies.observe(this) { popularMovies ->
            // Ambil tahun saat ini (misal: 2023/2024)
            val currentYear = Calendar.getInstance().get(Calendar.YEAR).toString()

            movieAdapter.addMovies(
                popularMovies
                    .filter { movie ->
                        // Filter: Hanya ambil film yang rilis tahun ini
                        // releaseDate bisa null, jadi pakai safe call ?.
                        movie.releaseDate?.startsWith(currentYear) == true
                    }
                    .sortedByDescending { it.popularity } // Urutkan dari yang terpopuler
            )
        }

        // 4. Mengamati pesan error
        movieViewModel.error.observe(this) { error ->
            if (error.isNotEmpty()) {
                Snackbar.make(recyclerView, error, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    // Fungsi untuk intent ke DetailsActivity
    private fun openMovieDetails(movie: Movie) {
        val intent = Intent(this, DetailsActivity::class.java).apply {
            putExtra(DetailsActivity.EXTRA_TITLE, movie.title)
            putExtra(DetailsActivity.EXTRA_RELEASE, movie.releaseDate)
            putExtra(DetailsActivity.EXTRA_OVERVIEW, movie.overview)
            putExtra(DetailsActivity.EXTRA_POSTER, movie.posterPath)
        }
        startActivity(intent)
    }
}