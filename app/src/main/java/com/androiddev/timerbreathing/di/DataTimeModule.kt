package com.androiddev.timerbreathing.di

import android.content.Context
import android.content.SharedPreferences
import com.androiddev.timerbreathing.domain.BreathParametersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataTimeModule {
    @Singleton
    @Provides
    fun provideSharedPref(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("preference_name", Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideParametersRepository(sharedPref: SharedPreferences) =
        BreathParametersRepository(sharedPref)
}
