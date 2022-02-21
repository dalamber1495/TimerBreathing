package com.example.timerbreathing.presentation.viewmodels

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.timerbreathing.data.ExerciseParameters
import com.example.timerbreathing.domain.ExerciseUseCase
import com.example.timerbreathing.presentation.TimerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val useCase: ExerciseUseCase) : ViewModel() {

    var userDataParameters:ExerciseParameters = useCase()
    private var timerState: TimerState = TimerState.Stopped()
    var timer: CountDownTimer? = null
    private val _curTimeBreath = MutableLiveData<TimerState>()
    val curTimeBreath:LiveData<TimerState> = _curTimeBreath

    init {
       _curTimeBreath.postValue(TimerState.Stopped())
    }
    fun startTimer(dataTime: ExerciseParameters) {
        when (_curTimeBreath.value) {
            is TimerState.Stopped -> {
                dataTime.apply {
                  userDataParameters = useCase(timeTraining,timeBreath,timeExhalation,timeBreathDelay,timeExhalationDelay)
                }
                timerState = TimerState.Started(dataTime)
                timer = object : CountDownTimer(timerState.dataTime.timeTraining * 1000, 1000) {
                    override fun onTick(p0: Long) {
                        timerState.dataTime.timeTraining=p0/1000
                        onParametersChange(p0)
                        _curTimeBreath.postValue(timerState)
                    }

                    override fun onFinish() = onTimerFinished()


                }.start()
            }
            is TimerState.Started ->{
                timerState = TimerState.Stopped(userDataParameters)
                _curTimeBreath.postValue(timerState)
            }
            else -> {}
        }
    }

    private fun onParametersChange(p0: Long) {
        timerState.dataTime.apply {
            if (timeBreath != 0L) {
                timeBreath--
            }else if(timeBreath == 0L && timeBreathDelay!=0L){timeBreathDelay--}
            else if(timeBreath ==0L && timeBreathDelay==0L &&timeExhalation!=0L){timeExhalation--}
            else if(timeBreath ==0L && timeBreathDelay==0L &&timeExhalation==0L &&timeExhalationDelay!=0L){timeExhalationDelay--}
            else {
                timeBreath = userDataParameters.timeBreath
                timeExhalation = userDataParameters.timeBreathDelay
                timeBreathDelay = userDataParameters.timeExhalation
                timeExhalationDelay = userDataParameters.timeExhalationDelay
                onParametersChange(p0)
            }
        }
    }

    private fun onTimerFinished() {
        timerState = TimerState.Stopped(userDataParameters)
        _curTimeBreath.postValue(timerState)


    }
}