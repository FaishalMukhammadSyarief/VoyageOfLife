package com.zhalz.voyageoflife.home

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.zhalz.voyageoflife.R
import com.zhalz.voyageoflife.ui.home.HomeActivity
import com.zhalz.voyageoflife.ui.welcome.WelcomeActivity
import com.zhalz.voyageoflife.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(HomeActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        Intents.init()
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        Intents.release()
    }

    @Test
    fun testLogoutProcess() {
        onView(withContentDescription("More options")).perform(click())

        onView(withText(R.string.logout)).perform(click())

        onView(withText(R.string.action_positive)).perform(click())

        intended(hasComponent(WelcomeActivity::class.java.name))
    }
}