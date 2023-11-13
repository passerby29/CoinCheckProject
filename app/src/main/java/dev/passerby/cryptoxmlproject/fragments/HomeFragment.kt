package dev.passerby.cryptoxmlproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayoutMediator
import dev.passerby.cryptoxmlproject.adapter.CoinsAdapter
import dev.passerby.cryptoxmlproject.adapter.FavoritesAdapter
import dev.passerby.cryptoxmlproject.databinding.FragmentHomeBinding
import dev.passerby.cryptoxmlproject.viewmodels.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding ?: throw RuntimeException("FragmentHomeBinding is null")

    private val viewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }

    private lateinit var coinsAdapter: CoinsAdapter
    private lateinit var favoritesAdapter: FavoritesAdapter
    private var state = BottomSheetBehavior.STATE_COLLAPSED

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
//        initBottomSheet()
        observeViewModel()
        binding.homeShowAllButton.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToBottomSheetFragment())
        }
    }

    private fun initAdapters() {
        coinsAdapter = CoinsAdapter(requireContext())
        favoritesAdapter = FavoritesAdapter(requireContext())
    }

    private fun initRecyclerView() {
        binding.homeCoinsRecyclerView.apply {
            layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.VERTICAL, false
            )
            adapter = coinsAdapter
        }
        setOnCoinClickListener()
    }

    private fun initViewPager() {
        with(binding) {
            homeFavoritesPager.adapter = favoritesAdapter
            TabLayoutMediator(
                homeFavoritesTabLayout, homeFavoritesPager
            ) { _, _ -> }.attach()
        }
    }

//    private fun initBottomSheet() {
//        val behavior = BottomSheetBehavior.from(binding.homeBottomSheetContainer)
//        behavior.apply {
//            peekHeight = (resources.displayMetrics.heightPixels * 0.55).toInt()
//            isHideable = false
//
//            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
//                override fun onStateChanged(bottomSheet: View, newState: Int) {
//                    when (newState) {
//                        BottomSheetBehavior.STATE_EXPANDED -> {
//                            viewModel.coinsList.observe(viewLifecycleOwner) {
//                                coinsAdapter.submitList(it)
//                            }
//                        }
//
//                        BottomSheetBehavior.STATE_COLLAPSED -> {
//                            viewModel.topCoinsList.observe(viewLifecycleOwner) {
//                                coinsAdapter.submitList(it)
//                            }
//                            binding.homeCoinsRecyclerView.isNestedScrollingEnabled = false
//                        }
//
//                        BottomSheetBehavior.STATE_DRAGGING -> {}
//                        BottomSheetBehavior.STATE_HIDDEN -> {}
//                        BottomSheetBehavior.STATE_SETTLING -> {}
//                        BottomSheetBehavior.STATE_HALF_EXPANDED -> {}
//                    }
//                }
//
//                override fun onSlide(bottomSheet: View, slideOffset: Float) {
//                }
//            })
//        }
//
//        binding.homeShowAllButton.setOnClickListener {
//            behavior.state = BottomSheetBehavior.STATE_EXPANDED
//        }
//    }

    private fun observeViewModel() {
        viewModel.topCoinsList.observe(viewLifecycleOwner) {
            favoritesAdapter.submitList(it)
            coinsAdapter.submitList(it)
        }
    }

    private fun setOnCoinClickListener() {
        coinsAdapter.onCoinItemCLickListener = {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToCoinInfoFragment(it.rank, it.id)
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}