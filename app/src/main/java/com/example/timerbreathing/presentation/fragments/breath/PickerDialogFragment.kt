package com.example.timerbreathing.presentation.fragments.breath

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.timerbreathing.R
import com.example.timerbreathing.databinding.FragmentPickerDialogBinding
import com.example.timerbreathing.utils.Constants
import com.example.timerbreathing.presentation.viewmodels.MainViewModel
import com.example.timerbreathing.utils.Constants.ARG_STARTED
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PickerDialogFragment : DialogFragment(R.layout.fragment_picker_dialog) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentPickerDialogBinding.bind(view)
        val resValue = arguments?.get(Constants.RESOURCE) as Int
        binding.apply {
            if (resValue == R.id.time_exercise_tv)
                numberPicker.minValue = 1
            else
                numberPicker.minValue = 0
            numberPicker.maxValue = 10
            numberPicker.value = arguments?.getInt(ARG_STARTED)!!
            numberPicker.setOnValueChangedListener { _, _, i2 ->
                viewModel.changeParameters(resValue, i2)
            }

        }
    }
}