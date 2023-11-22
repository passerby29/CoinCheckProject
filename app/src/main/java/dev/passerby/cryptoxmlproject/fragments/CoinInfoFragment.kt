package dev.passerby.cryptoxmlproject.fragments

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.animation.doOnEnd
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
import kotlin.math.roundToInt

class CoinInfoFragment : Fragment() {

    private var _binding: FragmentCoinInfoBinding? = null
    private val binding: FragmentCoinInfoBinding
        get() = _binding ?: throw RuntimeException("FragmentCoinInfoBinding is null")

    private val args by navArgs<CoinInfoFragmentArgs>()

    private val viewModelFactory by lazy {
        CoinInfoViewModelFactory(requireActivity().application, args.rank, args.coinId)
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
    private lateinit var favoriteModel: FavoriteModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoinInfoBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        initButtons()
        binding.coinInfoCollapsedChartView.gradientFillColors = intArrayOf(
            Color.parseColor("#454CEE"),
            Color.TRANSPARENT
        )
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
                    binding.coinInfoFavButton.setImageResource(R.drawable.ic_search)
                }
            }
        }

        for (i in 0 until binding.materialButtonToggleGroup.childCount) {
            binding.materialButtonToggleGroup.getChildAt(i).setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    viewModel.loadCoinHistory(PERIOD_LIST[i])?.observe(viewLifecycleOwner){
                        chartList = it.prices
                        initChart(chartList, true)
                    }
                }
            }
        }

        val displayMetrics = requireContext().resources.displayMetrics
        val dpHeight = displayMetrics.heightPixels / displayMetrics.density
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density

        binding.coinInfoShowAllButton.setOnClickListener {
            binding.materialButtonToggleGroup.visibility = View.VISIBLE
            binding.coinInfoCollapsedChartView.visibility = View.INVISIBLE
            val currentWidth = dpHeight * 0.5f
            val newWidth = dpHeight * 0.9f
            slideView(binding.collapsedContainer, currentWidth, newWidth)
            binding.collapsedLinearLayout.animate()
                .x(24f)
                .y(24f)
                .withStartAction {
                    binding.coinInfoPriceTextView.textSize = 16f
                }.withEndAction {
                    binding.coinInfoCollapsedLogoContainer.visibility = View.VISIBLE
                }.setDuration(delay).start()
            slideView(binding.coinInfoCollapsedChartView, 104f, dpHeight * 0.75f)
            initChart(chartList, true)
            binding.coinInfoPriceChangeContainer.visibility = View.GONE
            startX =
                binding.collapsedContainer.x + binding.collapsedContainer.width / 2 - binding.collapsedLinearLayout.width / 2
            startY = 24f
            binding.coinInfoShowAllButton.visibility = View.GONE
            binding.coinInfoCollapseButton.visibility = View.VISIBLE
        }

        binding.coinInfoCollapseButton.setOnClickListener {
            binding.materialButtonToggleGroup.visibility = View.GONE
            binding.coinInfoCollapsedChartView.visibility = View.INVISIBLE
            slideView(binding.collapsedContainer, dpHeight * 0.9f, dpHeight * 0.5f)
            slideView(binding.coinInfoCollapsedChartView, dpHeight * 0.75f, 104f)
            initChart(collapsedChartList, false)

            binding.collapsedLinearLayout.animate()
                .x(startX)
                .y(startY).withStartAction {
                    binding.coinInfoPriceTextView.textSize = 40f
                    binding.coinInfoCollapsedLogoContainer.visibility = View.GONE
                }.setDuration(delay).start()
            binding.coinInfoPriceChangeContainer.visibility = View.VISIBLE
            binding.coinInfoShowAllButton.visibility = View.VISIBLE
            binding.coinInfoCollapseButton.visibility = View.GONE
        }
    }

    private fun expandChart() {

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
            with(binding) {
                Glide.with(requireContext()).load(coin.icon).into(coinInfoLogoImageView)
                Glide.with(requireContext()).load(coin.icon).into(coinInfoCollapsedLogoImageView)
                coinInfoNameTextView.text = coin.name
                coinInfoCollapsedNameTextView.text = coin.name
                coinInfoSymbolTextView.text = coin.symbol
                coinInfoPriceTextView.text = coin.price.toString()
                coinInfoChangeTextView.text = coin.priceChange1d.toString()
            }
        }
        viewModel.coinHistory.observe(viewLifecycleOwner) { coinHistory ->
            initChart(coinHistory.prices, false)
            collapsedChartList = coinHistory.prices
            if (coinHistory.prices.isNotEmpty()) {
                binding.coinInfoMaxTextView.text = coinHistory.prices.max().toString()
                binding.coinInfoMinTextView.text = coinHistory.prices.min().toString()
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
        binding.coinInfoCollapsedChartView.animate(prices)
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
    }
}