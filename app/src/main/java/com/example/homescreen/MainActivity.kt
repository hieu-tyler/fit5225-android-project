package com.example.homescreen

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.example.homescreen.ui.theme.HomeScreenTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.GoogleMap
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager

class MainActivity : ComponentActivity() {

    private val viewModel : ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HomeScreenTheme {
                HomeScreen(viewModel)
            }
        }

    }

}

@Composable
fun checkPermission() {

    if (ActivityCompat.checkSelfPermission(
            LocalContext.current,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            LocalContext.current,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
//        ActivityCompat.requestPermissions(Activity(), arrayOf( Manifest.permission.ACCESS_FINE_LOCATION), 101)

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