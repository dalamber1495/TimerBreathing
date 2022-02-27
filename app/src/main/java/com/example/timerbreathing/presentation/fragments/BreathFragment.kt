package com.example.timerbreathing.presentation.fragments

import android.content.ComponentName
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.timerbreathing.R
import com.example.timerbreathing.data.ExerciseParameters
import com.example.timerbreathing.databinding.FragmentBreathBinding
import com.example.timerbreathing.exoplayer.MusicService
import com.example.timerbreathing.other.Constants
import com.example.timerbreathing.presentation.TimerState
import com.example.timerbreathing.presentation.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreathFragment : Fragment(R.layout.fragment_breath) {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentBreathBinding

    private var metronomeActivate = false
    private lateinit var mMediaBrowserCompat: MediaBrowserCompat
    private val connectionCallback: MediaBrowserCompat.ConnectionCallback =
        object : MediaBrowserCompat.ConnectionCallback() {
            override fun onConnected() {
                // The browser connected to the session successfully, use the token to create the controller
                super.onConnected()
                mMediaBrowserCompat.sessionToken.also { token ->
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

    fun onItemSelected(item: Int) {
        val mediaController = MediaControllerCompat.getMediaController(requireActivity())
        when (item) {
            R.id.metronom -> {
//                val state = mediaController.playbackState.state
//                Log.e("TAG", "onItemSelected: ${state}", )

                // if it is not playing then what are you waiting for ? PLAY !
//                if (state == PlaybackStateCompat.STATE_PAUSED ||
//                    state == PlaybackStateCompat.STATE_STOPPED ||
//                    state == PlaybackStateCompat.STATE_NONE
//                ) {
                mediaController.transportControls.playFromUri(
                    Uri.parse("asset:///heart_attack.mp3"),
                    null
                )
                //  btn.text = "Pause"
                //    }
                // you are playing ? knock it off !
//                else if (state == PlaybackStateCompat.STATE_PLAYING ||
//                    state == PlaybackStateCompat.STATE_BUFFERING ||
//                    state == PlaybackStateCompat.STATE_CONNECTING
//                ) {
//                    mediaController.transportControls.stop()
//                    //      btn.text = "Play"
//                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val componentName = ComponentName(requireContext(), MusicService::class.java)
        // initialize the browser
        mMediaBrowserCompat = MediaBrowserCompat(
            requireContext(), componentName, //Identifier for the service
            connectionCallback,
            null
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding = FragmentBreathBinding.bind(view)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        binding.toolbar.inflateMenu(R.menu.main_menu)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.metronom -> {
                    onItemSelected(R.id.metronom)
                    metronomeActivate = !metronomeActivate
                    if (metronomeActivate) {
                        Toast.makeText(context, "Metronome was activated", Toast.LENGTH_SHORT).show()
                    }else
                        Toast.makeText(context, "Metronome was deactivated", Toast.LENGTH_SHORT).show()

                    super.onOptionsItemSelected(it)
                }
                else -> {
                    super.onOptionsItemSelected(it)
                }
            }
        }
        binding.timeExerciseTv.setOnClickListener {
            openPicker(R.id.time_exercise_tv)
        }
        binding.timeBreathBtn.setOnClickListener {
            openPicker(R.id.time_breath_btn)
        }
        binding.timeWaitBtn.setOnClickListener {
            openPicker(R.id.time_wait_btn)
        }
        binding.timeExhBtn.setOnClickListener {
            openPicker(R.id.time_exh_btn)
        }
        binding.timeWait2Btn.setOnClickListener {
            openPicker(R.id.time_wait2_btn)
        }
        binding.breathBtn.setOnClickListener { v ->
//            when (viewModel.curTimeBreath.value) {
//                is TimerState.Started -> {
//                    (v as TextView).text = getString(R.string.breath)
//                }
//                is TimerState.Stopped -> {
//                    (v as TextView).text = getString(R.string.stop_timer)
//                }
//                else -> {}
//            }
            //Здесь отправляются параметры упражнения
            viewModel.startTimer()
        }

        viewModel.curTimeBreath.observe(viewLifecycleOwner) {
            when (it) {
                is TimerState.Stopped -> {
//                    MediaControllerCompat.getMediaController(requireActivity()).transportControls.stop()
                    viewModel.timer?.cancel()
                    binding.breathBtn.text = getString(R.string.breathing)
                    updateCountdownUI(it)

                }
                is TimerState.Started -> {
                    if (metronomeActivate) {
                        MediaControllerCompat.getMediaController(requireActivity()).transportControls.playFromUri(
                            Uri.parse("asset:///heart_attack.mp3"),
                            null
                        )
                    }
                    binding.progressBar.max = viewModel.immutableParameters.timeTraining.toInt()
                    binding.breathBtn.text = getString(R.string.stop_timer)
                    updateCountdownUI(it)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        mMediaBrowserCompat.connect()
    }


    override fun onPause() {
        super.onPause()
        viewModel.timer?.cancel()
    }

    override fun onStop() {
        super.onStop()
        val controllerCompat = MediaControllerCompat.getMediaController(requireActivity())
        controllerCompat?.unregisterCallback(mControllerCallback)
        mMediaBrowserCompat.disconnect()
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
                (viewModel.immutableParameters.timeTraining - timeState.dataTime.timeTraining).toInt()
            Log.e("TAG", "MAX: ${progressBar.max}")
            Log.e("TAG", "PROGRESS: ${progressBar.progress}")

        }
    }

    @Synchronized
    private fun openPicker(resValue: Int) {
        try {
            findNavController().navigate(
                R.id.action_breathFragment_to_pickerDialogFragment,
                bundleOf(Pair(Constants.RESOURCE, resValue))
            )
        }catch (e:IllegalArgumentException){

        }
    }
}