package com.androiddev.timerbreathing.presentation.fragments.breath

import android.annotation.SuppressLint
import android.content.ComponentName
import android.net.Uri
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat
import android.util.Log
import android.view.View
import android.view.MotionEvent
import android.widget.Button
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.androiddev.timerbreathing.R
import com.androiddev.timerbreathing.databinding.FragmentBreathBinding
import com.androiddev.timerbreathing.exoplayer.MusicService
import com.androiddev.timerbreathing.utils.Constants
import com.androiddev.timerbreathing.data.TimerState
import com.androiddev.timerbreathing.presentation.viewmodels.MainViewModel
import com.androiddev.timerbreathing.presentation.viewmodels.MainViewModel.Companion.ONE_MINUTE
import com.androiddev.timerbreathing.utils.Constants.ARG_STARTED
import dagger.hilt.android.AndroidEntryPoint
import java.lang.IllegalArgumentException
import kotlin.jvm.Throws


@AndroidEntryPoint
class BreathFragment : Fragment(R.layout.fragment_breath) {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentBreathBinding
    private var metronomeActivate = false
    private lateinit var mediaBrowserCompat: MediaBrowserCompat
    private val connectionCallback: MediaBrowserCompat.ConnectionCallback =
        object : MediaBrowserCompat.ConnectionCallback() {
            override fun onConnected() {
                super.onConnected()
                mediaBrowserCompat.sessionToken.also { token ->
                    val mediaController = MediaControllerCompat(requireContext(), token)
                    MediaControllerCompat.setMediaController(requireActivity(), mediaController)
                }
                Log.d("onConnected", "Controller Connected")
            }

            override fun onConnectionFailed() {
                super.onConnectionFailed()
                Log.d("onConnectionFailed", "Connection Failed")
            }
        }
    private val mControllerCallback = object : MediaControllerCompat.Callback() {
    }

    private fun onItemSelected(item: Int) {
        val mediaController = MediaControllerCompat.getMediaController(requireActivity())
        when (item) {
            R.id.metronom -> {
                mediaController.transportControls.playFromUri(
                    Uri.parse("asset:///heart_attack.mp3"),
                    null
                )
                metronomeActivate = !metronomeActivate
                when (metronomeActivate) {
                    true -> Toast.makeText(context, "Metronome was activated", Toast.LENGTH_SHORT)
                        .show()

                    false -> Toast.makeText(
                        context,
                        "Metronome was deactivated",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
            R.id.info -> {
                if (viewModel.curTimeBreath.value is TimerState.Started) viewModel.startTimer()
                findNavController().navigate(R.id.action_breathFragment_to_infoFragment, null)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val componentName = ComponentName(requireContext(), MusicService::class.java)
        mediaBrowserCompat = MediaBrowserCompat(
            requireContext(), componentName,
            connectionCallback,
            null
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentBreathBinding.bind(view)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        binding.toolbar.inflateMenu(R.menu.main_menu)
        binding.toolbar.setNavigationOnClickListener { viewModel.startTimer() }
        initButton()
        binding.toolbar.setOnMenuItemClickListener {
            onItemSelected(it.itemId)
            super.onOptionsItemSelected(it)
        }
        viewModel.curTimeBreath.observe(viewLifecycleOwner) {
            when (it) {
                is TimerState.Stopped -> {
                    binding.toolbar.navigationIcon = null
                    viewModel.timer?.cancel()
                    binding.apply {
                        breathBtn.text = getString(R.string.breathing)
                        breathBtn.setBackgroundResource(R.drawable.oval_button)
                        breathContainer.setBackgroundResource(R.drawable.gradient_background)
                    }
                    updateCountdownUI(it)

                }
                is TimerState.Started -> {
                    if (metronomeActivate) {
                        MediaControllerCompat.getMediaController(requireActivity()).transportControls.playFromUri(
                            Uri.parse("asset:///heart_attack.mp3"),
                            null
                        )
                    }
                    binding.toolbar.setNavigationIcon(R.drawable.ic_icon_back)
                    binding.progressBar.max =
                        viewModel.getCurrentUserParameters().timeTraining.toInt()
                    binding.apply {
                        breathBtn.text = getString(R.string.stop_timer)
                        breathContainer.setBackgroundResource(R.drawable.gradient_background2)
                    }
                    updateCountdownUI(it)
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initButton() {
        binding.timeExerciseTv.setOnClickListener {
            openPicker(
                R.id.time_exercise_tv,
                viewModel.curTimeBreath.value?.dataTime?.timeTraining?.toInt()?.div(ONE_MINUTE)
            )
        }
        binding.timeBreathBtn.setOnClickListener {
            openPicker(
                R.id.time_breath_btn,
                viewModel.curTimeBreath.value?.dataTime?.timeBreath?.toInt()
            )
        }
        binding.timeWaitBtn.setOnClickListener {
            openPicker(
                R.id.time_wait_btn,
                viewModel.curTimeBreath.value?.dataTime?.timeBreathDelay?.toInt()
            )
        }
        binding.timeExhBtn.setOnClickListener {
            openPicker(
                R.id.time_exh_btn,
                viewModel.curTimeBreath.value?.dataTime?.timeExhalation?.toInt()
            )
        }
        binding.timeWait2Btn.setOnClickListener {
            openPicker(
                R.id.time_wait2_btn,
                viewModel.curTimeBreath.value?.dataTime?.timeExhalationDelay?.toInt()
            )
        }

        binding.breathBtn.setOnClickListener {
            viewModel.startTimer()
            binding.breathBtn.setBackgroundResource(
                if (viewModel.curTimeBreath.value is TimerState.Started)
                    R.drawable.oval_button
                else R.drawable.oval_button2
            )
        }
        binding.breathBtn.setOnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN,
                MotionEvent.ACTION_MOVE -> {
                    v.setButtonStyle(R.drawable.oval_button_white, R.color.color4)
                }
                MotionEvent.ACTION_UP -> {
                    v.setButtonStyle(R.drawable.oval_button2, R.color.white)
                }
            }
            v?.onTouchEvent(event) ?: true
        }
    }

    fun View.setButtonStyle(resourceBack: Int, resourceColor: Int) {
        this.setBackgroundResource(resourceBack)
        (this as Button).setTextColor(
            ContextCompat.getColor(
                requireContext(),
                resourceColor
            )
        )
    }

    override fun onStart() {
        super.onStart()
        mediaBrowserCompat.connect()

    }


    override fun onPause() {
        super.onPause()
        viewModel.timer?.cancel()
    }

    override fun onStop() {
        super.onStop()
        val controllerCompat = MediaControllerCompat.getMediaController(requireActivity())
        controllerCompat?.unregisterCallback(mControllerCallback)
        mediaBrowserCompat.disconnect()
        if (viewModel.curTimeBreath.value is TimerState.Started) viewModel.startTimer()

    }

    private fun updateCountdownUI(timeState: TimerState) {
        val minutesUntilFinished = timeState.dataTime.timeTraining / ONE_MINUTE
        val secondsInMinuteUntilFinished =
            timeState.dataTime.timeTraining - minutesUntilFinished * ONE_MINUTE
        val secondsStr = secondsInMinuteUntilFinished.toString()
        binding.apply {
            timeExerciseTv.text =
                "$minutesUntilFinished:${if (secondsStr.length == 2) secondsStr else "0" + secondsStr}"
            timeBreathBtn.text = timeState.dataTime.timeBreath.toString()
            timeWaitBtn.text = timeState.dataTime.timeBreathDelay.toString()
            timeExhBtn.text = timeState.dataTime.timeExhalation.toString()
            timeWait2Btn.text = timeState.dataTime.timeExhalationDelay.toString()
            progressBar.progress =
                (viewModel.getCurrentUserParameters().timeTraining - timeState.dataTime.timeTraining).toInt()
            Log.e("TAG", "MAX: ${progressBar.max}")
            Log.e("TAG", "PROGRESS: ${progressBar.progress}")

        }
    }

    private fun openPicker(resValue: Int, curValue: Int?) {
        try {
            findNavController().navigate(
                R.id.action_breathFragment_to_pickerDialogFragment,
                bundleOf(Pair(Constants.RESOURCE, resValue), Pair(ARG_STARTED, curValue))
            )
        } catch (e: IllegalArgumentException) {
            Log.e("TAG", "openPicker: ${e.stackTrace}", )
            return
        }

    }
}
