package com.example.homescreen

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.example.homescreen.ui.theme.HomeScreenTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.mapbox.common.location.Location
import com.mapbox.geojson.Point

class MainActivity : ComponentActivity() {

    private val viewModel : ViewModel by viewModels()
    private lateinit var currentLocation: Point
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val permissionCode = 101
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreenTheme {
                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
                getCurrentLocation()
                HomeScreen(viewModel)
            }
        }
    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
            this, android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED
            ) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), permissionCode)
            return
        }

        val getLocation = fusedLocationProviderClient.lastLocation.addOnSuccessListener {
            location ->
            if (location != null) {
               val point = Point.fromLngLat(location.latitude, location.longitude)
                Toast.makeText(this, "${point.latitude()}, ${point.longitude()}", Toast.LENGTH_SHORT).show()
                currentLocation = point
            }
        }

    }


}
