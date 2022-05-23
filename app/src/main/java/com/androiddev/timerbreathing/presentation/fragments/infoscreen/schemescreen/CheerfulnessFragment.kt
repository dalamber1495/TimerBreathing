package com.androiddev.timerbreathing.presentation.fragments.infoscreen.schemescreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.androiddev.timerbreathing.R
import com.androiddev.timerbreathing.databinding.FragmentCheerfulnessBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheerfulnessFragment : Fragment(R.layout.fragment_cheerfulness) {
    private lateinit var binding: FragmentCheerfulnessBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCheerfulnessBinding.bind(view)
        binding.toolbar.setNavigationOnClickListener {findNavController().popBackStack() }
        binding.relaxBtn.setOnClickListener {
            findNavController().popBackStack(R.id.breathFragment,false) }
    }
}
