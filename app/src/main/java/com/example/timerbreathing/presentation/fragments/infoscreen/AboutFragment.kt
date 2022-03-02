package com.example.timerbreathing.presentation.fragments.infoscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.timerbreathing.R
import com.example.timerbreathing.databinding.FragmentAboutBinding
import com.example.timerbreathing.presentation.viewmodels.MainViewModel

class AboutFragment : Fragment(R.layout.fragment_about) {
    private lateinit var binding: FragmentAboutBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAboutBinding.bind(view)
        binding.toolbar.setNavigationOnClickListener {findNavController().popBackStack() }
        binding.relaxBtn.setOnClickListener {
            findNavController().navigate(R.id.action_aboutFragment_to_breathFragment) }
    }
}