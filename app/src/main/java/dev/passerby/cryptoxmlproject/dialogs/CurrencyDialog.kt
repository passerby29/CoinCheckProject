package dev.passerby.cryptoxmlproject.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dev.passerby.cryptoxmlproject.R
import dev.passerby.cryptoxmlproject.adapter.CurrencySelectionAdapter
import dev.passerby.cryptoxmlproject.databinding.DialogCurrencyBinding
import dev.passerby.cryptoxmlproject.viewmodels.SettingsViewModel

class CurrencyDialog : DialogFragment(R.layout.dialog_currency) {

    private lateinit var binding: DialogCurrencyBinding
    private val viewModel : SettingsViewModel by navGraphViewModels(R.id.main_navigation)
    private var currencySelectionAdapter: CurrencySelectionAdapter? = null
    private var selectedCurrencyId: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currencySelectionAdapter = CurrencySelectionAdapter(viewModel.currentCurrency.value!!.id)

        currencySelectionAdapter!!.onCurrencyClickListener = {
            viewModel.selectCurrency(it)
            selectedCurrencyId = it.id
        }

        binding = DialogCurrencyBinding.bind(view).apply {
            requireDialog().window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            requireDialog().setCancelable(true)
            requireDialog().show()

            currencyRecyclerView.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = currencySelectionAdapter
                setHasFixedSize(true)
            }

            currencyCancelButton.setOnClickListener {
                dialog?.dismiss()
                viewModel.resetCurrency()
            }

            currencyAcceptButton.setOnClickListener {
                dialog?.dismiss()
                viewModel.acceptCurrency(selectedCurrencyId)
            }

            viewModel.isCurrencyChanged.observe(viewLifecycleOwner) {
                currencyAcceptButton.isEnabled = it
            }
        }
        currencySelectionAdapter!!.submitList(viewModel.currencies)
    }
}