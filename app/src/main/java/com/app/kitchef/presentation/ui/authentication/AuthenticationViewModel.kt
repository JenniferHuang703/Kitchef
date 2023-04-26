package com.app.kitchef.presentation.ui.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.kitchef.domain.repository.AuthenticationRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class AuthenticationViewModel(private val authRepository: AuthenticationRepository) : ViewModel() {

    fun login(email: String, password: String) {
        if(email.isBlank() || password.isBlank()) {
            //error
        } else {
            // if email not valid
            //else
            viewModelScope.launch {
                val res = async { authRepository.loginWithEmail(email, password) }
            }
        }
    }

    fun signUp(email: String, password: String) {
        if(email.isBlank() || password.isBlank()) {
            //error
        } else {
            // if email not valid
            //else
            viewModelScope.launch {
                val res = async { authRepository.signUpWithEmail(email, password) }
            }
        }
    }

    fun signOut() {
        authRepository.signOut()
    }
}
