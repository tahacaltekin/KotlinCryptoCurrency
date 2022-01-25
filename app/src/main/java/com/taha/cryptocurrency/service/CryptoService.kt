package com.taha.cryptocurrency.service

import com.google.gson.GsonBuilder
import com.taha.cryptocurrency.model.CryptoModelsItem
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CryptoService {
    private val BASE_URL = "https://api.nomics.com/v1/"
    var gson = GsonBuilder()
        .setLenient()
        .create()

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build().create(CryptoAPI::class.java)

    fun getData() : Observable<List<CryptoModelsItem>> {
        return retrofit.getData()
    }
}