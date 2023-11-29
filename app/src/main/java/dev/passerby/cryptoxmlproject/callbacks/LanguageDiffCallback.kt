package dev.passerby.cryptoxmlproject.callbacks

import androidx.recyclerview.widget.DiffUtil
import dev.passerby.domain.models.LanguageModel

class LanguageDiffCallback : DiffUtil.ItemCallback<LanguageModel>() {
    override fun areItemsTheSame(oldItem: LanguageModel, newItem: LanguageModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: LanguageModel, newItem: LanguageModel): Boolean {
        return oldItem == newItem
    }
}