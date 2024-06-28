
package com.example.travelapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
//user1 isnt commited yet
//import com.example.travelapp.User1.Use.setEmail
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private var auth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        val emailEditText = findViewById<TextInputEditText>(R.id.emailEditText)
        val passwordEditText = findViewById<TextInputEditText>(R.id.passwordEditText)
        val registerButton = findViewById<MaterialButton>(R.id.signupButton)
        //intent for sign up page
        registerButton.setOnClickListener {
            startActivity(
                Intent(
                    this@LoginActivity,
                    RegisterActivity::class.java
                )
            )
        }
        val loginButton = findViewById<Button>(R.id.signinButton)
        loginButton.setOnClickListener {
            val txt_Email = emailEditText.text.toString()
            val txt_Pwd = passwordEditText.text.toString()
            if (TextUtils.isEmpty(txt_Email) ||
                TextUtils.isEmpty(txt_Pwd)
            ) {
                val msg = "Empty Username or Password"
                toastMsg(msg)
            } else if (txt_Pwd.length < 6) {
                val msg = "Password must be at least 6 characters."
                toastMsg(msg)
            } else if (!Patterns.EMAIL_ADDRESS.matcher(txt_Email).matches()) {
                val msg = "Invalid email address."
                toastMsg(msg)
            } else if (!txt_Pwd.matches("(.*[A-Z].*)".toRegex())) {
                val msg = "Password must contain at least one capital letter."
                toastMsg(msg)
            } else loginUser(txt_Email, txt_Pwd)
        }
    }

    private fun loginUser(txt_email: String, txt_pwd: String) {
        if (!Patterns.EMAIL_ADDRESS.matcher(txt_email).matches()) {
            val msg = "Invalid email address."
            toastMsg(msg)
        }
        auth!!.signInWithEmailAndPassword(txt_email, txt_pwd)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //Log.d(TAG, "signInWithEmail:success");
                    //setEmail(txt_email)
                    val msg = "Login Successful"
                    toastMsg(msg)
                    startActivity(
                        Intent(
                            this@LoginActivity,
                            MainActivity::class.java
                        )
                    )
                } else {
                    // If sign in fails, display a message to the user.
                    val msg = "The password or email address is incorrect."
                    toastMsg(msg)
                }
            }
    }

    fun toastMsg(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
