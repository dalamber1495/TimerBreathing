package com.example.timerbreathing.tests

import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.example.timerbreathing.R
import com.example.timerbreathing.launchFragmentInHiltContainer
import com.example.timerbreathing.presentation.fragments.breath.BreathFragment
import com.example.timerbreathing.presentation.fragments.infoscreen.InfoFragment
import com.example.timerbreathing.presentation.fragments.infoscreen.schemescreen.SchemeBreathFragment
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@MediumTest
@HiltAndroidTest

class FragmentTestActions {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun clickOnInfoButton_navigateToBreathFragment() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        launchFragmentInHiltContainer<BreathFragment> {
            navController.setGraph(R.navigation.nav_application_graph)
            Navigation.setViewNavController(requireView(), navController)
        }
        onView(withId(R.id.info)).perform(click())
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.infoFragment)
    }

    @Test
    fun clickOnInfoFragment_navigateSchemes() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        launchFragmentInHiltContainer<InfoFragment> {
            navController.setGraph(R.navigation.nav_application_graph)
            navController.setCurrentDestination(R.id.infoFragment)
            Navigation.setViewNavController(requireView(), navController)
        }
        onView(withId(R.id.scheme_brth)).perform(click())
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.schemeBreathFragment)
    }

    @Test
    fun clickOnInfoFragment_navigateAboutTimer() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        launchFragmentInHiltContainer<InfoFragment> {
            navController.setGraph(R.navigation.nav_application_graph)
            navController.setCurrentDestination(R.id.infoFragment)
            Navigation.setViewNavController(requireView(), navController)
        }
        onView(withId(R.id.about_tmr)).perform(click())
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.aboutTimerFragment)
    }

    @Test
    fun clickOnInfoFragment_navigateRecomendations() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        launchFragmentInHiltContainer<InfoFragment> {
            navController.setGraph(R.navigation.nav_application_graph)
            navController.setCurrentDestination(R.id.infoFragment)
            Navigation.setViewNavController(requireView(), navController)
        }
        onView(withId(R.id.recomentations)).perform(click())
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.recomendationsFragment)
    }

    @Test
    fun clickOnInfoFragment_navigateAbout() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        launchFragmentInHiltContainer<InfoFragment> {
            navController.setGraph(R.navigation.nav_application_graph)
            navController.setCurrentDestination(R.id.infoFragment)
            Navigation.setViewNavController(requireView(), navController)
        }
        onView(withId(R.id.about_auth)).perform(click())
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.aboutFragment)
    }

    @Test
    fun clickOnSchemeFragment_navigateRelax() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        launchFragmentInHiltContainer<SchemeBreathFragment> {
            navController.setGraph(R.navigation.nav_application_graph)
            navController.setCurrentDestination(R.id.schemeBreathFragment)
            Navigation.setViewNavController(requireView(), navController)
        }
        onView(withId(R.id.relax_btn)).perform(click())
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.relaxFragment)
    }

    @Test
    fun clickOnSchemeFragment_navigateBalance() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        launchFragmentInHiltContainer<SchemeBreathFragment> {
            navController.setGraph(R.navigation.nav_application_graph)
            navController.setCurrentDestination(R.id.schemeBreathFragment)
            Navigation.setViewNavController(requireView(), navController)
        }
        onView(withId(R.id.balance_btn)).perform(click())
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.balanceFragment)
    }

    @Test
    fun clickOnSchemeFragment_navigateExh() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        launchFragmentInHiltContainer<SchemeBreathFragment> {
            navController.setGraph(R.navigation.nav_application_graph)
            navController.setCurrentDestination(R.id.schemeBreathFragment)
            Navigation.setViewNavController(requireView(), navController)
        }
        onView(withId(R.id.cheerful_btn)).perform(click())
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.cheerfulnessFragment)
    }
}