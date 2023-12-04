package dev.passerby.cryptoxmlproject.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import dev.passerby.cryptoxmlproject.R
import dev.passerby.cryptoxmlproject.databinding.FragmentSettingsBinding
import dev.passerby.cryptoxmlproject.viewmodels.SettingsViewModel

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding
        get() = _binding ?: throw RuntimeException("FragmentSettingsBinding is null")

    private val viewModel: SettingsViewModel by navGraphViewModels(R.id.main_navigation)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeViewModel()
    }

    private fun initViews() {
        with(binding) {
            settingsToolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            settingsGithubButton.setOnClickListener {
                openBrowser("https://github.com/passerby29")
            }
            settingsDribbleButton.setOnClickListener {
                openBrowser("https://dribbble.com/shots/23174366-Cryptocurrency-Application")
            }
            settingsLanguageContainer.setOnClickListener {
                findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToLanguageDialog())
            }
            settingsCurrencyContainer.setOnClickListener {
                findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToCurrencyDialog())
            }
        }
    }

    private fun observeViewModel() {
        with(viewModel) {
            currentCurrency.observe(viewLifecycleOwner) {
                binding.settingsCurrencyCodeTextView.text = it.currencyCode
            }
        }
    }

    private fun openBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}