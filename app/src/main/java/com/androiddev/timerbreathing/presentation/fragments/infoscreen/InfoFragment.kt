package com.androiddev.timerbreathing.presentation.fragments.infoscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.androiddev.timerbreathing.R
import com.androiddev.timerbreathing.databinding.FragmentInfoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoFragment : Fragment(R.layout.fragment_info) {

    private lateinit var binding: FragmentInfoBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentInfoBinding.bind(view)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.schemeBrth.setOnClickListener {
            findNavController().navigate(R.id.action_infoFragment_to_schemeBreathFragment)
        }
        binding.recomentations.setOnClickListener {
            findNavController().navigate(R.id.action_infoFragment_to_recomendationsFragment)
        }
        binding.aboutAuth.setOnClickListener {
            findNavController().navigate(R.id.action_infoFragment_to_aboutFragment)
        }
        binding.aboutTmr.setOnClickListener {
            findNavController().navigate(R.id.action_infoFragment_to_aboutTimerFragment)
        }
    }
}



