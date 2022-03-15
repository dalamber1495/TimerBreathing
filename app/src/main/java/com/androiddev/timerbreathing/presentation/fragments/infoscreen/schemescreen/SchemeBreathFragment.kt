package com.androiddev.timerbreathing.presentation.fragments.infoscreen.schemescreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.androiddev.timerbreathing.R
import com.androiddev.timerbreathing.databinding.FragmentSchemeBreathBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SchemeBreathFragment : Fragment(R.layout.fragment_scheme_breath) {

    private lateinit var binding: FragmentSchemeBreathBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSchemeBreathBinding.bind(view)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.balanceBtn.setOnClickListener {
            findNavController().navigate(R.id.action_schemeBreathFragment_to_balanceFragment)
        }
        binding.relaxBtn.setOnClickListener {
            findNavController().navigate(R.id.action_schemeBreathFragment_to_relaxFragment)
        }
        binding.cheerfulBtn.setOnClickListener {
            findNavController().navigate(R.id.action_schemeBreathFragment_to_cheerfulnessFragment)
        }

    }
}
