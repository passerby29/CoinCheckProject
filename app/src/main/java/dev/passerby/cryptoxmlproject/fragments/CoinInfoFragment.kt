package dev.passerby.cryptoxmlproject.fragments

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.TextView
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dev.passerby.cryptoxmlproject.R
import dev.passerby.cryptoxmlproject.databinding.FragmentCoinInfoBinding
import dev.passerby.cryptoxmlproject.factories.CoinInfoViewModelFactory
import dev.passerby.cryptoxmlproject.viewmodels.CoinInfoViewModel
import dev.passerby.domain.models.FavoriteModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.absoluteValue
import kotlin.math.roundToInt


class CoinInfoFragment : Fragment() {

    private var _binding: FragmentCoinInfoBinding? = null
    private val binding: FragmentCoinInfoBinding
        get() = _binding ?: throw RuntimeException("FragmentCoinInfoBinding is null")

    private val args by navArgs<CoinInfoFragmentArgs>()

    private val viewModelFactory by lazy {
        CoinInfoViewModelFactory(requireActivity().application, args.coinId)
    }
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[CoinInfoViewModel::class.java]
    }

    private val prices = mutableListOf<Pair<String, Float>>()
    private var chartList = listOf<Double>()
    private var collapsedChartList = listOf<Double>()
    private var startX = 0f
    private var startY = 0f
    private var delay = 1000L
    private var dpHeight = 0f
    private lateinit var displayMetrics: DisplayMetrics
    private lateinit var favoriteModel: FavoriteModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoinInfoBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayMetrics = requireContext().resources.displayMetrics
        dpHeight = displayMetrics.heightPixels / displayMetrics.density
        observeViewModel()
        initButtons()
    }

    private fun initButtons() {
        binding.coinInfoBackButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        binding.coinInfoFavButton.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                if (viewModel.isCoinAddedToFav()) {
                    viewModel.removeCoinFromFav(favoriteModel.id)
                    binding.coinInfoFavButton.setImageResource(R.drawable.ic_favorite_border)
                } else {
                    viewModel.addCoinToFav(favoriteModel)
                    binding.coinInfoFavButton.setImageResource(R.drawable.ic_favorite_filled)
                }
            }
        }

        for (i in 0 until binding.materialButtonToggleGroup.childCount) {
            binding.materialButtonToggleGroup.getChildAt(i).setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    viewModel.loadCoinHistory(PERIOD_LIST[i])?.observe(viewLifecycleOwner) {
                        chartList = it.prices
                        initChart(chartList, true)
                    }
                }
            }
        }

        binding.coinInfoShowAllButton.setOnClickListener {
            expandChart()
        }

        binding.coinInfoCollapseButton.setOnClickListener {
            collapseChart()
        }
    }

    private fun expandChart() {
        with(binding) {
            materialButtonToggleGroup.visibility = View.VISIBLE
            coinInfoCollapsedChartView.visibility = View.INVISIBLE
            val currentWidth = dpHeight * 0.5f
            val newWidth = dpHeight * 0.9f

            slideView(collapsedContainer, currentWidth, newWidth)
            slideView(coinInfoCollapsedChartView, 104f, dpHeight * 0.75f)

            collapsedLinearLayout.animate()
                .x(32f)
                .y(32f)
                .withStartAction {
                    startTextSizeAnimation(coinInfoPriceTextView, 36f, 16f)
                    startTextSizeAnimation(coinInfoChangeTextView, 16f, 12f)
                    coinInfoCollapsedLogoContainer.visibility = View.VISIBLE
                }.setDuration(delay).start()


            initChart(chartList, true)

            startX =
                collapsedContainer.x + collapsedContainer.width / 2 - collapsedLinearLayout.width / 2
            startY = 32f

            coinInfoPriceChangeContainer.visibility = View.GONE
            coinInfoCollapseButton.visibility = View.VISIBLE
            coinInfoShowAllButton.visibility = View.GONE
        }
    }


    private fun collapseChart() {
        with(binding) {
            materialButtonToggleGroup.visibility = View.GONE
            coinInfoCollapsedChartView.visibility = View.INVISIBLE

            val currentWidth = dpHeight * 0.9f
            val newWidth = dpHeight * 0.5f

            slideView(collapsedContainer, currentWidth, newWidth)
            slideView(coinInfoCollapsedChartView, dpHeight * 0.75f, 104f)

            collapsedLinearLayout.animate().x(startX).y(startY).withStartAction {
                startTextSizeAnimation(coinInfoPriceTextView, 16f, 36f)
                startTextSizeAnimation(coinInfoChangeTextView, 12f, 16f)
                coinInfoCollapsedLogoContainer.visibility = View.GONE
            }.setDuration(delay).start()

            initChart(collapsedChartList, false)


            coinInfoPriceChangeContainer.visibility = View.VISIBLE
            coinInfoShowAllButton.visibility = View.VISIBLE
            coinInfoCollapseButton.visibility = View.GONE
        }
    }

    private fun observeViewModel() {
        viewModel.coinInfo.observe(viewLifecycleOwner) { coin ->
            favoriteModel = FavoriteModel(
                coin.icon,
                coin.id,
                coin.name,
                coin.price,
                coin.priceChange1h,
                coin.rank,
                coin.symbol
            )

            val priceChange = roundDouble(coin.price / 100 * coin.priceChange1h).toBigDecimal()
            with(binding) {
                Glide.with(requireContext()).load(coin.icon).into(coinInfoLogoImageView)
                Glide.with(requireContext()).load(coin.icon).into(coinInfoCollapsedLogoImageView)
                coinInfoNameTextView.text = coin.name
                coinInfoCollapsedNameTextView.text = coin.name
                coinInfoSymbolTextView.text = coin.symbol
                coinInfoPriceTextView.text = String.format(
                    requireContext().getString(R.string.price_placeholder),
                    coin.price
                )
                coinInfoChangeTextView.apply {
                    text = String.format(
                        context.getString(
                            if (coin.priceChange1h < 0) {
                                R.string.price_change_placeholder_fav_minus
                            } else {
                                R.string.price_change_placeholder_fav_plus
                            }
                        ),
                        priceChange.abs(),
                        coin.priceChange1h.absoluteValue
                    )
                    setTextColor(
                        ContextCompat.getColor(
                            context,
                            if (coin.priceChange1h < 0) {
                                R.color.minus_color
                            } else {
                                R.color.plus_color
                            }
                        )
                    )
                }
            }
        }
        viewModel.coinHistory.observe(viewLifecycleOwner) { coinHistory ->
            initChart(coinHistory.prices, false)
            collapsedChartList = coinHistory.prices
            if (coinHistory.prices.isNotEmpty()) {
                binding.coinInfoMaxTextView.text = String.format(
                    requireContext().getString(R.string.today_max_placeholder),
                    coinHistory.prices.max()
                )
                binding.coinInfoMinTextView.text = String.format(
                    requireContext().getString(R.string.today_min_placeholder),
                    coinHistory.prices.min()
                )
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            if (viewModel.isCoinAddedToFav()) {
                binding.coinInfoFavButton.setImageResource(R.drawable.ic_favorite_filled)
            } else {
                binding.coinInfoFavButton.setImageResource(R.drawable.ic_favorite_border)
            }
        }
    }

    private fun initChart(list: List<Double>, isExpanded: Boolean) {
        if (list.isEmpty()) {
            return
        }
        if (isExpanded) {
            prices.clear()
            for (i in list.indices) {
                prices.add("index$i" to list[i].toFloat())
            }
        } else {
            prices.clear()
            for (i in 0 until 21) {
                prices.add("index$i" to list[14 * i].toFloat())
            }
            prices.add("index22" to list.last().toFloat())
        }
        binding.coinInfoCollapsedChartView.apply {
            gradientFillColors = intArrayOf(
                ContextCompat.getColor(requireContext(), R.color.button_background),
                Color.TRANSPARENT
            )
            animate(prices)
        }
    }

    private fun slideView(view: View, currentWidth: Float, newWidth: Float) {
        val slideAnimator = ValueAnimator.ofFloat(currentWidth, newWidth).setDuration(delay)

        slideAnimator.addUpdateListener { animation1: ValueAnimator ->
            val value = animation1.animatedValue as Float
            view.layoutParams.height =
                (value * Resources.getSystem().displayMetrics.density).roundToInt()
            view.requestLayout()
        }
        val animationSet = AnimatorSet()
        animationSet.interpolator = AccelerateDecelerateInterpolator()
        animationSet.play(slideAnimator)
        animationSet.doOnEnd {
            binding.coinInfoCollapsedChartView.visibility = View.VISIBLE
            binding.coinInfoCollapsedChartView.animate(prices)
        }
        animationSet.start()
    }

    private fun startTextSizeAnimation(textView: TextView, currentSize: Float, newSize: Float) {
        ObjectAnimator.ofFloat(
            textView, "textSize", currentSize, newSize
        ).setDuration(delay).start()
    }

    private fun roundDouble(double: Double): Double {
        val locale = Locale("en", "UK")
        val pattern = if (double >= 10 || double <= -10) {
            DECIMAL_FORMAT_PATTERN_TWO
        } else {
            DECIMAL_FORMAT_PATTERN_FOUR
        }
        val decimalFormat = NumberFormat.getNumberInstance(locale) as DecimalFormat
        decimalFormat.applyPattern(pattern)

        return decimalFormat.format(double).toDouble()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private val PERIOD_LIST = listOf(
            "24h",
            "1w",
            "1m",
            "3m",
            "6m",
            "1y",
            "all",
        )

        private const val DECIMAL_FORMAT_PATTERN_FOUR = "###.####"
        private const val DECIMAL_FORMAT_PATTERN_TWO = "###.##"
    }
}