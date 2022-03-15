package com.androiddev.timerbreathing.presentation.fragments.infoscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.androiddev.timerbreathing.R
import com.androiddev.timerbreathing.databinding.FragmentAboutTimerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutTimerFragment : Fragment(R.layout.fragment_about_timer) {
    private lateinit var binding: FragmentAboutTimerBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAboutTimerBinding.bind(view)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.relaxBtn.setOnClickListener {
            findNavController().navigate(R.id.action_aboutTimerFragment_to_breathFragment)
        }
        binding.schemeBtn.setOnClickListener {
            findNavController().navigate(R.id.action_aboutTimerFragment_to_schemeBreathFragment)
        }
    }
}
