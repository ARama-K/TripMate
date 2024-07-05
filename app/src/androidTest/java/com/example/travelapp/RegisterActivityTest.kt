
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.travelapp.RegisterActivity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegisterActivityTest {

    @Test
    fun testEmptyEmailOrPassword() {
        val scenario = ActivityScenario.launch(RegisterActivity::class.java)
        scenario.onActivity { activity ->
            activity.handleRegistration("", "password123", "password123")
            assertEquals("Empty Username or Password", activity.testToastMessage)
        }
    }

    @Test
    fun testInvalidEmail() {
        val scenario = ActivityScenario.launch(RegisterActivity::class.java)
        scenario.onActivity { activity ->
            activity.handleRegistration("invalid-email", "Password123", "Password123")
            assertEquals("Invalid email address.", activity.testToastMessage)
        }
    }

    @Test
    fun testPasswordTooShort() {
        val scenario = ActivityScenario.launch(RegisterActivity::class.java)
        scenario.onActivity { activity ->
            activity.handleRegistration("test@example.com", "pass", "pass")
            assertEquals("Password must be at least 6 characters.", activity.testToastMessage)
        }
    }

    @Test
    fun testPasswordNoCapitalLetter() {
        val scenario = ActivityScenario.launch(RegisterActivity::class.java)
        scenario.onActivity { activity ->
            activity.handleRegistration("test@example.com", "password", "password")
            assertEquals("Password must contain at least one capital letter.", activity.testToastMessage)
        }
    }

    @Test
    fun testPasswordsDoNotMatch() {
        val scenario = ActivityScenario.launch(RegisterActivity::class.java)
        scenario.onActivity { activity ->
            activity.handleRegistration("test@example.com", "Password123", "DifferentPassword123")
            assertEquals("Passwords must match.", activity.testToastMessage)
        }
    }

    @Test
    fun testSuccessfulRegistration() {
        val scenario = ActivityScenario.launch(RegisterActivity::class.java)
        scenario.onActivity { activity ->
            // Use valid email and password that meets all conditions
            activity.handleRegistration("test@example.com", "Password123", "Password123")
            activity.isRegistrationSuccessful?.let { assertTrue(it) }
        }
    }
}
