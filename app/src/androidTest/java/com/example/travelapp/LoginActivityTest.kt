package com.example.travelapp

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    @Before
    fun setup() {
        resetValidationFlags()
    }

    @After
    fun tearDown() {
        resetValidationFlags()
    }

    @Test
    fun testEmptyFieldsShowErrorMessage() {
        ActivityScenario.launch(LoginActivity::class.java)

        onView(withId(R.id.signinButton)).perform(click())
        assert(checkValidationFlag("EmptyUsernameOrPassword"))
    }

    @Test
    fun testInvalidEmailShowsErrorMessage() {
        ActivityScenario.launch(LoginActivity::class.java)

        onView(withId(R.id.emailEditText)).perform(typeText("invalidEmail"), closeSoftKeyboard())
        onView(withId(R.id.passwordEditText)).perform(typeText("Password1"), closeSoftKeyboard())
        onView(withId(R.id.signinButton)).perform(click())
        assert(checkValidationFlag("InvalidEmailAddress"))
    }

    @Test
    fun testPasswordTooShortShowsErrorMessage() {
        ActivityScenario.launch(LoginActivity::class.java)

        onView(withId(R.id.emailEditText)).perform(typeText("test@example.com"), closeSoftKeyboard())
        onView(withId(R.id.passwordEditText)).perform(typeText("123"), closeSoftKeyboard())
        onView(withId(R.id.signinButton)).perform(click())
        assert(checkValidationFlag("PasswordTooShort"))
    }

    @Test
    fun testPasswordNoCapitalLetterShowsErrorMessage() {
        ActivityScenario.launch(LoginActivity::class.java)

        onView(withId(R.id.emailEditText)).perform(typeText("test@example.com"), closeSoftKeyboard())
        onView(withId(R.id.passwordEditText)).perform(typeText("password"), closeSoftKeyboard())
        onView(withId(R.id.signinButton)).perform(click())
        assert(checkValidationFlag("PasswordNoCapitalLetter"))
    }

    @Test
    fun testSignUpButtonNavigatesToRegisterActivity() {
        ActivityScenario.launch(LoginActivity::class.java)

        onView(withId(R.id.signupButton)).perform(click())

        val expectedIntent = Intent(
            androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().targetContext,
            RegisterActivity::class.java
        )
        assert(expectedIntent.component?.className == RegisterActivity::class.java.name)
    }

    private fun resetValidationFlags() {
        val sharedPref = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().targetContext
            .getSharedPreferences("LoginActivityPrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            clear()
            apply()
        }
    }

    private fun checkValidationFlag(flag: String): Boolean {
        val sharedPref = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().targetContext
            .getSharedPreferences("LoginActivityPrefs", Context.MODE_PRIVATE)
        return sharedPref.getBoolean(flag, false)
    }
}
