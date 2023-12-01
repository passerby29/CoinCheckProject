package dev.passerby.cryptoxmlproject.callbacks

import androidx.recyclerview.widget.DiffUtil
import dev.passerby.domain.models.CurrencyModel
import dev.passerby.domain.models.LanguageModel

class CurrencyDiffCallback : DiffUtil.ItemCallback<CurrencyModel>() {
    override fun areItemsTheSame(oldItem: CurrencyModel, newItem: CurrencyModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CurrencyModel, newItem: CurrencyModel): Boolean {
        return oldItem == newItem
    }
}