package com.example.meditasyonapp.feature.login.presentation

import android.text.Editable
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    companion object {
        private const val MIN_USERNAME_LENGTH = 2
        private const val MIN_PASSWORD_LENGTH = 6
        private const val REGEX_AT_LEAST_ONE_UPPERCASE = "(?=.*[A-Z])"
        private const val REGEX_AT_LEAST_ONE_DIGIT = "(?=.*\\d)"
    }

    fun checkUsernameValid(username: Editable?): Boolean = username?.let { it.length > MIN_USERNAME_LENGTH  } ?: false

    fun checkPasswordValid(password: Editable?): Boolean = password?.let { it.length >= MIN_PASSWORD_LENGTH
            && it.contains(REGEX_AT_LEAST_ONE_UPPERCASE.toRegex()) && it.contains(REGEX_AT_LEAST_ONE_DIGIT.toRegex()) } ?: false
}