package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.text.style.TextAlign
import com.example.myapplication.database.DatabaseHelper
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle


@Composable
fun LoginPage(navController: NavController, databaseHelper: DatabaseHelper, isLoggedIn: MutableState<Boolean>) {
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val showError = remember { mutableStateOf(false) }
    val navigateToList = remember { mutableStateOf(false) }

    LaunchedEffect(navigateToList.value) {
        if (navigateToList.value) {
            val user = databaseHelper.userDao().getUser(username.value, password.value)
            if (user != null) {
                isLoggedIn.value = true
                navController.navigate("list")
                navigateToList.value = false
            } else {
                showError.value = true
                navigateToList.value = false
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Set background color to black
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.pelem),
                contentDescription = "Login",
                modifier = Modifier.size(250.dp),
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = username.value,
                onValueChange = { newValue -> username.value = newValue },
                label = { Text("Username", color = Color(0xFFF7C873)) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black),
                textStyle = TextStyle(color = Color.White)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password.value,
                onValueChange = { newValue -> password.value = newValue },
                label = { Text("Password", color = Color(0xFFF7C873)) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black),
                textStyle = TextStyle(color = Color.White)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (username.value.isEmpty() || password.value.isEmpty()) {
                        showError.value = true
                    } else {
                        navigateToList.value = true
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFF7C873),
                    contentColor = Color.White
                )
            ) {
                Text("Login")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Don't have an account?",
                textAlign = TextAlign.Center,
                color = Color(0xFFF7C873)
            )

            Button(
                onClick = { navController.navigate("register") },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent,
                    contentColor = Color(0xFFF7C873) // Change text color of the "Register" button
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally) // Center the button
            ) {
                Text("Register")
            }
        }

        if (showError.value) {
            Snackbar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 240.dp),
                action = {
                    Button(onClick = { showError.value = false }) {
                        Text("Dismiss", color = Color(0xFFF7C873))
                    }
                }
            ) {
                Text("Username atau Password tidak boleh kosong", color = Color(0xFFF7C873))
            }
        }
    }
}
