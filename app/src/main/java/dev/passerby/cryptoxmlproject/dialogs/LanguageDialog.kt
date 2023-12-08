package dev.passerby.cryptoxmlproject.dialogs

import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dev.passerby.cryptoxmlproject.R
import dev.passerby.cryptoxmlproject.adapter.LanguageSelectionAdapter
import dev.passerby.cryptoxmlproject.databinding.DialogLanguageBinding
import dev.passerby.cryptoxmlproject.viewmodels.SettingsViewModel
import java.util.Locale

class LanguageDialog : DialogFragment(R.layout.dialog_language) {

    private lateinit var binding: DialogLanguageBinding

    private val viewModel: SettingsViewModel by navGraphViewModels(R.id.main_navigation)
    private var languageSelectionAdapter: LanguageSelectionAdapter? = null
    private var selectedLanguageId: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        languageSelectionAdapter = LanguageSelectionAdapter(viewModel.currentLanguage.value!!.id)
        languageSelectionAdapter!!.onLanguageClickListener = {
            viewModel.selectLanguage(it)
            selectedLanguageId = it.id
        }

        binding = DialogLanguageBinding.bind(view).apply {
            requireDialog().window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            requireDialog().setCancelable(true)
            requireDialog().show()

            languageRecyclerView.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = languageSelectionAdapter
                setHasFixedSize(true)
            }

            languageCancelButton.setOnClickListener {
                dialog?.dismiss()
                viewModel.resetLanguage()
            }

            languageAcceptButton.setOnClickListener {
                dialog?.dismiss()
                viewModel.acceptLanguage(selectedLanguageId)
            }

            viewModel.isLanguageChanged.observe(viewLifecycleOwner){
                languageAcceptButton.isEnabled = it
            }
        }
        languageSelectionAdapter!!.submitList(viewModel.languages)
    }
}