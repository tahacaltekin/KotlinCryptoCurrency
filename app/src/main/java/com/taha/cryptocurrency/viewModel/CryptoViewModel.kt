package com.taha.cryptocurrency.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.taha.cryptocurrency.model.CryptoModelsItem
import com.taha.cryptocurrency.service.CryptoService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class CryptoViewModel : ViewModel() {

    private var cryptoApiService = CryptoService()
    private var compositeDisposable = CompositeDisposable()
    val getCryptoLiveData = MutableLiveData<ArrayList<CryptoModelsItem>>()

    fun refrestData() {
        getCryptoData()
    }

    fun getCryptoData() {
        compositeDisposable.add(
            cryptoApiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<ArrayList<CryptoModelsItem>>() {
                    override fun onNext(t: ArrayList<CryptoModelsItem>) {
                        getCryptoLiveData.value = t
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }

                    override fun onComplete() {
                        getCryptoLiveData.value
                    }

                })
        )
    }
}