package dev.passerby.cryptoxmlproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dev.passerby.cryptoxmlproject.databinding.FragmentCoinInfoBinding
import dev.passerby.cryptoxmlproject.factories.CoinInfoViewModelFactory
import dev.passerby.cryptoxmlproject.viewmodels.CoinInfoViewModel

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

    var prices = mutableListOf<Pair<String, Float>>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoinInfoBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.coinInfo.observe(viewLifecycleOwner) { coin ->
            with(binding) {
                Glide.with(requireContext()).load(coin.icon).into(coinInfoLogoImageView)
                coinInfoNameTextView.text = coin.name
                coinInfoSymbolTextView.text = coin.symbol
                coinInfoPriceTextView.text = coin.price.toString()
                coinInfoChangeTextView.text = coin.priceChange1d.toString()
            }
        }
        viewModel.coinHistory.observe(viewLifecycleOwner){coinHistory->
            coinHistory.prices.forEachIndexed { index, price ->
                prices.add("index$index" to price.toFloat())
            }
            binding.coinInfoCollapsedChartView.animate(prices.toList())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}