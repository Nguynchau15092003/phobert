package com.example.phobert

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface APIService {
    @POST("predict")
    fun predict(@Body request: TextRequest): Call<SentimentResponse>
}
