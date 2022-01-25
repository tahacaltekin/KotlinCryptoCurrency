package com.taha.cryptocurrency

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.taha.cryptocurrency.adapter.CryptoListAdapter
import com.taha.cryptocurrency.databinding.ActivityMainBinding
import com.taha.cryptocurrency.model.CryptoModelsItem
import com.taha.cryptocurrency.service.CryptoAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val BASE_URL = "https://api.nomics.com/v1/"
    private var cryptoModels : ArrayList<CryptoModelsItem>? = null
    private var cryptoListAdapter : CryptoListAdapter? = null

    private var compositeDisposable : CompositeDisposable? = null

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        compositeDisposable = CompositeDisposable()

        //Crypto Row
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.cryptoList.layoutManager = layoutManager

        loadData()
    }

    private fun loadData() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(CryptoAPI::class.java)

        compositeDisposable?.add(retrofit.getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResponse))
    }

    private fun handleResponse(cryptoList : List<CryptoModelsItem>) {
        cryptoModels = ArrayList(cryptoList)

        cryptoModels?.let {
            cryptoListAdapter = CryptoListAdapter(it)
            binding.cryptoList.adapter = cryptoListAdapter

            for (cryptoModel : CryptoModelsItem in cryptoModels!!) {
                println(cryptoModel.logo_url)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable?.clear()
    }
}
