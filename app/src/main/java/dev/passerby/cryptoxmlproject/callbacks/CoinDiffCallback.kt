package dev.passerby.cryptoxmlproject.callbacks

import androidx.recyclerview.widget.DiffUtil
import dev.passerby.domain.models.CoinModel

class CoinDiffCallback : DiffUtil.ItemCallback<CoinModel>() {
    override fun areItemsTheSame(oldItem: CoinModel, newItem: CoinModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CoinModel, newItem: CoinModel): Boolean {
        return oldItem == newItem
    }
}