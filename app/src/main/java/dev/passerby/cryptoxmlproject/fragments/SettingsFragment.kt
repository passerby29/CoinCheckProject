package dev.passerby.cryptoxmlproject.fragments

import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import dev.passerby.cryptoxmlproject.R
import dev.passerby.cryptoxmlproject.databinding.FragmentSettingsBinding
import dev.passerby.cryptoxmlproject.viewmodels.SettingsViewModel
import java.util.Locale

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding
        get() = _binding ?: throw RuntimeException("FragmentSettingsBinding is null")

    private val viewModel: SettingsViewModel by navGraphViewModels(R.id.main_navigation)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val preferences =
            requireActivity().getSharedPreferences("AppPreferences", AppCompatActivity.MODE_PRIVATE)

        val lang = when (preferences.getInt("langId", 0)) {
            1 -> "ru"
            2 -> "uk"
            3 -> "es"
            4 -> "kk"
            else -> "en"
        }

        val myLocale = Locale(lang)
        val res: Resources = resources
        val dm: DisplayMetrics = res.displayMetrics
        val conf: Configuration = res.configuration
        conf.setLocale(myLocale)
        res.updateConfiguration(conf, dm)

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
            settingsDarkThemeImageView.setOnClickListener {
                viewModel.acceptTheme(0)
                requireActivity().recreate()
            }
            settingsLightThemeImageView.setOnClickListener {
                viewModel.acceptTheme(1)
                requireActivity().recreate()
            }
        }
    }

    private fun observeViewModel() {
        with(viewModel) {
            currentCurrency.observe(viewLifecycleOwner) {
                binding.settingsCurrencyCodeTextView.text = it.currencyCode
            }

            currentLanguage.observe(viewLifecycleOwner) {
                binding.settingsLanguageTextView.text = it.languageName
            }

            selectedThemeId.observe(viewLifecycleOwner) {
                if (it == 0) {
                    binding.settingsDarkThemeImageView.setBackgroundResource(R.color.button_background)
                    binding.settingsLightThemeImageView.setBackgroundResource(android.R.color.transparent)
                } else {
                    binding.settingsDarkThemeImageView.setBackgroundResource(android.R.color.transparent)
                    binding.settingsLightThemeImageView.setBackgroundResource(R.color.button_background)
                }
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