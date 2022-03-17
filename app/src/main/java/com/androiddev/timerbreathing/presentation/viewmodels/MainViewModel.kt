package com.androiddev.timerbreathing.presentation.viewmodels

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androiddev.timerbreathing.R
import com.androiddev.timerbreathing.data.TimerState
import com.androiddev.timerbreathing.domain.BreathParametersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val breathParametersRepository: BreathParametersRepository
) : ViewModel() {

    private var timerState: TimerState =
        TimerState.Stopped(breathParametersRepository.getParameters())
    var timer: CountDownTimer? = null
    private val _curTimeBreath = MutableLiveData<TimerState>()
    val curTimeBreath: LiveData<TimerState> = _curTimeBreath

    init {
        _curTimeBreath.postValue(TimerState.Stopped(breathParametersRepository.getParameters()))
    }

    fun getCurrentUserParameters() = breathParametersRepository.getParameters()

    fun startTimer() {
        when (_curTimeBreath.value) {
            is TimerState.Stopped -> {
                timerState = TimerState.Started(breathParametersRepository.getParameters())
                timer = object : CountDownTimer(
                    timerState.dataTime.timeTraining * ONE_SECOND,
                    ONE_SECOND.toLong()
                ) {
                    override fun onTick(p0: Long) {
                        timerState.dataTime.timeTraining = p0 / ONE_SECOND
                        onParametersChange(p0)
                        _curTimeBreath.postValue(timerState)
                    }

                    override fun onFinish() = onTimerFinished()


                }.start()
            }
            is TimerState.Started -> {
                timerState = TimerState.Stopped(breathParametersRepository.getParameters())
                _curTimeBreath.postValue(timerState)
            }
            else -> {}
        }
    }

    private fun onParametersChange(p0: Long) {
        breathParametersRepository.getParameters().apply {
            if (timeBreath == 0L && timeBreathDelay == 0L && timeExhalation == 0L && timeExhalationDelay == 0L)
                return
        }
        timerState.dataTime.apply {
            if (timeBreath != 0L) {
                timeBreath--
            } else if (timeBreath == 0L && timeBreathDelay != 0L) {
                timeBreathDelay--
            } else if (timeBreath == 0L && timeBreathDelay == 0L && timeExhalation != 0L) {
                timeExhalation--
            } else if (timeBreath == 0L && timeBreathDelay == 0L && timeExhalation == 0L && timeExhalationDelay != 0L) {
                timeExhalationDelay--
            } else {
                timeBreath = breathParametersRepository.getParameters().timeBreath
                timeExhalation = breathParametersRepository.getParameters().timeExhalation
                timeBreathDelay = breathParametersRepository.getParameters().timeBreathDelay
                timeExhalationDelay = breathParametersRepository.getParameters().timeExhalationDelay
                onParametersChange(p0)
            }
        }
    }

    private fun onTimerFinished() {
        timerState = TimerState.Stopped(breathParametersRepository.getParameters())
        _curTimeBreath.postValue(timerState)
    }

    fun changeParameters(resValue: Int, newValue: Int) {
        when (resValue) {
            R.id.time_exercise_tv -> {
                breathParametersRepository.setTimeTraining(newValue.toLong() * ONE_MINUTE)
            }
            R.id.time_breath_btn -> {
                breathParametersRepository.setTimeBreath(newValue.toLong())
            }
            R.id.time_wait_btn -> {
                breathParametersRepository.setTimeBreathDelay(newValue.toLong())
            }
            R.id.time_exh_btn -> {
                breathParametersRepository.setTimeExhalation(newValue.toLong())
            }
            R.id.time_wait2_btn -> {
                breathParametersRepository.setTimeExhalationDelay(newValue.toLong())
            }
        }
        _curTimeBreath.postValue(TimerState.Stopped(breathParametersRepository.getParameters()))

    }

    companion object {
        const val ONE_SECOND = 1000
        const val ONE_MINUTE = 60
    }
}
