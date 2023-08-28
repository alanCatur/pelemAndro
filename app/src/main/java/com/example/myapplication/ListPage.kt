package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.myapplication.api.NowPlayingService
import com.example.myapplication.api.PopularService


import com.example.myapplication.model.Movie
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.compose.ui.text.font.FontWeight
import com.example.myapplication.api.TopRatedService
import androidx.compose.foundation.border
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.ui.draw.clip

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.MutableState

@Composable
fun ListPage(navController: NavController, isLoggedIn: MutableState<Boolean>) {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val movieService = retrofit.create(NowPlayingService::class.java)
    val popularService = retrofit.create(PopularService::class.java)
    val topRatedService = retrofit.create(TopRatedService::class.java)


    var movies by remember { mutableStateOf(listOf<Movie>()) }
    var popularMovies by remember { mutableStateOf(listOf<Movie>()) }
    var topRatedMovies by remember { mutableStateOf(listOf<Movie>()) }
    var error by remember { mutableStateOf<String?>(null) }



    LaunchedEffect(Unit) {
        try {
            movies = movieService.getNowPlayingMovies().results
            popularMovies = popularService.getPopularMovies().results
            topRatedMovies = topRatedService.getTopRatedMovies().results
        } catch (e: Exception) {
            error = e.localizedMessage
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize() // Mengisi layar penuh
            .background(Color.Black) // Warna latar belakang hitam
    ) {
        TopAppBar(
            title = { Color(0xFFF7C873) },
            backgroundColor = Color(0xFF3a3f47),
            actions = {

                if (isLoggedIn.value) {
                    IconButton(onClick = { navController.navigate("wishlist") }) {
                        Icon(Icons.Filled.Favorite, contentDescription = "Wishlist")
                    }
                }

                if (!isLoggedIn.value) {
                    Button(
                        onClick = {
                            navController.navigate("login")
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFFF7C873), // Warna latar belakang hitam
                            contentColor = Color.White // Warna teks Color(0xFF6A0EAC)
                        )
                    ) {
                        Text(
                            text = "Login",
                            fontWeight = FontWeight.Bold
                        )

                    }
                }
            }
        )

        LazyColumn {
            if (error != null) {
                item {
                    Text("Error: $error")
                }
            } else {
                item {
                    Text(
                        text = "Now Playing",
                        modifier = Modifier.padding(8.dp),
                        color = Color(0xFFF7C873),
                        fontWeight = FontWeight.Bold
                    )
                }
                item {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(movies) { movie ->
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .padding(1.dp)
                                    .background(
                                        color = Color(0xFF6A0EAC),
                                        shape = RoundedCornerShape(10.dp) // Rounded corners for the background
                                    )
                                    .clickable { navController.navigate("detail/${movie.id}") }
                            ) {
                                Image(
                                    painter = rememberImagePainter(
                                        data = "https://image.tmdb.org/t/p/w500${movie.poster_path}",
                                        builder = {
                                            crossfade(true)
                                        }
                                    ),
                                    contentDescription = "Movie Poster",
                                    modifier = Modifier
                                        .size(100.dp, 150.dp)
                                        .clip(RoundedCornerShape(10.dp)), // Rounded corners for the image
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }

                item {
                    Text(
                        text = "Popular",
                        modifier = Modifier.padding(8.dp),
                        color = Color(0xFFF7C873),
                        fontWeight = FontWeight.Bold
                    )
                }

                item {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(popularMovies) { movie ->
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .padding(1.dp)
                                    .background(
                                        color = Color(0xFF6A0EAC),
                                        shape = RoundedCornerShape(10.dp) // Rounded corners for the background
                                    )
                                    .clickable { navController.navigate("detail/${movie.id}") }
                            ) {
                                Image(
                                    painter = rememberImagePainter(
                                        data = "https://image.tmdb.org/t/p/w500${movie.poster_path}",
                                        builder = {
                                            crossfade(true)
                                        }
                                    ),
                                    contentDescription = "Movie Poster",
                                    modifier = Modifier
                                        .size(100.dp, 150.dp)
                                        .clip(RoundedCornerShape(10.dp)), // Rounded corners for the image
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }

                item {
                    Text(
                        text = "Top Rated",
                        modifier = Modifier.padding(8.dp),
                        color = Color(0xFFF7C873),
                        fontWeight = FontWeight.Bold
                    )
                }
                item {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(topRatedMovies) { movie ->
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .padding(1.dp)
                                    .background(
                                        color = Color(0xFF6A0EAC),
                                        shape = RoundedCornerShape(10.dp) // Rounded corners for the background
                                    )
                                    .clickable { navController.navigate("detail/${movie.id}") }
                            ) {
                                Image(
                                    painter = rememberImagePainter(
                                        data = "https://image.tmdb.org/t/p/w500${movie.poster_path}",
                                        builder = {
                                            crossfade(true)
                                        }
                                    ),
                                    contentDescription = "Movie Poster",
                                    modifier = Modifier
                                        .size(100.dp, 150.dp)
                                        .clip(RoundedCornerShape(10.dp)), // Rounded corners for the image
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}