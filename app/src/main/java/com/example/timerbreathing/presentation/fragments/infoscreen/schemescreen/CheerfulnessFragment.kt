package com.example.timerbreathing.presentation.fragments.infoscreen.schemescreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.timerbreathing.R
import com.example.timerbreathing.databinding.FragmentCheerfulnessBinding
import com.example.timerbreathing.presentation.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheerfulnessFragment : Fragment(R.layout.fragment_cheerfulness) {
    private lateinit var binding: FragmentCheerfulnessBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCheerfulnessBinding.bind(view)
        binding.toolbar.setNavigationOnClickListener {findNavController().popBackStack() }
        binding.relaxBtn.setOnClickListener {
            findNavController().navigate(R.id.action_cheerfulnessFragment_to_breathFragment) }
    }
}
