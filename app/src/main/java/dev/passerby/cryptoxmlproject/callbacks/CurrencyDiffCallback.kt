package dev.passerby.cryptoxmlproject.callbacks

import androidx.recyclerview.widget.DiffUtil
import dev.passerby.domain.models.CurrencyModel

class CurrencyDiffCallback : DiffUtil.ItemCallback<CurrencyModel>() {
    override fun areItemsTheSame(oldItem: CurrencyModel, newItem: CurrencyModel): Boolean {
        return oldItem.isChecked == newItem.isChecked
    }

    override fun areContentsTheSame(oldItem: CurrencyModel, newItem: CurrencyModel): Boolean {
        return oldItem == newItem
    }
}