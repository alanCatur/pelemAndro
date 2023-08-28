package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.database.DatabaseHelper
import com.example.myapplication.database.User
import kotlinx.coroutines.launch

@Composable
fun RegisterPage(navController: NavController, databaseHelper: DatabaseHelper) {
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val showError = remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Tambahkan gambar di atas input username
            Image(
                painter = painterResource(id = R.drawable.pelem), // Ganti dengan resource ID gambar Anda
                contentDescription = "Logo", // Ganti dengan deskripsi gambar
                modifier = Modifier.size(200.dp) // Sesuaikan ukuran gambar sesuai keinginan Anda
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Register", style = MaterialTheme.typography.h4)

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = username.value,
                onValueChange = { newValue -> username.value = newValue },
                label = { Text("Username", color = Color(0xFFF7C873)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().background(Color.Black),
                textStyle = TextStyle(color = Color.White)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password.value,
                onValueChange = { newValue -> password.value = newValue },
                label = { Text("Password", color = Color(0xFFF7C873)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().background(Color.Black),
                textStyle = TextStyle(color = Color.White)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                if (username.value.isEmpty() || password.value.isEmpty()) {
                    showError.value = true
                } else {
                    coroutineScope.launch {
                        databaseHelper.userDao().insertUser(User(username = username.value, password = password.value))
                        navController.navigate("login")
                    }
                }
            }) {
                Text("Register")
            }
        }

        if (showError.value) {
            Snackbar(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(bottom = 240.dp),
                action = {
                    Button(onClick = { showError.value = false }) {
                        Text("Dismiss")
                    }
                }
            ) {

            }
        }
    }
}
