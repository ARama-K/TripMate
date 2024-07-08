package com.example.travelapp

import android.content.Context
import android.content.SharedPreferences
import android.widget.Button
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.google.android.material.textfield.TextInputEditText
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(LoginActivity::class.java)

    private lateinit var sharedPref: SharedPreferences

    @Before
    fun setUp() {
        // Clear shared preferences before each test
        sharedPref = InstrumentationRegistry.getInstrumentation().targetContext
            .getSharedPreferences("LoginActivityPrefs", Context.MODE_PRIVATE)
        sharedPref.edit().clear().apply()
    }

    @After
    fun tearDown() {
        // Clear shared preferences after each test
        sharedPref.edit().clear().apply()
    }

    @Test
    fun testEmptyUsernameOrPassword() {
        ActivityScenario.launch(LoginActivity::class.java).use { scenario ->
            scenario.onActivity { activity ->
                activity.findViewById<TextInputEditText>(R.id.emailEditText).setText("")
                activity.findViewById<TextInputEditText>(R.id.passwordEditText).setText("")

                activity.findViewById<Button>(R.id.signinButton).performClick()

                assertTrue(sharedPref.getBoolean("EmptyUsernameOrPassword", false))
            }
        }
    }

    @Test
    fun testPasswordTooShort() {
        ActivityScenario.launch(LoginActivity::class.java).use { scenario ->
            scenario.onActivity { activity ->
                activity.findViewById<TextInputEditText>(R.id.emailEditText).setText("test@example.com")
                activity.findViewById<TextInputEditText>(R.id.passwordEditText).setText("12345")

                activity.findViewById<Button>(R.id.signinButton).performClick()

                assertTrue(sharedPref.getBoolean("PasswordTooShort", false))
            }
        }
    }

    @Test
    fun testInvalidEmailAddress() {
        ActivityScenario.launch(LoginActivity::class.java).use { scenario ->
            scenario.onActivity { activity ->
                activity.findViewById<TextInputEditText>(R.id.emailEditText).setText("invalid-email")
                activity.findViewById<TextInputEditText>(R.id.passwordEditText).setText("Password1")

                activity.findViewById<Button>(R.id.signinButton).performClick()

                assertTrue(sharedPref.getBoolean("InvalidEmailAddress", false))
            }
        }
    }

    @Test
    fun testPasswordNoCapitalLetter() {
        ActivityScenario.launch(LoginActivity::class.java).use { scenario ->
            scenario.onActivity { activity ->
                activity.findViewById<TextInputEditText>(R.id.emailEditText).setText("test@example.com")
                activity.findViewById<TextInputEditText>(R.id.passwordEditText).setText("password")

                activity.findViewById<Button>(R.id.signinButton).performClick()

                assertTrue(sharedPref.getBoolean("PasswordNoCapitalLetter", false))
            }
        }
    }

    @Test
    fun testSuccessfulLogin() {
        ActivityScenario.launch(LoginActivity::class.java).use { scenario ->
            scenario.onActivity { activity ->
                // Assuming valid credentials
                activity.findViewById<TextInputEditText>(R.id.emailEditText).setText("abc@gmail.com")
                activity.findViewById<TextInputEditText>(R.id.passwordEditText).setText("Abcdefg")

                activity.findViewById<Button>(R.id.signinButton).performClick()

                assertTrue(sharedPref.getBoolean("LoginSuccessful", true))
            }
        }
    }

}
