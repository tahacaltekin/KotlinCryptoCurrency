package com.taha.cryptocurrency.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmadrosid.svgloader.SvgLoader
import com.taha.cryptocurrency.adapter.CryptoListAdapter
import com.taha.cryptocurrency.databinding.ActivityMainBinding
import com.taha.cryptocurrency.viewModel.CryptoViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.crypto_row.*

class MainActivity : AppCompatActivity() {

    private var cryptoListAdapter : CryptoListAdapter? = null

    private var compositeDisposable : CompositeDisposable? = null
    private lateinit var cryptoViewModel: CryptoViewModel

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

        cryptoViewModel = ViewModelProvider(this).get(CryptoViewModel::class.java)
        cryptoViewModel.refrestData()

        observeLiveData()
    }

    private fun observeLiveData() {
        cryptoViewModel.getCryptoLiveData.observe(this, {
            it.let {
                    cryptoListAdapter = CryptoListAdapter(it)
                    binding.cryptoList.adapter = cryptoListAdapter
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable?.clear()
    }
}
