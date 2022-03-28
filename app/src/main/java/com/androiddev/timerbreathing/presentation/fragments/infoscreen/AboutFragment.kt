package com.androiddev.timerbreathing.presentation.fragments.infoscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.androiddev.timerbreathing.R
import com.androiddev.timerbreathing.databinding.FragmentAboutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutFragment : Fragment(R.layout.fragment_about) {
    private lateinit var binding: FragmentAboutBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAboutBinding.bind(view)
        binding.toolbar.setNavigationOnClickListener {findNavController().popBackStack() }
        binding.relaxBtn.setOnClickListener {
            findNavController().popBackStack(R.id.breathFragment,true) }
    }

}
