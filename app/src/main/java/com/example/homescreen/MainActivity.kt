package com.example.homescreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.homescreen.ViewModel
import com.example.homescreen.ui.theme.HomeScreenTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {

    private val viewModel: ViewModel by viewModels()
    @RequiresApi(64)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreenTheme {
//
//                HomeScreen(viewModel)
                val navController = rememberNavController()
                // for user to login when open the app
                // FirebaseAuth.getInstance().signOut()
                NavHost(navController = navController, startDestination = "login") {
                    composable("login") {
                        // Check if the user is already logged in
                        if (FirebaseAuth.getInstance().currentUser == null) {
                            LoginScreen(navController, onLoginClicked = { email, password ->
                                performLogin(email, password, navController)
                            })
                        } else {
                            navigateToHome(navController)
                        }
                    }
                    composable("register") {
                        RegistrationScreen(navController,
                            onNavigateToLogin = { navController.navigate("login") {
                                popUpTo("login") { inclusive = true }
                            } }
                        )
                    }
                    composable("home") {
                        HomeScreen(viewModel)
                    }
                }
            }
        }
    }

    private fun performLogin(email: String, password: String, navController: NavController) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    navigateToHome(navController)
                } else {
                    // Handle login failure (show error message)
                }
            }
    }

    private fun navigateToHome(navController: NavController) {
        navController.navigate("home") {
            popUpTo("login") { inclusive = true }  // Clear the back stack
        }
    }
}


// TODO: Clean up this HomeScreen function
//@SuppressLint("UnrememberedMutableState")
//@Composable
//fun HomeScreen() {
//    val tabs = listOf(
//        "Daily Activities",
//        "Nutritional Intake",
//        "Health Metrics",
//        "Profile and Setting Management"
//    )
//
//    var selectedTabIndex by mutableStateOf(0)
//
//    Surface(color = MaterialTheme.colorScheme.background) {
//        Column(modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)) {
//            TabRow(selectedTabIndex) {
//                tabs.forEachIndexed { index, title ->
//                    Tab(
//                        selected = selectedTabIndex == index,
//                        onClick = { selectedTabIndex = index }
//                    ) {
//                        Text(
//                            text = title,
//                            style = MaterialTheme.typography.titleSmall,
//                            textAlign = TextAlign.Center,
//                            modifier = Modifier.fillMaxWidth()
//                        )
//                    }
//                }
//            }
//            TabContent(tabs[selectedTabIndex])
//        }
//    }
//}

// TODO: Clean up this TabContent function
//@Composable
//fun TabContent(title: String) {
//    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.surface) {
//        Column(modifier = Modifier.padding(24.dp)) {
//            Text(text = title,
//                style = MaterialTheme.typography.headlineMedium,
//                color = MaterialTheme.colorScheme.onSurface)
//        }
//    }
//}

// TODO: Clean up this HomeScreenPreview function
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreenTheme {
//        HomeScreen()
    }
}