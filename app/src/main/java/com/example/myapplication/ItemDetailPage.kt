package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.myapplication.api.MovieDetailService
import com.example.myapplication.model.MovieDetail
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.ui.unit.sp
import com.example.myapplication.model.Movie
import com.google.gson.Gson

@Composable
fun ItemDetailPage(
    navController: NavController,
    item: Int,
    viewModel: MainViewModel,
    isLoggedIn: MutableState<Boolean>
) {
    val movieDetailService = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MovieDetailService::class.java)

    var movieDetail by remember { mutableStateOf<MovieDetail?>(null) }
    var error by remember { mutableStateOf<String?>(null) }
    var isInWishlist by remember { mutableStateOf(false) }

    LaunchedEffect(item) {
        try {
            movieDetail = movieDetailService.getMovieDetail(item)
            val movieDetailJson = Gson().toJson(movieDetail)
            Log.d("MovieDetail", movieDetailJson)
            isInWishlist = viewModel.wishlistMovies.value?.any { it.id == item } ?: false
        } catch (e: Exception) {
            error = e.localizedMessage
            Log.e("MovieDetail", "Error: $error", e)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(movieDetail?.title.orEmpty(), color = Color(0xFFF7C873))},
                backgroundColor = Color(0xFF3a3f47),
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (isLoggedIn.value) {
                        if (isInWishlist) {
                            IconButton(
                                onClick = {
                                    val movie = Movie(
                                        id = movieDetail?.id ?: 0,
                                        title = movieDetail?.title.orEmpty(),
                                        poster_path = movieDetail?.poster_path.orEmpty(),
                                        release_date = movieDetail?.release_date.orEmpty()
                                    )
                                    viewModel.removeMovieFromWishlist(movie)
                                    isInWishlist = false
                                },
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Icon(
                                    Icons.Filled.Favorite,
                                    contentDescription = "Remove from Wishlist",
                                )
                            }
                        } else {
                            IconButton(
                                onClick = {
                                    val movie = Movie(
                                        id = movieDetail?.id ?: 0,
                                        title = movieDetail?.title.orEmpty(),
                                        poster_path = movieDetail?.poster_path.orEmpty(),
                                        release_date = movieDetail?.release_date.orEmpty(),
                                    )
                                    viewModel.addMovieToWishlist(movie)
                                    isInWishlist = true
                                },
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Icon(
                                    Icons.Filled.FavoriteBorder,
                                    contentDescription = "Add to Wishlist",
                                )
                            }
                        }
                    }
                },
            )
        },
        content = { innerPadding ->
            if (error != null) {
                // Handle error case
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                        .padding(innerPadding)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            // Image on the left
                            Box(
                                modifier = Modifier
                                    .weight(1f) // Take up half of the screen width
                                    .height(400.dp) // Set the desired image height
                                    .padding(20.dp) // Add padding
                            ) {
                                if (movieDetail != null) {
                                    Image(
                                        painter = rememberImagePainter(
                                            data = "https://image.tmdb.org/t/p/w500${movieDetail?.poster_path}",
                                            builder = {
                                                crossfade(true)
                                            }
                                        ),
                                        contentDescription = "Movie Poster",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    )
                                }
                            }

                            // Text on the right
                            Box(
                                modifier = Modifier
                                    .weight(1f) // Take up the other half of the screen width
                                    .padding(16.dp) // Add padding
                            ) {
                                if (movieDetail != null) {
                                    Column(
                                        modifier = Modifier.fillMaxHeight()
                                    ) {
                                        Text(
                                            text = "Release Date : " + movieDetail?.release_date.orEmpty(),
                                            style = MaterialTheme.typography.h6.copy(fontSize = 20.sp, color = Color.White)
                                        )

                                        Spacer(modifier = Modifier.height(16.dp))

                                        Text(
                                            text = "Description : ",
                                            style = MaterialTheme.typography.h6.copy(fontSize = 20.sp, color = Color.White)
                                        )

                                        Text(
                                            text = movieDetail?.overview.orEmpty(),
                                            fontSize = 20.sp,
                                            color = Color.White
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}


