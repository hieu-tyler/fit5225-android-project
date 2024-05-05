package com.example.homescreen.profile

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.homescreen.Routes
import com.example.homescreen.ViewModel
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ProfileSettingsScreen(
    navController: NavController,
    viewModel: ViewModel,
    userId: String
) {
    // Load the user profile when the composable enters the composition
    LaunchedEffect(userId) {
        viewModel.loadUserProfile(userId)
    }

    // Observe UserProfile LiveData with a default empty UserProfile if null
    val userProfile by viewModel.userProfile.observeAsState(UserProfile.empty())
    if (userProfile.userId == "") {
        return
    }

    var firstName by remember { mutableStateOf(userProfile.firstName) }
    var editingFirstName by remember { mutableStateOf(false) }
    var lastName by remember { mutableStateOf(userProfile.lastName) }
    var editingLastName by remember { mutableStateOf(false) }
    var selectedGender by remember { mutableStateOf(userProfile.selectedGender) }
    var phone by remember { mutableStateOf(userProfile.phone) }
    var editingPhone by remember { mutableStateOf(false) }
    var birthDate by remember { mutableStateOf(userProfile.birthDate) }
    var allowLocation by remember { mutableStateOf(userProfile.allowLocation) }
    var allowActivityShare by remember { mutableStateOf(userProfile.allowActivityShare) }
    var allowHealthDataShare by remember { mutableStateOf(userProfile.allowHealthDataShare) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Profile Settings",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(top = 12.dp).padding(bottom = 12.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "First Name: ",
                    style = MaterialTheme.typography.titleMedium
                )
                if (editingFirstName) {
                    OutlinedTextField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        trailingIcon = {
                            IconButton(onClick = { editingFirstName = false }) {
                                Icon(imageVector = Icons.Default.Check, contentDescription = "Save")
                            }
                        }
                    )
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = firstName,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier
                                .weight(1f)
                                .clickable { editingFirstName = true }
                                .padding(12.dp)
                        )
                        IconButton(onClick = { editingFirstName = true }) {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                        }
                    }
                }
            }
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Last Name: ",
                    style = MaterialTheme.typography.titleMedium
                )
                if (editingLastName) {
                    OutlinedTextField(
                        value = lastName,
                        onValueChange = { lastName = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        trailingIcon = {
                            IconButton(onClick = { editingLastName = false }) {
                                Icon(imageVector = Icons.Default.Check, contentDescription = "Save")
                            }
                        }
                    )
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = lastName,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier
                                .weight(1f)
                                .clickable { editingLastName = true }
                                .padding(12.dp)
                        )
                        IconButton(onClick = { editingLastName = true }) {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                        }
                    }
                }
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Gender:",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                selectedGender,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 12.dp, top = 11.dp)
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth().padding(vertical = 14.dp)
        ) {
            Text(
                "Date of Birth:",
                style = MaterialTheme.typography.titleMedium
            )
            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.ROOT)
            Text(
                formatter.format(birthDate),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 12.dp, top = 11.dp)
            )
        }
        Column {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Phone Number: ",
                    style = MaterialTheme.typography.titleMedium
                )
                if (editingPhone) {
                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        trailingIcon = {
                            IconButton(onClick = { editingPhone = false }) {
                                Icon(imageVector = Icons.Default.Check, contentDescription = "Save")
                            }
                        }
                    )
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = phone,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier
                                .weight(1f)
                                .clickable { editingPhone = true }
                                .padding(12.dp)
                        )
                        IconButton(onClick = { editingPhone = true }) {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp, horizontal = 12.dp)) {
            Switch(
                checked = allowLocation,
                onCheckedChange = { allowLocation = it },
                modifier = Modifier.padding(end = 16.dp)
            )
            Text("Share Location Data", style = MaterialTheme.typography.titleMedium)
        }
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp, horizontal = 12.dp)) {
            Switch(
                checked = allowActivityShare,
                onCheckedChange = { allowActivityShare = it },
                modifier = Modifier.padding(end = 16.dp)
            )
            Text("Share Activity Data", style = MaterialTheme.typography.titleMedium)
        }
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp, horizontal = 12.dp)) {
            Switch(
                checked = allowHealthDataShare,
                onCheckedChange = { allowHealthDataShare = it },
                modifier = Modifier.padding(end = 16.dp)
            )
            Text("Share Health Data", style = MaterialTheme.typography.titleMedium)
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { val updatedProfile = userProfile.copy(
                firstName = firstName,
                lastName = lastName,
                phone = phone,
                allowLocation = allowLocation,
                allowActivityShare = allowActivityShare,
                allowHealthDataShare = allowHealthDataShare
            )
                Log.d("updatedProfile", "phone = $phone")
                viewModel.updateUser(updatedProfile) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Changes")
        }
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedButton(
            onClick = { signOut(navController) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Log Out")
        }
    }
}

fun signOut(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    auth.signOut()
    // Navigate to login screen
    navController.navigate(Routes.Login.value) {
        popUpTo(navController.graph.startDestinationId) {
            inclusive = true
        }
    }
}
