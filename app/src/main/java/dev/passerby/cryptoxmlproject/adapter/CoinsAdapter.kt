package dev.passerby.cryptoxmlproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import dev.passerby.cryptoxmlproject.R
import dev.passerby.cryptoxmlproject.callbacks.CoinDiffCallback
import dev.passerby.cryptoxmlproject.databinding.ItemCoinBinding
import dev.passerby.cryptoxmlproject.viewholders.CoinViewHolder
import dev.passerby.domain.models.CoinModel

class CoinsAdapter(private val context: Context, private val currencyId: Int) :
    ListAdapter<CoinModel, CoinViewHolder>(CoinDiffCallback()) {

    var onCoinItemCLickListener: ((CoinModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val itemView =
            ItemCoinBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoinViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        val item = getItem(position)
        val binding = holder.binding
        val currenciesArray = context.resources.getStringArray(R.array.price_placeholder)
        with(binding) {
            Glide.with(context).load(item.icon).into(coinLogoImageView)
            coinNameTextView.text = item.name
            coinSymbolTextView.text = item.symbol
            coinPriceTextView.text = String.format(currenciesArray[currencyId], item.price)
            coinChangeTextView.apply {
                text = String.format(
                    context.getString(R.string.price_change_placeholder_coin),
                    item.priceChange1h
                )
                setTextColor(
                    ContextCompat.getColor(
                        context,
                        if (item.priceChange1h < 0) {
                            R.color.minus_color
                        } else {
                            R.color.plus_color
                        }
                    )
                )
            }
            this.root.setOnClickListener { onCoinItemCLickListener?.invoke(item) }
        }
    }
}