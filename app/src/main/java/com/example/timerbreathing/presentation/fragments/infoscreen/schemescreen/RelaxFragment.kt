package com.example.timerbreathing.presentation.fragments.infoscreen.schemescreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.timerbreathing.R
import com.example.timerbreathing.databinding.FragmentRelaxBinding
import com.example.timerbreathing.presentation.viewmodels.MainViewModel

class RelaxFragment : Fragment(R.layout.fragment_relax) {
    private lateinit var binding: FragmentRelaxBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRelaxBinding.bind(view)
        binding.toolbar.setNavigationOnClickListener {findNavController().popBackStack() }
        binding.relaxBtn.setOnClickListener {
            findNavController().navigate(R.id.action_relaxFragment_to_breathFragment) }
    }

}