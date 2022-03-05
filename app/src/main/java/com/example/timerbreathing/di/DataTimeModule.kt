package com.example.timerbreathing.di

import com.example.timerbreathing.data.ExerciseParameters
import com.example.timerbreathing.domain.ExerciseUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataTimeModule {

    @Singleton
    @Provides
    fun provideExerciseParameters() = ExerciseUseCase(ExerciseParameters())
}