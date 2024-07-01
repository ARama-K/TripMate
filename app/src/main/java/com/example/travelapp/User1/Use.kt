package com.example.travelapp.User1

object Use {
    var email: String = ""
        private set

    fun setEmail(email: String) {
        Use.email = email
    }

    fun getUserEmail(): String {
        return email
    }
}