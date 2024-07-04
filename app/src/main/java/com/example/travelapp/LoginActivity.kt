package com.example.travelapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContentView(R.layout.activity_login)
        initFirebaseAuth()

        val emailEditText = findViewById<TextInputEditText>(R.id.emailEditText)
        val passwordEditText = findViewById<TextInputEditText>(R.id.passwordEditText)
        val registerButton = findViewById<MaterialButton>(R.id.signupButton)

        registerButton.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }

        val loginButton = findViewById<Button>(R.id.signinButton)
        loginButton.setOnClickListener {
            val txtEmail = emailEditText.text.toString()
            val txtPwd = passwordEditText.text.toString()
            when {
                TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPwd) -> {
                    val msg = "Empty Username or Password"
                    toastMsg(msg)
                    setValidationFlag("EmptyUsernameOrPassword")
                }
                txtPwd.length < 6 -> {
                    val msg = "Password must be at least 6 characters."
                    toastMsg(msg)
                    setValidationFlag("PasswordTooShort")
                }
                !Patterns.EMAIL_ADDRESS.matcher(txtEmail).matches() -> {
                    val msg = "Invalid email address."
                    toastMsg(msg)
                    setValidationFlag("InvalidEmailAddress")
                }
                !txtPwd.matches("(.*[A-Z].*)".toRegex()) -> {
                    val msg = "Password must contain at least one capital letter."
                    toastMsg(msg)
                    setValidationFlag("PasswordNoCapitalLetter")
                }
                else -> loginUser(txtEmail, txtPwd)
            }
        }
    }

    fun initFirebaseAuth() {
        auth = FirebaseAuth.getInstance()
    }

    private fun loginUser(txtEmail: String, txtPwd: String) {
        Log.d("LoginActivity", "Attempting to log in with email: $txtEmail")
        if (!Patterns.EMAIL_ADDRESS.matcher(txtEmail).matches()) {
            val msg = "Invalid email address."
            toastMsg(msg)
            setValidationFlag("InvalidEmailAddress")
            return
        }
        auth.signInWithEmailAndPassword(txtEmail, txtPwd)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("LoginActivity", "signInWithEmail:success")
                    val msg = "Login Successful"
                    toastMsg(msg)
                    setValidationFlag("LoginSuccessful")
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                } else {
                    Log.w("LoginActivity", "signInWithEmail:failure", task.exception)
                    val msg = "The password or email address is incorrect."
                    toastMsg(msg)
                    setValidationFlag("LoginFailed")
                }
            }
    }

    private fun toastMsg(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun setValidationFlag(flag: String) {
        val sharedPref = getSharedPreferences("LoginActivityPrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean(flag, true)
            apply()
        }
    }
}
