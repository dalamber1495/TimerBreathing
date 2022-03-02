package com.example.timerbreathing.presentation.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.timerbreathing.R
import com.example.timerbreathing.databinding.FragmentPickerDialogBinding
import com.example.timerbreathing.other.Constants
import com.example.timerbreathing.presentation.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PickerDialogFragment : DialogFragment(R.layout.fragment_picker_dialog) {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
               dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#99FFFFFF")))
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentPickerDialogBinding.bind(view)
        val resValue = arguments?.get(Constants.RESOURCE) as Int
        binding.apply {
            numberPicker.maxValue = 10
            numberPicker.minValue = 1
            numberPicker.value = arguments?.getInt(ARG_STARTED)!!
            numberPicker.setOnValueChangedListener { _, _, i2 ->
                viewModel.changeParameters(resValue, i2)
            }

        }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }
}