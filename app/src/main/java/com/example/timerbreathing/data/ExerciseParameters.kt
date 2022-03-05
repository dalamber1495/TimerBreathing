package com.example.timerbreathing.data

data class ExerciseParameters(
    var timeTraining:Long = 60,
    var timeBreath:Long = 5,
    var timeExhalation:Long = 5,
    var timeBreathDelay:Long = 5,
    var timeExhalationDelay:Long = 5
)
