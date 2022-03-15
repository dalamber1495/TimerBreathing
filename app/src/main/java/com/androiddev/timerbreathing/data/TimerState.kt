package com.androiddev.timerbreathing.data

sealed class TimerState(val dataTime: ExerciseParameters) {
    class Started(dataTime: ExerciseParameters) : TimerState(dataTime)
    class Stopped(dataTime:ExerciseParameters) : TimerState(dataTime)
}

