package com.androiddev.timerbreathing.presentation.fragments.infoscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.androiddev.timerbreathing.R
import com.androiddev.timerbreathing.databinding.FragmentRecomendationsBinding
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
            findNavController().popBackStack(R.id.breathFragment,false) }
    }
}
