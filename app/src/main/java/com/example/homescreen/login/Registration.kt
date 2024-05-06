package com.example.homescreen

import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.homescreen.profile.UserProfile
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Calendar
import java.util.Date
import java.util.Locale

@RequiresApi(0)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    createUserWithEmailPassword: (String, String, String, String, String, String, Date) -> Unit,
    navController: NavController, viewModel: ViewModel) {
    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }
    val gender = listOf("Male", "Female")
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    var selectedGender by rememberSaveable { mutableStateOf(gender[0]) }
    var phone by rememberSaveable { mutableStateOf("") }
    val calendar = Calendar.getInstance()
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli()
    )
    var showDatePicker by rememberSaveable { mutableStateOf(false) }
    var birthDate by rememberSaveable { mutableStateOf(calendar.timeInMillis) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Create Your Account",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        TextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("First Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Last Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
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
        Spacer(modifier = Modifier.height(8.dp))
        ExposedDropdownMenuBox(expanded = isExpanded, onExpandedChange = { isExpanded = it }) {
            TextField(
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .focusProperties {
                        canFocus = false
                    }
                    .padding(bottom = 8.dp),
                readOnly = true,
                value = selectedGender,
                onValueChange = {},
                label = { Text("Gender") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) }
            )
            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false })
            {
                gender.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            selectedGender = selectionOption
                            isExpanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }
        TextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = { showDatePicker = true },
                modifier = Modifier
                    .weight(1f)
                    .height(46.dp)
            ) { Text(text = "Enter Date of Birth") }
            Spacer(modifier = Modifier.width(12.dp))
            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.ROOT)
            Text(
                text = "${formatter.format(Date(birthDate))}",
                modifier = Modifier.weight(1f)
            )
        }
        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        showDatePicker = false
                        birthDate = datePickerState.selectedDateMillis!!
                    }) { Text(text = "OK") }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) { Text(text = "Cancel") }
                }
            ) { DatePicker(state = datePickerState) }
        }
        Spacer(modifier = Modifier.height(22.dp))
        Button(
            onClick = { createUserWithEmailPassword(firstName, lastName, email, password, selectedGender, phone, Date(birthDate), navController, viewModel) },
            modifier = Modifier
                .height(46.dp)
                .width(190.dp)
        ) { Text("Register") }
        Row(
            modifier = Modifier.padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Already have an account? ")
            TextButton(onClick = { navController.navigate(Routes.Login.value) }) { Text("Login here!") }
        }
    }
}

fun createUserWithEmailPassword(firstName: String, lastName: String, email: String, password: String,
                                selectedGender: String, phone: String, birthDate: Date, navController: NavController,
                                viewModel: ViewModel) {
    val auth = FirebaseAuth.getInstance()
    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            Log.d("Register Success", "User email: $email")
            val userId = auth.currentUser?.uid ?: ""  // Retrieve the unique user ID from Firebase
            if (userId.isNotEmpty()) {
                val userProfile = UserProfile(
                    userId = userId,
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    password = password,
                    selectedGender = selectedGender,
                    phone = phone,
                    birthDate = birthDate,
                    allowLocation = false,
                    allowActivityShare = false,
                    allowHealthDataShare = false
                )
                viewModel.insertUser(userProfile)
                navController.navigate(Routes.Login.value)  // Navigate to login screen after successful registration
            } else {
                Log.e("Registration", "Failed to retrieve Firebase user ID.")
            }
        } else {
            Log.e("Registration", "Registration failed: ${task.exception?.message}")
        }
    }
}

//fun createUserWithEmailPassword(firstName: String, lastName: String, email: String, password: String,
//                                selectedGender: String, phone: String, birthDate: Date, navController: NavController) {
//    val auth = FirebaseAuth.getInstance()
//    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
//        if (task.isSuccessful) {
//            val user = auth.currentUser
//            val userId = user?.uid // Get the user ID from the Firebase Auth User
//            val userMap = hashMapOf(
//                "firstName" to firstName,
//                "lastName" to lastName,
//                "gender" to selectedGender,
//                "phone" to phone,
//                "birthDate" to birthDate
//            )
//            // Store additional details in Firestore
//            user?.let {
//                FirebaseFirestore.getInstance().collection("users").document(userId!!)
//                    .set(userMap)
//                    .addOnSuccessListener {
//                        Log.d("Register", "User profile created for $userId")
//                        // Navigate to the next screen or home screen
//                        navController.navigate(Routes.HealthMetrics.value)
//                    }
//                    .addOnFailureListener { e ->
//                        Log.w("Register", "Error writing document", e)
//                    }
//            }
//        } else {
//            Log.e("Register", "Failed to create user: ${task.exception?.message}")
//        }
//    }
//}
