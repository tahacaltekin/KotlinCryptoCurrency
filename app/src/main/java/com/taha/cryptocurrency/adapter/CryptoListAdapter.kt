package com.taha.cryptocurrency.adapter
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.taha.cryptocurrency.databinding.CryptoRowBinding
import com.taha.cryptocurrency.model.CryptoModelsItem

class CryptoListAdapter(private val cryptoList : ArrayList<CryptoModelsItem>) : RecyclerView.Adapter<CryptoListAdapter.ListHolder>() {

    private val colors : Array<String> = arrayOf("#ffd4e5", "#d4ffea", "#eecbff", "#feffa3", "#dbdcff", "#a3c1ad", "#a0d6b4", "#5f9ea0")

    class ListHolder(val binding : CryptoRowBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cryptoModel : CryptoModelsItem, colors : Array<String>, position: Int) {
            itemView.setBackgroundColor(Color.parseColor(colors[position % 8]))
            binding.cryptoName.text = cryptoModel.name
            binding.cryptoPrice.text = "$${cryptoModel.price.substring(0, 8)}"
            binding.cryptoSymbol.text = cryptoModel.symbol
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        val binding = CryptoRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListHolder(binding)
    }

    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        holder.bind(cryptoList[position], colors, position)
        Picasso.get().load(cryptoList[position].logo_url).into(holder.binding.cryptoIcon)
    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }
}