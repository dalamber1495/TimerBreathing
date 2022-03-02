package com.example.timerbreathing.presentation.fragments.infoscreen.schemescreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.timerbreathing.R
import com.example.timerbreathing.databinding.FragmentBalanceBinding
import com.example.timerbreathing.presentation.viewmodels.MainViewModel

class BalanceFragment : Fragment(R.layout.fragment_balance) {
    private lateinit var binding: FragmentBalanceBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBalanceBinding.bind(view)
        binding.toolbar.setNavigationOnClickListener {findNavController().popBackStack() }
        binding.relaxBtn.setOnClickListener {
            findNavController().navigate(R.id.action_balanceFragment_to_breathFragment) }
    }
}