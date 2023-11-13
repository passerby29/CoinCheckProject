package dev.passerby.cryptoxmlproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.R.id.design_bottom_sheet
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.passerby.cryptoxmlproject.adapter.CoinsAdapter
import dev.passerby.cryptoxmlproject.databinding.FragmentBottomSheetBinding
import dev.passerby.cryptoxmlproject.viewmodels.HomeViewModel

class BottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetBinding? = null
    private val binding: FragmentBottomSheetBinding
        get() = _binding ?: throw RuntimeException("FragmentBottomSheetBinding is null")

    private val viewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }
    private var coinsAdapter: CoinsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        coinsAdapter = CoinsAdapter(requireContext())

        setOnCoinClickListener()

        binding.homeBigRecyclerView.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = coinsAdapter
        }

        viewModel.coinsList.observe(viewLifecycleOwner) {
            coinsAdapter!!.submitList(it)
        }


        val density = requireContext().resources.displayMetrics.density
        dialog?.let {
            val bottomSheet = it.findViewById<View>(design_bottom_sheet) as FrameLayout
            val behavior = BottomSheetBehavior.from(bottomSheet)
            val collapsedHeight = resources.displayMetrics.heightPixels / 2
            behavior.peekHeight = (collapsedHeight * density).toInt()
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
            behavior.maxHeight = resources.displayMetrics.heightPixels - 64

            behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                }
            })
        }
    }

    private fun setOnCoinClickListener() {
        coinsAdapter?.onCoinItemCLickListener = {
            findNavController().navigate(
                BottomSheetFragmentDirections.actionBottomSheetFragmentToCoinInfoFragment(it.rank, it.id)
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}