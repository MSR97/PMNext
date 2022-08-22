package com.pomonext.pomonext.utils

import android.content.Context

private fun isValidEmailAddress(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

private fun isPasswordLengthValid(password: String): Boolean {
    return (password.trim().length >= 6)
}

private fun isPasswordConfirmed(password: String, confirmPassword: String): Boolean {
    return password == confirmPassword
}

fun isEmailAndPasswordValid(
    isCreatingAccount: Boolean,
    email: String,
    password: String,
    confirmPassword: String
): Any {
    if (email.trim().isEmpty() || password.trim().isEmpty()) {
        return "Please fill all the fields"
    }
    if (!isValidEmailAddress(email.trim())) {
        return "Email Address is not valid"
    }
    if (!isPasswordLengthValid(password.trim())) {
        return "Password length at least should have 6 characters"
    }
    if (isCreatingAccount && !isPasswordConfirmed(password.trim(), confirmPassword.trim())) {
        return "Your password and confirmation password do not match"
    }
    return true
}

fun getScreenWidth(context: Context): Float {
    val resources = context.resources
    val displayMetrics = resources.displayMetrics
    return displayMetrics.widthPixels / displayMetrics.density

}