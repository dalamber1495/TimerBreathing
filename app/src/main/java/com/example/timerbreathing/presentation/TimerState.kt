package com.example.timerbreathing.presentation

import com.example.timerbreathing.data.ExerciseParameters

sealed class TimerState(val dataTime: ExerciseParameters) {
    class Started(dataTime: ExerciseParameters) : TimerState(dataTime)
    class Stopped(dataTime:ExerciseParameters = ExerciseParameters()) : TimerState(dataTime)
}

