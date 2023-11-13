package dev.passerby.cryptoxmlproject.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.passerby.cryptoxmlproject.R
import dev.passerby.cryptoxmlproject.viewmodels.CoinInfoViewModel

class CoinInfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_coin_info, container, false)
    }
}