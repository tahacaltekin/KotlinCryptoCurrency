package com.taha.cryptocurrency.service

import com.taha.cryptocurrency.model.CryptoModelsItem
import io.reactivex.Observable
import retrofit2.http.GET

interface CryptoAPI {
    @GET("currencies/ticker?key=82749821e599f7cfeb7f3321ce92e2119349fdf1&ids=BTC,ADA,AVAX,CHZ,DOGE,ETH,MANA,DENT,NEO,SAND,SHIB,SOL,TRX,UNI,USDT,XLM,XRP,&attributes=id,currency,symbol,name,logo_url,price")
    fun getData() : Observable<ArrayList<CryptoModelsItem>>


}