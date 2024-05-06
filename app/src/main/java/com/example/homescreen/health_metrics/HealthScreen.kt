package com.example.homescreen.health_metrics

import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.homescreen.Routes
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.WriteBatch
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
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

    var userHealthMetrics by remember { mutableStateOf<UserHealthMetrics?>(null) }

    // Helper function to fetch latest health metrics
    fun fetchHealthMetrics() {
        CoroutineScope(Dispatchers.IO).launch {
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return@launch
            val db = Firebase.firestore
            val userDoc = db.collection("users").document(userId)

            // Assume each metric is stored as a separate document in a sub-collection
            val healthData = userDoc.collection("HealthRecords")
            val metrics = UserHealthMetrics(Date(), 0f, 0f, 0f, 0f, 0f)

            // Retrieve each metric
            listOf("Weight", "Height", "Waist Circumference", "Systolic Blood Pressure", "Diastolic Blood Pressure").forEach { type ->
                healthData.whereEqualTo("recordType", type)
                    .orderBy("timestamp", Query.Direction.DESCENDING)
                    .limit(1)
                    .get()
                    .await()
                    .documents.firstOrNull()?.let { doc ->
                        val value = doc.getDouble("value") ?: return@let
                        when (type) {
                            "Weight" -> metrics.weight = value.toFloat()
                            "Height" -> metrics.height = value.toFloat()
                            "Waist Circumference" -> metrics.waist = value.toFloat()
                            "Systolic Blood Pressure" -> metrics.systolicBP = value.toFloat()
                            "Diastolic Blood Pressure" -> metrics.diastolicBP = value.toFloat()
                        }
                    }
            }

            // Update state
            userHealthMetrics = metrics
        }
    }

    // Fetch metrics when Composable enters composition
    LaunchedEffect(true) {
        fetchHealthMetrics()
    }

    Column {
        // Nested NavHost
        NavHost(navController = subNavController, startDestination = Routes.HealthMetrics.value) {
            composable(Routes.HealthMetrics.value) {
                userHealthMetrics?.let { it1 ->
                    UserHealthDashboard(stepsTaken = 5500, actualExerciseFreq = 2,
                        actualExerciseTime = 30,
                        userHealthMetricsNewest = it1,
                        subNavController)
                }
            }
            composable("HealthMetricsSettingsScreen") {
                userHealthMetrics?.let { it1 ->
                    HealthMetricsSettingsScreen(
                        userHealthMetrics = it1,
                        onSaveMetrics = { metrics ->
                            saveMetrics(metrics)
                            subNavController.popBackStack()
                            fetchHealthMetrics()
                                        },
                        subNavController
                    )
                }
            }
        }
    }
}