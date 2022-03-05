package com.example.timerbreathing.presentation.viewmodels

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.timerbreathing.R
import com.example.timerbreathing.data.ExerciseParameters
import com.example.timerbreathing.domain.ExerciseUseCase
import com.example.timerbreathing.presentation.TimerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val useCase: ExerciseUseCase) : ViewModel() {

    private var timerState: TimerState = TimerState.Stopped()
    var timer: CountDownTimer? = null
    private val _curTimeBreath = MutableLiveData<TimerState>()
    val curTimeBreath: LiveData<TimerState> = _curTimeBreath

    init {
        _curTimeBreath.postValue(TimerState.Stopped(useCase()))
    }

    fun getCurrentUserParameters() = ExerciseParameters(
        useCase().timeTraining,
        useCase().timeBreath,
        useCase().timeExhalation,
        useCase().timeBreathDelay,
        useCase().timeExhalationDelay
    )

    fun startTimer() {
        when (_curTimeBreath.value) {
            is TimerState.Stopped -> {
                timerState = TimerState.Started(getCurrentUserParameters())
                timer = object : CountDownTimer(timerState.dataTime.timeTraining * 1000, 1000) {
                    override fun onTick(p0: Long) {
                        timerState.dataTime.timeTraining = p0 / 1000
                        onParametersChange(p0)
                        _curTimeBreath.postValue(timerState)
                    }

                    override fun onFinish() = onTimerFinished()


                }.start()
            }
            is TimerState.Started -> {
                timerState = TimerState.Stopped(useCase())
                _curTimeBreath.postValue(timerState)
            }
            else -> {}
        }
    }

    private fun onParametersChange(p0: Long) {
        useCase().apply {
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
                timeBreath = useCase().timeBreath
                timeExhalation = useCase().timeExhalation
                timeBreathDelay = useCase().timeBreathDelay
                timeExhalationDelay = useCase().timeExhalationDelay
                onParametersChange(p0)
            }
        }
    }

    private fun onTimerFinished() {
        timerState = TimerState.Stopped(useCase())
        _curTimeBreath.postValue(timerState)
    }

    fun changeParameters(resValue: Int, newValue: Int) {
        when (resValue) {
            R.id.time_exercise_tv -> {
                _curTimeBreath.postValue(TimerState.Stopped(useCase(timeTraining = newValue.toLong() * 60)))
            }
            R.id.time_breath_btn -> {
                _curTimeBreath.postValue(TimerState.Stopped(useCase(timeBreath = newValue.toLong())))
            }
            R.id.time_wait_btn -> {
                _curTimeBreath.postValue(TimerState.Stopped(useCase(timeBreathDelay = newValue.toLong())))
            }
            R.id.time_exh_btn -> {
                _curTimeBreath.postValue(TimerState.Stopped(useCase(timeExhalation = newValue.toLong())))
            }
            R.id.time_wait2_btn -> {
                _curTimeBreath.postValue(TimerState.Stopped(useCase(timeExhalationDelay = newValue.toLong())))
            }
        }
    }
}