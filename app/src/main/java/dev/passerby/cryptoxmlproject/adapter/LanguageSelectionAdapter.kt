package dev.passerby.cryptoxmlproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import dev.passerby.cryptoxmlproject.callbacks.LanguageDiffCallback
import dev.passerby.cryptoxmlproject.databinding.ItemLanguageSelectionBinding
import dev.passerby.cryptoxmlproject.viewholders.LanguageViewHolder
import dev.passerby.domain.models.LanguageModel

class LanguageSelectionAdapter(private val context: Context) :
    ListAdapter<LanguageModel, LanguageViewHolder>(LanguageDiffCallback()) {

    var onPredictionItemCLickListener: ((LanguageModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        val itemView = ItemLanguageSelectionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LanguageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        val item = getItem(position)
        val binding = holder.binding
        with(binding) {
            coinNameTextView.text = item.languageName
            coinSymbolTextView.text = item.nativeName
            languageRadioButton.isChecked = item.isChecked
        }
    }
}