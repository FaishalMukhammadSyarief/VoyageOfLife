package com.zhalz.voyageoflife.login

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.zhalz.voyageoflife.R
import com.zhalz.voyageoflife.ui.home.HomeActivity
import com.zhalz.voyageoflife.ui.login.LoginActivity
import com.zhalz.voyageoflife.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(LoginActivity::class.java)

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
    fun testLoginProcess() {
        onView(withId(R.id.ed_login_email)).perform(typeText(TEST_EMAIL), closeSoftKeyboard())
        onView(withId(R.id.ed_login_password)).perform(typeText(TEST_PASSWORD), closeSoftKeyboard())

        onView(withId(R.id.btn_login)).perform(click())

        intended(hasComponent(HomeActivity::class.java.name))
    }

    companion object {
        private const val TEST_EMAIL = "pppppppp8@gmail.com"
        private const val TEST_PASSWORD = "pppppppp8"
    }

}
