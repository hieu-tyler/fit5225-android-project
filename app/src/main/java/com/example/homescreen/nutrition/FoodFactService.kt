package com.example.homescreen.nutrition

import retrofit2.http.GET
import retrofit2.http.Query
interface FoodFactService {
    @GET("v1/nutrition")
    suspend fun getFoodFact(
        @Query("query") foodName: String,
        @Query("X-Api-Key") apiKey: String
    ): List<FoodAPI>
}