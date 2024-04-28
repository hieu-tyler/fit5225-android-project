package com.example.homescreen.profile

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ProfileSettingsScreen(
    navController: NavHostController,
    userProfile: UserProfile, // Assuming UserProfile is a data class containing user info
    onSaveProfile: (UserProfile) -> Unit, // Callback when Save button is clicked
    onSignOut: () -> Unit // Callback when Sign Out button is clicked
) {
    // Local state for form fields, initialized with userProfile data
    var userId by rememberSaveable { mutableStateOf(userProfile.userId) }
    var firstName by rememberSaveable { mutableStateOf(userProfile.firstName) }
    var editingFirstName by remember { mutableStateOf(false) }
    var lastName by rememberSaveable { mutableStateOf(userProfile.lastName) }
    var editingLastName by remember { mutableStateOf(false) }
    var email by rememberSaveable { mutableStateOf(userProfile.email) }
    var password by rememberSaveable { mutableStateOf(userProfile.password) }
    var selectedGender by rememberSaveable { mutableStateOf(userProfile.selectedGender) }
    var phone by rememberSaveable { mutableStateOf(userProfile.phone) }
    var editingPhone by remember { mutableStateOf(false) }
    var birthDate by rememberSaveable { mutableStateOf(userProfile.birthDate) }
    var allowLocation by rememberSaveable { mutableStateOf(userProfile.allowLocation) }
    var allowActivityShare by rememberSaveable { mutableStateOf(userProfile.allowActivityShare) }
    var allowHealthDataShare by rememberSaveable { mutableStateOf(userProfile.allowHealthDataShare) }
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "First Name: ",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier)
                if (editingFirstName) {
                    OutlinedTextField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        trailingIcon = {
                            IconButton(onClick = { editingFirstName = false }) {
                                Icon(imageVector = Icons.Default.Check, contentDescription = "Save")
                            }
                        }
                    )
                } else {
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Last Name: ",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier)
                if (editingLastName) {
                    OutlinedTextField(
                        value = lastName,
                        onValueChange = { lastName = it },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        trailingIcon = {
                            IconButton(onClick = { editingLastName = false }) {
                                Icon(imageVector = Icons.Default.Check, contentDescription = "Save")
                            }
                        }
                    )
                } else {
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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Gender:    $selectedGender",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth().padding(vertical = 14.dp)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(vertical = 14.dp)
        ) {
            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.ROOT)
            Text(
                "Date of Birth:    ${formatter.format(birthDate)}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Phone Number: ",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier)
                if (editingPhone) {
                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        trailingIcon = {
                            IconButton(onClick = { editingPhone = false }) {
                                Icon(imageVector = Icons.Default.Check, contentDescription = "Save")
                            }
                        }
                    )
                } else {
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
            onClick = { onSaveProfile(UserProfile(userId, firstName, lastName, email, password, selectedGender, phone, birthDate, allowLocation, allowActivityShare, allowHealthDataShare)) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Changes")
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(
            onClick = onSignOut,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancel")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PofileSetting() {
    val navController = rememberNavController()
    val sampleUserProfile = UserProfile(
        userId = 1,
        firstName = "John",
        lastName = "Doe",
        email = "johndoe@example.com",
        password = "password123",
        selectedGender = "Male",
        phone = "0412345678",
        birthDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse("01/01/1990") ?: Date(),
        allowLocation = true,
        allowActivityShare = true,
        allowHealthDataShare = false
    )
    ProfileSettingsScreen(navController, sampleUserProfile, {}, {})
}