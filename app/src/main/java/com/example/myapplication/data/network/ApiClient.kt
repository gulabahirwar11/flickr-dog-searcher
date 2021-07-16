package com.example.myapplication.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {
    private const val BASE_URL = "https://www.flickr.com/"
    private  var INSTANCE : ApiInterface?= null


    fun getInstance() : ApiInterface{
        if (INSTANCE == null) {
            createRetrofitService()
        }
        return INSTANCE!! // instance wont be null
    }

    private fun createRetrofitService(){
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
         val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                 .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
        INSTANCE =  retrofit.create(ApiInterface::class.java)
    }
}