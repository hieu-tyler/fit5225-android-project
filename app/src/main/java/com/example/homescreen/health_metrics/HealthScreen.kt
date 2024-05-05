package com.example.homescreen.health_metrics

import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.homescreen.Routes
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.WriteBatch
import java.util.Date

@RequiresApi(64)
@Composable
fun HealthScreen() {
    val subNavController = rememberNavController()


    fun convertMetricsToMapList(metrics: UserHealthMetrics): List<MutableMap<String, Any>> {
        return listOf(
            mutableMapOf("recordType" to "Weight", "value" to metrics.weight, "unit" to "kg", "timestamp" to metrics.entryDate),
            mutableMapOf("recordType" to "Height", "value" to metrics.height, "unit" to "cm", "timestamp" to metrics.entryDate),
            mutableMapOf("recordType" to "BMI", "value" to metrics.bmi, "unit" to "index", "timestamp" to metrics.entryDate),
            mutableMapOf("recordType" to "Waist Circumference", "value" to metrics.waist, "unit" to "cm", "timestamp" to metrics.entryDate),
            mutableMapOf("recordType" to "Systolic Blood Pressure", "value" to metrics.systolicBP, "unit" to "mmHg", "timestamp" to metrics.entryDate),
            mutableMapOf("recordType" to "Diastolic Blood Pressure", "value" to metrics.diastolicBP, "unit" to "mmHg", "timestamp" to metrics.entryDate)
        )
    }

    fun saveMetrics(metrics: UserHealthMetrics) {
        val db = FirebaseFirestore.getInstance()
        val mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser
        val metricsList = convertMetricsToMapList(metrics)
        if (currentUser != null) {
            currentUser.uid
            val batch: WriteBatch = db.batch()

            // Add each map to the batch
            metricsList.forEach { healthMetric ->
                val healthRecordRef = db.collection("users").document(currentUser.uid)
                    .collection("HealthRecords").document() // Creates a new document reference
                // Add to batch
                batch.set(healthRecordRef, healthMetric)
            }

            // Commit the batch
            batch.commit()
                .addOnSuccessListener {
                    Log.d("Firestore Batch", "Batch write succeeded.")
                }
                .addOnFailureListener { e ->
                    Log.w("Firestore Batch", "Batch write failed.", e)
                }

        } else {
            // Handle case where no user is logged in
        }
    }

    Column {
        val sampleUserHealthMetrics = UserHealthMetrics(
            entryDate = Date(),
            weight = 60F,
            height = 170F,
            bmi = 20F,
            waist = 105F,
            systolicBP = 120F,
            diastolicBP = 80F,
        )
        // Nested NavHost
        NavHost(navController = subNavController, startDestination = Routes.HealthMetrics.value) {
            composable(Routes.HealthMetrics.value) {
                UserHealthDashboard(stepsTaken = 5500, actualExerciseFreq = 2,
                    actualExerciseTime = 30, userHealthMetricsNewest = sampleUserHealthMetrics, subNavController)
            }
            composable("HealthMetricsSettingsScreen") {
                HealthMetricsSettingsScreen(
                    userHealthMetrics = sampleUserHealthMetrics,
                    onSaveMetrics = { metrics -> saveMetrics(metrics) },
                    subNavController
                )
            }
//            composable("ExerciseGoalSettingsScreen") {
//                ExerciseGoalSettingsScreen(
//                    userHealthMetrics = sampleUserHealthMetrics,
//                    onSaveMetrics = {},
//                    subNavController
//                )
//            }
//            composable("StepsGoalSettingsScreen") {
//                StepsGoalSettingsScreen(
//                    userHealthMetrics = sampleUserHealthMetrics,
//                    onSaveMetrics = {},
//                    subNavController
//                )
//            }
        }
    }
}