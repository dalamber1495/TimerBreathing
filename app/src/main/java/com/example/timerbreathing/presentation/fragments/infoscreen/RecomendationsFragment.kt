package com.example.timerbreathing.presentation.fragments.infoscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.timerbreathing.R
import com.example.timerbreathing.databinding.FragmentRecomendationsBinding
import com.example.timerbreathing.presentation.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecomendationsFragment : Fragment(R.layout.fragment_recomendations) {
    private lateinit var binding: FragmentRecomendationsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRecomendationsBinding.bind(view)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.relaxBtn.setOnClickListener {
            findNavController().navigate(R.id.action_recomendationsFragment_to_breathFragment) }
    }
}
