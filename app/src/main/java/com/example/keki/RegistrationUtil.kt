package com.example.keki

/**
 * the input is not valid if...
 *  ...the user/password is empty
 *  ...the username is already taken
 *  ...the confirmed password is not same as real password
 *  ...the password contains less than 2 digits
 */
object RegistrationUtil {
    private val existingUsers = listOf("Peter","Carl")
    fun validateRegistrationInput(
        username: String,
        password: String,
        confirmedPassword: String
    ):Boolean{
       if(username.isEmpty() || password.isEmpty())
           return false
        if (username in existingUsers)
            return false
        if (confirmedPassword != password)
            return false
        if (password.count{ it.isDigit()} < 2)
            return false
            return true
    }

}