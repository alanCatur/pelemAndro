
package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.myapplication.database.DatabaseHelper

class MainActivity : ComponentActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dbHelper = Room.databaseBuilder(
            applicationContext,
            DatabaseHelper::class.java, "database-name"
        ).build()

        viewModel = MainViewModel(dbHelper)

        setContent {
            MaterialTheme {
                Surface {
                    val navController = rememberNavController()
                    val isLoggedIn = remember { mutableStateOf(false) } // Remember login status

                    NavHost(navController = navController, startDestination = "list") {
                        composable("login") {
                            LoginPage(navController, dbHelper, isLoggedIn)
                        }
                        composable("register") {
                            RegisterPage(navController, dbHelper)
                        }
                        composable("list") {
                            ListPage(navController, isLoggedIn)
                        }
                        composable("wishlist") {
                            WishlistPage(navController, viewModel) // Pass ViewModel to WishlistPage
                        }
                        composable("detail/{movie_id}") { backStackEntry ->
                            val movieId = backStackEntry.arguments?.getString("movie_id")?.toIntOrNull()
                            if (movieId != null) {
                                ItemDetailPage(navController = navController, item = movieId, viewModel = viewModel,isLoggedIn)
                            }
                        }
                    }
                }
            }
        }
    }
}