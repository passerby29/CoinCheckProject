package dev.passerby.cryptoxmlproject.fragments

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.inputmethod.InputMethodManager
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import dev.passerby.cryptoxmlproject.adapter.CoinPredictionsAdapter
import dev.passerby.cryptoxmlproject.adapter.CoinsAdapter
import dev.passerby.cryptoxmlproject.adapter.FavoritesAdapter
import dev.passerby.cryptoxmlproject.databinding.FragmentHomeBinding
import dev.passerby.cryptoxmlproject.viewmodels.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding ?: throw RuntimeException("FragmentHomeBinding is null")

    private val viewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }

    private lateinit var coinsAdapter: CoinsAdapter
    private lateinit var coinPredictionsAdapter: CoinPredictionsAdapter
    private lateinit var favoritesAdapter: FavoritesAdapter
    private var filterString = ""
    private var isSearchOpen = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapters()
        initRecyclerView()
        initViewPager()
        initBottomSheet()
        initSearch()
        observeViewModel()

        binding.homeSettingsButton.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToSettingsFragment()
            )
        }

        binding.homeFavoritesPlaceholderContainer.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToBottomSheetFragment()
            )
        }
    }

    private fun initSearch() {
        binding.homeSearchButton.setOnClickListener {
            val displayMetrics = requireContext().resources.displayMetrics
            val dpWidth = displayMetrics.widthPixels / displayMetrics.density
            isSearchOpen = if (isSearchOpen) {
                slideView(binding.homeDateTextView, 1f, dpWidth * 0.65f)
                slideView(binding.homeSearchEditText, dpWidth * 0.65f, 1f)
                false
            } else {
                slideView(binding.homeDateTextView, dpWidth * 0.65f, 1f)
                slideView(binding.homeSearchEditText, 1f, dpWidth * 0.65f)
                true
            }
            binding.homeSearchPredictionsContainer.visibility = if (isSearchOpen){
                View.VISIBLE
            } else {
                View.GONE
            }
            if (!isSearchOpen){
                hideSoftKeyboard(requireActivity(), binding.homeSearchEditText)
            } else {
                openSoftKeyboard(requireActivity(), binding.homeSearchEditText)
            }
        }

        binding.homeSearchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(filter: CharSequence?, p1: Int, p2: Int, p3: Int) {
                CoroutineScope(Dispatchers.Main).launch {
                    delay(200)
                    if (filter?.isNotEmpty() == true) {
                        filterString =
                            StringBuilder().append("%").append(filter.trim()).append("%").toString()
                        viewModel.searchCoins(filterString).observe(viewLifecycleOwner) {
                            coinPredictionsAdapter.submitList(it)
                        }
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }

    private fun hideSoftKeyboard(activity: Activity, view: View) {
        view.clearFocus()
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.applicationWindowToken, 0)
    }

    private fun openSoftKeyboard(activity: Activity, view: View) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, 0)
    }

    private fun initAdapters() {
        coinsAdapter = CoinsAdapter(requireContext())
        coinPredictionsAdapter = CoinPredictionsAdapter(requireContext())
        favoritesAdapter = FavoritesAdapter(requireContext())
    }

    private fun initRecyclerView() {
        binding.homeCoinsRecyclerView.apply {
            layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.VERTICAL, false
            )
            adapter = coinsAdapter
        }

        binding.homeSearchPredictionsRecyclerView.apply {
            layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.VERTICAL, false
            )
            adapter = coinPredictionsAdapter
        }

        setOnCoinClickListener()
        setOnPredictionClickListener()
        setOnFavoriteClickListener()
    }

    private fun initViewPager() {
        with(binding) {
            homeFavoritesPager.adapter = favoritesAdapter
            TabLayoutMediator(
                homeFavoritesTabLayout, homeFavoritesPager
            ) { _, _ -> }.attach()
        }
    }

    private fun initBottomSheet() {
        binding.homeShowAllButton.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToBottomSheetFragment())
        }
    }

    private fun observeViewModel() {
        viewModel.topCoinsList.observe(viewLifecycleOwner) {
            coinsAdapter.submitList(it)
        }
        viewModel.currentDate.observe(viewLifecycleOwner) {
            binding.homeDateTextView.text = it
        }
        viewModel.favCoinsList.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.homeFavoritesPlaceholderContainer.visibility = View.VISIBLE
                binding.homeFavoritesPager.visibility = View.INVISIBLE
                binding.homeFavoritesTabLayout.visibility = View.INVISIBLE
            } else {
                binding.homeFavoritesPlaceholderContainer.visibility = View.GONE
                binding.homeFavoritesPager.visibility = View.VISIBLE
                binding.homeFavoritesTabLayout.visibility = View.VISIBLE
            }
            favoritesAdapter.submitList(it)
        }
    }

    private fun setOnCoinClickListener() {
        coinsAdapter.onCoinItemCLickListener = {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToCoinInfoFragment(it.id)
            )
        }
    }

    private fun setOnPredictionClickListener() {
        coinPredictionsAdapter.onPredictionItemCLickListener = {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToCoinInfoFragment(it.id)
            )
        }
    }

    private fun setOnFavoriteClickListener() {
        favoritesAdapter.onFavItemCLickListener = {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToCoinInfoFragment(it.id)
            )
        }
    }

    private fun slideView(view: View, currentWidth: Float, newWidth: Float) {
        val slideAnimator = ValueAnimator.ofFloat(currentWidth, newWidth).setDuration(500)

        slideAnimator.addUpdateListener { animation1: ValueAnimator ->
            val value = animation1.animatedValue as Float
            view.layoutParams.width =
                (value * Resources.getSystem().displayMetrics.density).roundToInt()
            view.requestLayout()
        }
        val animationSet = AnimatorSet()
        animationSet.interpolator = AccelerateDecelerateInterpolator()
        animationSet.play(slideAnimator)
        animationSet.doOnStart {
            if (isSearchOpen){
                binding.homeDateTextView.visibility = View.VISIBLE
            } else {
                binding.homeSearchEditText.visibility = View.VISIBLE
            }
        }
        animationSet.doOnEnd {
            if (isSearchOpen){
                binding.homeDateTextView.visibility = View.INVISIBLE
            } else {
                binding.homeSearchEditText.visibility = View.INVISIBLE
            }
        }
        animationSet.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}