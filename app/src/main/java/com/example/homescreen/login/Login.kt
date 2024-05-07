package com.example.homescreen

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(loginWithEmailPassword: (String, String, (String) -> Unit) -> Unit,
                navController: NavController, viewModel: ViewModel) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var showResetPasswordDialog by remember { mutableStateOf(false) }
    var loginError by remember { mutableStateOf("") }

    val context = LocalContext.current
    val signInIntent by viewModel.googleSignInIntent.observeAsState()

    // Setup the launcher
    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleSignInResult(task, viewModel)
        }
    }

    // Listen for changes in the signInIntent and launch the Google Sign-In process when not null
    LaunchedEffect(signInIntent) {
        signInIntent?.let { intent ->
            googleSignInLauncher.launch(intent)
            navController.navigate(Routes.HealthMetrics.value) {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
            }
        }
    }

//    // Observing the navigation LiveData
//    val navigateToHealthMetrics by viewModel.navigateToHealthMetrics.observeAsState()
//
//    // React to changes in navigation trigger
//    LaunchedEffect(key1 = navigateToHealthMetrics) {
//        navigateToHealthMetrics?.let {
//            if (it) {
//                navController.navigate(Routes.HealthMetrics.value) {
//                    popUpTo(navController.graph.startDestinationId) {
//                        inclusive = true
//                    }
//                }
//                viewModel.resetNavigationTrigger()  // Make sure to reset the trigger
//            }
//        }
//    }

//    val context = LocalContext.current
//    val signInIntent by viewModel.signInIntent.observeAsState()
//    val navigateToHealthMetrics by viewModel.navigateToHealthMetrics.observeAsState()
//
//    // Observe the navigation event
//    LaunchedEffect(navigateToHealthMetrics) {
//        if (navigateToHealthMetrics == true) {
//            navController.navigate(Routes.HealthMetrics.value) {  // Use your actual home route
//                popUpTo(navController.graph.startDestinationId) {
//                    inclusive = true
//                }
//            }
//        }
//    }

    if (showResetPasswordDialog) {
        PasswordResetDialog(
            initialEmail = email,  // Pass the current email state
            onDismiss = { showResetPasswordDialog = false },
            viewModel = viewModel
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Login to Your Account",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp) // Add some space below the title
        )
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon = if (isPasswordVisible) painterResource(id = R.drawable.eye) else painterResource(id = R.drawable.hidden)
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        painter = icon,
                        contentDescription = "Show or hide password",
                        modifier = Modifier.height(22.dp)
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = {
                loginWithEmailPassword(email, password, navController) { error -> loginError = error }
            },
            modifier = Modifier
                .height(46.dp)
                .width(190.dp)
        ) {
            Text("Login")
        }
        Spacer(modifier = Modifier.height(4.dp))
        if (loginError.isNotEmpty()) {
            Snackbar {
                Text(loginError)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        // Google Sign-In Button
        Button(
            onClick = { viewModel.signInWithGoogle() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            contentPadding = PaddingValues(0.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.android_light_rd),
                contentDescription = "Sign in with Google",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        TextButton(onClick = { showResetPasswordDialog = true }) {
            Text("Forgot Password?")
        }
        Row(modifier = Modifier.padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Don't have an account? ")
            TextButton(onClick = { navController.navigate(Routes.Registration.value) },
            ) {
                Text("Register here!")
            }
        }
    }
}

fun loginWithEmailPassword(email: String, password: String,
                           navController: NavController,
                           onLoginError: (String) -> Unit) {
    val auth = FirebaseAuth.getInstance()
    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            Log.d("Login Success", "User email: $email")
            Log.d("NavController", "Current back stack entry: ${navController.currentBackStackEntry}")
            if (navController.currentBackStackEntry != null) {
                navController.navigate(Routes.HealthMetrics.value) {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                }
            }
        } else {
            Log.e("Login Error", task.exception?.message ?: "Unknown Error")
            val errorMessage = "Login failed: please make sure you provide the correct email and password!"
            onLoginError(errorMessage)  // Pass the error message back to the Composable
        }
    }
}

@Composable
fun PasswordResetDialog(initialEmail: String, onDismiss: () -> Unit, viewModel: ViewModel) {
    var email by rememberSaveable { mutableStateOf(initialEmail) }
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Password Reset") },
        text = {
            Column {
                Text("Enter your email to reset your password:")
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    singleLine = true,
                    placeholder = { Text("Email address") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    viewModel.sendPasswordResetEmail(email)
                    onDismiss()
                }
            ) { Text("Send Email") }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) { Text("Cancel") }
        }
    )
}

fun handleSignInResult(task: Task<GoogleSignInAccount>, viewModel: ViewModel) {
    try {
        val account = task.getResult(ApiException::class.java)
        Log.d("GoogleLogin", "Google Sign-In successful for: ${account.email}")
        viewModel.firebaseAuthWithGoogle(account.idToken!!)
    } catch (e: ApiException) {
        Log.e("GoogleLogin", "signInResult:failed code=${e.statusCode}", e)
    }
}
