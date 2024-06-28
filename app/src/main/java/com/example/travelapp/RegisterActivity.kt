package com.example.travelapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.travelapp.LoginActivity
import com.example.travelapp.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class RegisterActivity : AppCompatActivity() {
    private var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        val registerButton = findViewById<MaterialButton>(R.id.addButton)
        val emailEditText = findViewById<TextInputEditText>(R.id.emailEditText)
        val passwordEditText = findViewById<TextInputEditText>(R.id.passwordEditText)
        val confirmPasswordEditText = findViewById<TextInputEditText>(R.id.confirmPasswordEditText)

        registerButton.setOnClickListener {
            val email_txt = emailEditText.text.toString()
            val password_txt = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString().trim()

            if (TextUtils.isEmpty(email_txt) || TextUtils.isEmpty(password_txt)) {
                toastMsg("Empty Username or Password")
            } else if (password_txt.length < 6) {
                toastMsg("Password must be at least 6 characters.")
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email_txt).matches()) {
                toastMsg("Invalid email address.")
            } else if (!password_txt.matches("(.*[A-Z].*)".toRegex())) {
                toastMsg("Password must contain at least one capital letter.")
            } else if (password_txt != confirmPassword) {
                toastMsg("Passwords must match.")
            } else {
                registerUser(email_txt, password_txt)
            }
        }
    }

    private fun registerUser(email_txt: String, password_txt: String) {
        auth?.createUserWithEmailAndPassword(email_txt, password_txt)
            ?.addOnCompleteListener(this, OnCompleteListener { task ->
                if (task.isSuccessful) {
                    toastMsg("Registration Successful")
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                } else {
                    toastMsg("Registration Unsuccessful")
                }
            })
    }

    private fun toastMsg(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
