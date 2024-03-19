package com.example.homescreen

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.homescreen.ui.theme.HomeScreenTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.rememberNavController
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Calendar
import java.util.Date
import java.util.Locale

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            LoginScreen({ _, _ ->})
//            RegistrationScreen({ _, _, _, _, _, _, _ ->}, {->})
            HomeScreen()
        }
    }
}

@Composable
fun LoginScreen(onLoginClicked: (String, String) -> Unit) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
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
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onLoginClicked(email, password) },
            modifier = Modifier.height(46.dp).width(190.dp)
        ) {
            Text("Login")
        }
        Spacer(modifier = Modifier.height(6.dp))
        TextButton(onClick = { /* Handle forgot password */ }) {
            Text("Forgot Password?")
        }
        Row {
            TextButton(onClick = { /* Navigate to registration screen */ },
                ) {
                Text("Don't have an account? Sign Up!")
            }
        }
        // Google Sign-In Button
        Button(
            onClick = { /* Implement Google Sign-In logic here */ },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            contentPadding = PaddingValues(0.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.android_light_rd),
                contentDescription = "Sign in with Google",
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )
        }
    }
}

@RequiresApi(0)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    onRegisterClicked: (String, String, String, String, String, String, Date) -> Unit,
    onNavigateToLogin: () -> Unit // Navigate back to login screen
) {
    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }
    val genders = listOf("Male", "Female")
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    var selectedGender by rememberSaveable { mutableStateOf(genders[0]) }
    var phone by rememberSaveable { mutableStateOf("") }
    val calendar = Calendar.getInstance()
    calendar.set(2024, 0, 1) // month (0) is January
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli()
    )
    var showDatePicker by rememberSaveable { mutableStateOf(false) }
    var selectedDate by rememberSaveable { mutableStateOf(calendar.timeInMillis) }
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
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
                modifier = Modifier.menuAnchor().fillMaxWidth().focusProperties {
                        canFocus = false
                    }.padding(bottom = 8.dp),
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
                genders.forEach { selectionOption ->
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
                modifier = Modifier.weight(1f).height(46.dp)
            ) { Text(text = "Enter Date of Birth") }
            Spacer(modifier = Modifier.width(12.dp))
            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.ROOT)
            Text(
                text = "${formatter.format(Date(selectedDate))}",
                modifier = Modifier.weight(1f)
            )
        }
        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        showDatePicker = false
                        selectedDate = datePickerState.selectedDateMillis!!
                    }) {Text(text = "OK") }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) { Text(text = "Cancel") }
                }
            ) { DatePicker(state = datePickerState) }
        }
        Spacer(modifier = Modifier.height(22.dp))
        Button(
            onClick = { onRegisterClicked(firstName, lastName, email, password, selectedGender, phone, Date(selectedDate)) },
            modifier = Modifier.height(46.dp).width(190.dp)
        ) { Text("Register") }
        Row(
            modifier = Modifier.padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Already have an account? ")
            TextButton(onClick = onNavigateToLogin) {Text("Login here!") }
        }
    }
}

sealed class Screen(val route: String)

object DailyActivities : Screen("Daily Activities")
object NutritionalIntake : Screen("Nutritional Intake")
object HealthMetrics : Screen("Health Metrics")
object ProfileAndSettings : Screen("Profile and Settings")

@SuppressLint("UnrememberedMutableState")
@Composable
fun HomeScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) {
        NavHost(navController = navController, startDestination = DailyActivities.route) {
            composable(DailyActivities.route) { DailyActivitiesScreen() }
            composable(NutritionalIntake.route) { NutritionalIntakeScreen() }
            composable(HealthMetrics.route) { HealthMetricsScreen() }
            composable(ProfileAndSettings.route) { ProfileAndSettingsScreen() }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(DailyActivities, NutritionalIntake, HealthMetrics, ProfileAndSettings)
    BottomNavigation(
        backgroundColor = Color.Yellow, // Set the background color to yellow
    ) {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        items.forEach { screen ->
            val icon = getIconForScreen(screen)
            BottomNavigationItem(
                icon = { Icon(icon, contentDescription = null) },
                label = {
                    Text(
                        text = screen.route,
                        textAlign = TextAlign.Center,
                    )
                },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        navController.graph.startDestinationRoute?.let { route -> popUpTo(route) {
                            saveState = true
                        }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun getIconForScreen(screen: Screen): ImageVector {
    return when (screen) {
        DailyActivities -> Icons.Filled.Create
        NutritionalIntake -> Icons.Filled.Info
        HealthMetrics -> Icons.Filled.Star
        ProfileAndSettings -> Icons.Filled.Settings
    }
}

@Composable
fun DailyActivitiesScreen() {
    Text("Daily Activities")
}

@Composable
fun NutritionalIntakeScreen() {
    Text("Nutritional Intake")
}

@Composable
fun HealthMetricsScreen() {
    Text("Health Metrics")
}

@Composable
fun ProfileAndSettingsScreen() {
    Text("Profile and Settings")
}

@Composable
fun TabContent(title: String) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.surface) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
