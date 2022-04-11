package com.androiddev.timerbreathing.presentation.fragments.breath

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.androiddev.timerbreathing.R
import com.androiddev.timerbreathing.databinding.FragmentPickerDialogBinding
import com.androiddev.timerbreathing.utils.Constants
import com.androiddev.timerbreathing.presentation.viewmodels.MainViewModel
import com.androiddev.timerbreathing.utils.Constants.ARG_STARTED
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PickerDialogFragment : DialogFragment(R.layout.fragment_picker_dialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentPickerDialogBinding.bind(view)
        val resValue = arguments?.get(Constants.RESOURCE) as Int
        binding.apply {
            if (resValue == R.id.time_exercise_tv)
                numberPicker.minValue = MIN_VALUE
            else
                numberPicker.minValue = MIN_NULL_VALUE
            numberPicker.maxValue = MAX_VALUE
            numberPicker.value = arguments?.getInt(ARG_STARTED)!!
            numberPicker.setOnValueChangedListener { _, _, i2 ->
                viewModel.changeParameters(resValue, i2)
            }
        }
    }
    companion object{
        const val MIN_NULL_VALUE = 0
        const val MIN_VALUE = 1
        const val MAX_VALUE = 10
    }
}
