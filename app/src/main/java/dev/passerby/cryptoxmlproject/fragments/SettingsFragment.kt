package dev.passerby.cryptoxmlproject.fragments

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import dev.passerby.cryptoxmlproject.adapter.LanguageSelectionAdapter
import dev.passerby.cryptoxmlproject.databinding.FragmentSettingsBinding
import dev.passerby.cryptoxmlproject.dialogs.LanguageDialog
import dev.passerby.cryptoxmlproject.viewmodels.SettingsViewModel

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding
    get() = _binding ?: throw RuntimeException("FragmentSettingsBinding is null")

    private val viewModel by lazy {
        ViewModelProvider(this)[SettingsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.roomsToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.settingsGithubButton.setOnClickListener {
            openBrowser("https://github.com/passerby29")
        }
        binding.settingsDribbleButton.setOnClickListener {
            //TODO()
        }
        binding.linearLayout3.setOnClickListener {
            findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToLanguageDialog())
        }
        binding.llCurrency.setOnClickListener {
            findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToCurrencyDialog())
        }
    }

    private fun openBrowser(url: String){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    override fun onDestroy(){
        super.onDestroy()
        _binding = null
    }
}