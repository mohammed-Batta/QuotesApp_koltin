package com.albatta.mohmmed.mishal.movie

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface CallQuotes{
    @GET("api/quotes")
    fun call(): Call<List<QuotesModel>>
}