package dev.passerby.cryptoxmlproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import dev.passerby.cryptoxmlproject.callbacks.CurrencyDiffCallback
import dev.passerby.cryptoxmlproject.databinding.ItemCurrencySelectionBinding
import dev.passerby.cryptoxmlproject.viewholders.CurrencyViewHolder
import dev.passerby.domain.models.CurrencyModel

class CurrencySelectionAdapter(private val context: Context) :
    ListAdapter<CurrencyModel, CurrencyViewHolder>(CurrencyDiffCallback()) {

    var onPredictionItemCLickListener: ((CurrencyModel) -> Unit)? = null

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
            Glide.with(context).load(item.imageUrl).into(currencyFlagImageView)
            coinNameTextView.text = item.currencyName
            coinSymbolTextView.text = item.currencyCode
            currencySymbolTextView.text = item.symbol
            languageRadioButton.isChecked = item.isChecked
        }
    }
}