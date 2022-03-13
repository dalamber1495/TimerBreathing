package com.example.timerbreathing.domain

import android.content.SharedPreferences
import com.example.timerbreathing.data.ExerciseParameters
import javax.inject.Inject

class BreathParametersRepository @Inject constructor(private val sharedPref: SharedPreferences) {

    fun setTimeTraining(t:Long){sharedPref.edit().putLong("timeTraining",t).apply()}
    fun setTimeBreath(t:Long){sharedPref.edit().putLong("timeBreath",t).apply()}
    fun setTimeExhalation(t:Long){sharedPref.edit().putLong("timeExhalation",t).apply()}
    fun setTimeBreathDelay(t:Long){sharedPref.edit().putLong("timeBreathDelay",t).apply()}
    fun setTimeExhalationDelay(t:Long){sharedPref.edit().putLong("timeExhalationDelay",t).apply()}


    fun getParameters(): ExerciseParameters =
        ExerciseParameters(
            sharedPref.getLong("timeTraining", SIXTY_SECONDS),
            sharedPref.getLong("timeBreath", FIVE_SECONDS),
            sharedPref.getLong("timeExhalation", FIVE_SECONDS),
            sharedPref.getLong("timeBreathDelay", FIVE_SECONDS),
            sharedPref.getLong("timeExhalationDelay", FIVE_SECONDS)
        )
    companion object{
        const val SIXTY_SECONDS = 60L
        const val FIVE_SECONDS = 5L
    }
}
