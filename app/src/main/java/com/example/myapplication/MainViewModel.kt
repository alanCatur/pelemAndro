// MainViewModel.kt
package com.example.myapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.database.DatabaseHelper
import com.example.myapplication.model.Movie
import kotlinx.coroutines.launch

class MainViewModel(private val db: DatabaseHelper) : ViewModel() {
    val wishlistMovies = MutableLiveData<List<Movie>>()

    init {
        viewModelScope.launch {
            wishlistMovies.value = db.wishlistDao().getAllMovies()
        }
    }

    fun addMovieToWishlist(movie: Movie) {
        viewModelScope.launch {
            db.wishlistDao().insertMovie(movie)
            wishlistMovies.value = db.wishlistDao().getAllMovies()
        }
    }

    fun removeMovieFromWishlist(movie: Movie) {
        viewModelScope.launch {
            db.wishlistDao().deleteMovie(movie)
            wishlistMovies.value = db.wishlistDao().getAllMovies()

        }
    }
}