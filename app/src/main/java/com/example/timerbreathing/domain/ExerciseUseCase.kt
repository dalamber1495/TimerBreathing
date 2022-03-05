package com.example.timerbreathing.domain

import com.example.timerbreathing.data.ExerciseParameters

class ExerciseUseCase (private val userDataParameters: ExerciseParameters){

    operator fun invoke(timeTraining:Long? = null,
                        timeBreath:Long? = null,
                        timeExhalation:Long? = null,
                        timeBreathDelay:Long? = null,
                        timeExhalationDelay:Long? = null):ExerciseParameters{
        userDataParameters.timeTraining = timeTraining?:userDataParameters.timeTraining
        userDataParameters.timeBreath = timeBreath?:userDataParameters.timeBreath
        userDataParameters.timeExhalation = timeExhalation?:userDataParameters.timeExhalation
        userDataParameters.timeBreathDelay = timeBreathDelay?:userDataParameters.timeBreathDelay
        userDataParameters.timeExhalationDelay = timeExhalationDelay?:userDataParameters.timeExhalationDelay

        return userDataParameters
    }
}