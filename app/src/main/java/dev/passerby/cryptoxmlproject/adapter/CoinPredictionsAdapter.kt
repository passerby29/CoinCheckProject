package dev.passerby.cryptoxmlproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import dev.passerby.cryptoxmlproject.callbacks.CoinDiffCallback
import dev.passerby.cryptoxmlproject.databinding.ItemCoinPredictionBinding
import dev.passerby.cryptoxmlproject.viewholders.CoinPredictionViewHolder
import dev.passerby.domain.models.CoinModel

class CoinPredictionsAdapter(private val context: Context) :
    ListAdapter<CoinModel, CoinPredictionViewHolder>(CoinDiffCallback()) {

    var onPredictionItemCLickListener: ((CoinModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinPredictionViewHolder {
        val itemView = ItemCoinPredictionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CoinPredictionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CoinPredictionViewHolder, position: Int) {
        val item = getItem(position)
        val binding = holder.binding
        with(binding) {
            Glide.with(context).load(item.icon).into(coinLogoImageView)
            coinNameTextView.text = item.name
            this.root.setOnClickListener { onPredictionItemCLickListener?.invoke(item) }
        }
    }
}