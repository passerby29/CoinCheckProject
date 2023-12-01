package dev.passerby.cryptoxmlproject.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dev.passerby.cryptoxmlproject.R
import dev.passerby.cryptoxmlproject.adapter.LanguageSelectionAdapter
import dev.passerby.cryptoxmlproject.databinding.DialogLanguageBinding
import dev.passerby.cryptoxmlproject.viewmodels.SettingsViewModel

class LanguageDialog : DialogFragment(R.layout.dialog_language) {

    private lateinit var binding: DialogLanguageBinding

    private val viewModel by lazy {
        ViewModelProvider(this)[SettingsViewModel::class.java]
    }

    private var languageSelectionAdapter: LanguageSelectionAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        languageSelectionAdapter = LanguageSelectionAdapter(requireContext())

        binding = DialogLanguageBinding.bind(view).apply {
            requireDialog().window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            requireDialog().setCancelable(true)
            requireDialog().show()

            languageRecyclerView.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = languageSelectionAdapter
            }
        }
        languageSelectionAdapter!!.submitList(viewModel.languages)
    }
}