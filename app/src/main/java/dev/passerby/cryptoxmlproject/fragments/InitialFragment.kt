package dev.passerby.cryptoxmlproject.fragments

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dev.passerby.cryptoxmlproject.R
import dev.passerby.cryptoxmlproject.databinding.FragmentInitialBinding


class InitialFragment : Fragment() {

    private var _binding: FragmentInitialBinding? = null
    private val binding: FragmentInitialBinding
        get() = _binding ?: throw RuntimeException("FragmentInitialBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInitialBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val window: Window = requireActivity().window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.gradient_end_color)

        val sharedPreferences =
            requireActivity().getSharedPreferences("AppPreferences", MODE_PRIVATE)

        val isFirstOpen = sharedPreferences.getBoolean("firstOpen", true)
        if (!isFirstOpen){
            findNavController().navigate(InitialFragmentDirections.actionInitialFragmentToHomeFragment())
        }
        binding.initialStartButton.setOnClickListener {
            findNavController().navigate(InitialFragmentDirections.actionInitialFragmentToHomeFragment())
            val editor = sharedPreferences.edit()
            editor.putBoolean("firstOpen", false)
            editor.apply()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}