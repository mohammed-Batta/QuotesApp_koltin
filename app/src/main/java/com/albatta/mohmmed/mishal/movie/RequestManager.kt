package com.albatta.mohmmed.mishal.movie

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RequestManager(context: Context) {
    var gson: Gson = GsonBuilder()
        .setLenient()
        .create()
    private val re="https://type.fit/"
        var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(re)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

   fun getAllQuotes(listener: QuotesResponseListener){
        val call=retrofit.create(CallQuotes::class.java).call()
        call.enqueue(object : Callback<List<QuotesModel>> {


            override fun onResponse(call: Call<List<QuotesModel>>, response: Response<List<QuotesModel>>) {
                if (!response.isSuccessful){
                    listener.didError(response.message())
                    return
                }
                response.body()?.let { listener.didFetch(it as ArrayList<QuotesModel>,response.message()) }
            }



            override fun onFailure(call: Call<List<QuotesModel>>, t: Throwable) {
                t.message?.let { listener.didError(t.message.toString()) }
            }


        })
    }


}