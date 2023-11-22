package dev.passerby.cryptoxmlproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import dev.passerby.cryptoxmlproject.R
import dev.passerby.cryptoxmlproject.callbacks.FavoritesDiffCallback
import dev.passerby.cryptoxmlproject.databinding.ItemFavoriteCoinBinding
import dev.passerby.cryptoxmlproject.viewholders.FavoritesViewHolder
import dev.passerby.domain.models.FavoriteModel
import java.math.RoundingMode
import java.text.DecimalFormat

class FavoritesAdapter(private val context: Context) :
    ListAdapter<FavoriteModel, FavoritesViewHolder>(FavoritesDiffCallback()) {

    var onFavItemCLickListener: ((FavoriteModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = ItemFavoriteCoinBinding.inflate(layoutInflater, parent, false)
        return FavoritesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val item = getItem(position)
        val binding = holder.binding
        with(binding) {
            val price = roundDouble(item.price)
            val priceChange = roundDouble(item.price / 100 * item.priceChange1h)
            favoritePriceTextView.text =
                String.format(context.getString(R.string.price_placeholder), price)
            Glide.with(context).load(item.icon).into(favoriteLogoImageView)
            favoriteNameTexView.text = item.name
            favoriteSymbolTexView.text = item.symbol
            favoriteChangeTextView.text = String.format(
                context.getString(R.string.price_change_placeholder),
                priceChange,
                item.priceChange1h
            )
            favoriteMoreButton.setOnClickListener { onFavItemCLickListener?.invoke(item) }
        }
    }

    private fun roundDouble(double: Double): String {
        val decimalFormat = DecimalFormat(
            if (double >= 10 || double <= -10) {
                DECIMAL_FORMAT_PATTERN_TWO
            } else {
                DECIMAL_FORMAT_PATTERN_FOUR
            }
        )
        decimalFormat.roundingMode = RoundingMode.DOWN
        return decimalFormat.format(double)
    }

    companion object {
        private const val DECIMAL_FORMAT_PATTERN_FOUR = "#.####"
        private const val DECIMAL_FORMAT_PATTERN_TWO = "#.##"
    }
}