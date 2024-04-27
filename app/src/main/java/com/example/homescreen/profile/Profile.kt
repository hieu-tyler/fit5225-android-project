package com.example.homescreen.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
@Composable
fun Profile(navController: NavHostController){
    var name by remember { mutableStateOf("") }
    Box (modifier = Modifier. fillMaxSize(),
        contentAlignment = Alignment.Center)
    {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Profile Screen", style =
            MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.size(30.dp))
            Text("Here you create a form for entering user profile ", style
            = MaterialTheme.typography.bodyLarge)
        }
    }
}