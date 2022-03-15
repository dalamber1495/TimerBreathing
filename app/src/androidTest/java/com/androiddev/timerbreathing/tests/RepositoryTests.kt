package com.androiddev.timerbreathing.tests

import android.content.SharedPreferences
import androidx.test.filters.SmallTest
import com.androiddev.timerbreathing.domain.BreathParametersRepository
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@HiltAndroidTest
class RepositoryTests {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)
    @Inject
    @Named("test_sh")
    lateinit var sharedPreferences:SharedPreferences
    lateinit var repository: BreathParametersRepository



    @Before
    fun setup(){
        hiltRule.inject()
        repository = BreathParametersRepository(sharedPreferences)
    }

    @Test
    fun repositoryTestTimeTraining(){
        repository.setTimeTraining(1L)
        assertThat(repository.getParameters().timeTraining).isEqualTo(1L)
    }
    @Test
    fun repositoryTestTimeExhalation(){
        repository.setTimeExhalation(1L)
        assertThat(repository.getParameters().timeExhalation).isEqualTo(1L)
    }
    @Test
    fun repositoryTestTimeExhalationDelay(){
        repository.setTimeExhalationDelay(1L)
        assertThat(repository.getParameters().timeExhalationDelay).isEqualTo(1L)
    }
    @Test
    fun repositoryTestTimeBreath(){
        repository.setTimeBreath(1L)
        assertThat(repository.getParameters().timeBreath).isEqualTo(1L)
    }
    @Test
    fun repositoryTestTimeBreathDelay(){
        repository.setTimeBreathDelay(1L)
        assertThat(repository.getParameters().timeBreathDelay).isEqualTo(1L)
    }
}