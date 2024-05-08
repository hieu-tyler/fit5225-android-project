package com.example.homescreen.exercise_report

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.homescreen.ViewModel
import com.example.homescreen.checkPermission
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MapScreen(navController: NavController, viewModel: ViewModel) {

    val startTimestamp =System.currentTimeMillis()
    Box (
        modifier = Modifier.fillMaxSize()
    ) {

        FilledTonalButton(
            onClick = { calculateUpdate(viewModel,navController, startTimestamp) },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = MaterialTheme.colorScheme.error)
        ) {
            Text(text = "Stop Exercise")
        }
    }
}

@Composable
fun GoogleMapScreen() {
    lateinit var fusedLocationProviderClient : FusedLocationProviderClient
    val singapore = LatLng(1.35, 103.87)
    val context = LocalContext.current

    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(LocalContext.current)
    checkPermission()
    var currentLocation = singapore
    val task = fusedLocationProviderClient.lastLocation

    task.addOnSuccessListener {
        if (it != null) {
            Toast.makeText(context, "${it.latitude} ${it.longitude}", Toast.LENGTH_SHORT).show()
            currentLocation = LatLng(it.latitude,it.longitude)
        }
        else {
            Toast.makeText(context, "Null value", Toast.LENGTH_SHORT).show()
        }
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentLocation, 10f)
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = currentLocation),
            title = "Location",
            snippet = "Marker in Location"
        )
    }
}
