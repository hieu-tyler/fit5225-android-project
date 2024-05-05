package com.example.homescreen.nutrition

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object FoodRetrofit {
    private val BASE_URL = "https://api.api-ninjas.com/"
    val retrofitService: FoodFactService by lazy {
        Log.i("RetrofitObject", "called")
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(FoodFactService::class.java)
    }
}