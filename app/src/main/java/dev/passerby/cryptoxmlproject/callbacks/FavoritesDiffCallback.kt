package dev.passerby.cryptoxmlproject.callbacks

import androidx.recyclerview.widget.DiffUtil
import dev.passerby.domain.models.CoinModel
import dev.passerby.domain.models.FavoriteModel

class FavoritesDiffCallback : DiffUtil.ItemCallback<FavoriteModel>() {
    override fun areItemsTheSame(oldItem: FavoriteModel, newItem: FavoriteModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FavoriteModel, newItem: FavoriteModel): Boolean {
        return oldItem == newItem
    }
}