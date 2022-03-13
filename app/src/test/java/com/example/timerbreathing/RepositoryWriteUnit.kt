package com.example.timerbreathing

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.platform.app.InstrumentationRegistry
import com.example.timerbreathing.data.ExerciseParameters
import com.example.timerbreathing.domain.BreathParametersRepository
import com.example.timerbreathing.presentation.viewmodels.MainViewModel
import com.example.timerbreathing.presentation.viewmodels.MainViewModel.Companion.ONE_MINUTE
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doReturn

import org.mockito.kotlin.mock
import org.mockito.kotlin.verify


class RepositoryWriteUnit {


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var viewModel: MainViewModel
    private lateinit var repository: BreathParametersRepository


    @Test
    fun `write on repository time training`(){
        val repository:BreathParametersRepository = mock{
            on { getParameters() }doReturn ExerciseParameters(60L,5L,5L,5L,5L)
        }
        val parameter:Int = 2
        viewModel = MainViewModel(repository)
        viewModel.changeParameters(R.id.time_exercise_tv,parameter)
        verify(repository).setTimeTraining(parameter.toLong()*ONE_MINUTE)
    }
    @Test
    fun `write on repository time breath`(){
        val repository:BreathParametersRepository = mock{
            on { getParameters() }doReturn ExerciseParameters(60L,5L,5L,5L,5L)
        }
        val parameter:Int = 2
        viewModel = MainViewModel(repository)
        viewModel.changeParameters(R.id.time_breath_btn,parameter)
        verify(repository).setTimeBreath(parameter.toLong())
    }
    @Test
    fun `write on repository time exhalation`(){
        val repository:BreathParametersRepository = mock{
            on { getParameters() }doReturn ExerciseParameters(60L,5L,5L,5L,5L)
        }
        val parameter:Int = 2
        viewModel = MainViewModel(repository)
        viewModel.changeParameters(R.id.time_exh_btn,parameter)
        verify(repository).setTimeExhalation(parameter.toLong())
    }
    @Test
    fun `write on repository time breath delay`(){
        val repository:BreathParametersRepository = mock{
            on { getParameters() }doReturn ExerciseParameters(60L,5L,5L,5L,5L)
        }
        val parameter = 2
        viewModel = MainViewModel(repository)
        viewModel.changeParameters(R.id.time_wait_btn,parameter)
        verify(repository).setTimeBreathDelay(parameter.toLong())
    }
    @Test
    fun `write on repository time exhalation delay`(){
        val repository:BreathParametersRepository = mock{
            on { getParameters() }doReturn ExerciseParameters(60L,5L,5L,5L,5L)
        }
        val parameter = 2
        viewModel = MainViewModel(repository)
        viewModel.changeParameters(R.id.time_wait2_btn,parameter)
        verify(repository).setTimeExhalationDelay(parameter.toLong())
    }

}
