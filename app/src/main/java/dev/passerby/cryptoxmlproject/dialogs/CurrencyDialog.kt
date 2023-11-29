package dev.passerby.cryptoxmlproject.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dev.passerby.cryptoxmlproject.R
import dev.passerby.cryptoxmlproject.adapter.CurrencySelectionAdapter
import dev.passerby.cryptoxmlproject.databinding.DialogCurrencyBinding
import dev.passerby.cryptoxmlproject.viewmodels.SettingsViewModel

class CurrencyDialog : DialogFragment(R.layout.dialog_currency) {

    private lateinit var binding: DialogCurrencyBinding

    private val viewModel by lazy {
        ViewModelProvider(this)[SettingsViewModel::class.java]
    }

    private var currencySelectionAdapter: CurrencySelectionAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currencySelectionAdapter = CurrencySelectionAdapter(requireContext())

        binding = DialogCurrencyBinding.bind(view).apply {
            requireDialog().window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            requireDialog().setCancelable(true)
            requireDialog().show()

            languageRecyclerView.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = currencySelectionAdapter
            }
        }
        currencySelectionAdapter!!.submitList(viewModel.currencies)
    }
}