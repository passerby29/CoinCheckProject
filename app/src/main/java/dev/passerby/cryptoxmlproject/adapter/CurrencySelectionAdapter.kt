package dev.passerby.cryptoxmlproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.passerby.cryptoxmlproject.callbacks.CurrencyDiffCallback
import dev.passerby.cryptoxmlproject.databinding.ItemCurrencySelectionBinding
import dev.passerby.cryptoxmlproject.viewholders.CurrencyViewHolder
import dev.passerby.domain.models.CurrencyModel


class CurrencySelectionAdapter(private var singleSelectionPosition: Int) :
    ListAdapter<CurrencyModel, CurrencyViewHolder>(CurrencyDiffCallback()) {

    var onCurrencyClickListener: ((CurrencyModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val itemView = ItemCurrencySelectionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CurrencyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val item = getItem(position)
        val binding = holder.binding
        with(binding) {
            currencyFlagImageView.setImageResource(item.imageUrl)
            coinNameTextView.text = item.currencyName
            coinSymbolTextView.text = item.currencyCode
            currencySymbolTextView.text = item.symbol
            languageRadioButton.isChecked = singleSelectionPosition == position
            root.setOnClickListener {
                onCurrencyClickListener!!.invoke(item)
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