package com.example.timerbreathing.presentation.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.timerbreathing.R
import com.example.timerbreathing.data.ExerciseParameters
import com.example.timerbreathing.databinding.FragmentBreathBinding
import com.example.timerbreathing.presentation.TimerState
import com.example.timerbreathing.presentation.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreathFragment : Fragment(R.layout.fragment_breath) {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentBreathBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentBreathBinding.bind(view)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        binding.progressBar.max = viewModel.userDataParameters.timeTraining.toInt()

        binding.timeExerciseTv.setOnClickListener {
            findNavController().navigate(R.id.action_breathFragment_to_pickerDialogFragment)
        }
        binding.breathBtn.setOnClickListener { v ->
            when (viewModel.curTimeBreath.value) {
                is TimerState.Started -> {
                    (v as TextView).text = getString(R.string.breath)
                }
                is TimerState.Stopped -> {
                    (v as TextView).text = getString(R.string.stop_timer)

                }
                else -> {}
            }
            //Здесь отправляются параметры упражнения
            viewModel.startTimer(dataTime = ExerciseParameters())
        }

        viewModel.curTimeBreath.observe(viewLifecycleOwner) {
            when (it) {
                is TimerState.Stopped -> {
                    viewModel.timer?.cancel()
                    updateCountdownUI(it)

                }
                is TimerState.Started -> {
                    updateCountdownUI(it)
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.timer?.cancel()
    }

    private fun updateCountdownUI(timeState: TimerState) {
        val minutesUntilFinished = timeState.dataTime.timeTraining / 60
        val secondsInMinuteUntilFinished =
            timeState.dataTime.timeTraining - minutesUntilFinished * 60
        val secondsStr = secondsInMinuteUntilFinished.toString()
        binding.apply {
            timeExerciseTv.text =
                "$minutesUntilFinished:${if (secondsStr.length == 2) secondsStr else "0" + secondsStr}"
            timeBreathBtn.text = timeState.dataTime.timeBreath.toString()
            timeWaitBtn.text = timeState.dataTime.timeBreathDelay.toString()
            timeExhBtn.text = timeState.dataTime.timeExhalation.toString()
            timeWait2Btn.text = timeState.dataTime.timeExhalationDelay.toString()
            progressBar.progress =
                (viewModel.userDataParameters.timeTraining - timeState.dataTime.timeTraining).toInt()
            Log.e("TAG", "MAX: ${progressBar.max}")
            Log.e("TAG", "PROGRESS: ${progressBar.progress}")

        }
    }
}