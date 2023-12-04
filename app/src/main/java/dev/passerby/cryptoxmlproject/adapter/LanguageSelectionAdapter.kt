package dev.passerby.cryptoxmlproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.passerby.cryptoxmlproject.callbacks.LanguageDiffCallback
import dev.passerby.cryptoxmlproject.databinding.ItemLanguageSelectionBinding
import dev.passerby.cryptoxmlproject.viewholders.LanguageViewHolder
import dev.passerby.domain.models.LanguageModel

class LanguageSelectionAdapter(private var singleSelectionPosition: Int) :
    ListAdapter<LanguageModel, LanguageViewHolder>(LanguageDiffCallback()) {

    var onLanguageClickListener: ((LanguageModel) -> Unit)? = null

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
            languageRadioButton.isChecked = singleSelectionPosition == position
            root.setOnClickListener {
                onLanguageClickListener!!.invoke(item)
                setSingleSelection(position)
            }
        }
    }

    private fun setSingleSelection(adapterPosition: Int){
        if (adapterPosition == RecyclerView.NO_POSITION) return

        notifyItemChanged(singleSelectionPosition)
        singleSelectionPosition = adapterPosition
        notifyItemChanged(singleSelectionPosition)
    }
}