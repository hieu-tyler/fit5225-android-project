package com.example.homescreen.exercise_report

import android.app.Activity
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.NoOpUpdate
import androidx.core.app.ActivityCompat
import com.example.homescreen.MainActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager


@Composable
fun MapView() {
    val defaultLocation = Point.fromLngLat(145.1347, -37.9142)
    var currentLocation: Point = defaultLocation
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    val mapContext = LocalContext.current
    val permissionCode = 101
    fusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(LocalContext.current)
    //Permission check
    if (ActivityCompat.checkSelfPermission(
            LocalContext.current, android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
        PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            LocalContext.current, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
        PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(LocalContext.current as Activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), permissionCode)
    }
    val getLocation = fusedLocationProviderClient.lastLocation
    getLocation.addOnSuccessListener {
            location ->
        if (location != null) {
            val point = Point.fromLngLat(location.latitude, location.longitude)
            Toast.makeText( mapContext, "${point.latitude()}, ${point.longitude()}", Toast.LENGTH_SHORT).show()
            currentLocation = point
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {

        CreateMapBoxView(
            modifier = Modifier
                .fillMaxSize(),
            point = currentLocation
        )

    }
}



@Composable
fun CreateMapBoxView(
    modifier: Modifier,
    point: Point?,
) {

    var pointAnnotationManager: PointAnnotationManager? by remember {
        mutableStateOf(null)
    }
    AndroidView(
        factory = {
            MapView(it).also { mapView ->
                val annotationApi = mapView.annotations
                pointAnnotationManager =
                    annotationApi.createPointAnnotationManager()
            }
        },
        update = { mapView ->
            if (point != null) {
                pointAnnotationManager?.let {
                    //in this scope {} it refers to pointAnnotationManager
                    it.deleteAll()
                    val pointAnnotationOptions = PointAnnotationOptions()
                        .withPoint(point)
                    it.create(pointAnnotationOptions)
                    mapView.mapboxMap.setCamera(
                        CameraOptions.Builder()
                            .center(point)
                            .pitch(0.0)
                            .zoom(16.0)
                            .bearing(0.0)
                            .build()
                    )
                }
            }
            NoOpUpdate
        },
        modifier = modifier

    )
}

