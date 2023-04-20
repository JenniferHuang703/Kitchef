package com.app.kitchef.presentation.ui.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.kitchef.R
import com.app.kitchef.databinding.FragmentLoginBinding

class LoginFragment : AuthenticationBaseFragment<FragmentLoginBinding>() {

    override fun setViewBinding(): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(layoutInflater)
    }

    override fun observeViews() {
        super.observeViews()

        //TODO observe errors
    }

    override fun setUpViews() {
        super.setUpViews()

        binding.loginBtn.setOnClickListener(object : OnClickListener {
            override fun onClick(view: View?) {
                handleLogin()
            }
        })
    }

    private fun handleLogin() {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

//        viewModel.login(email, password)
    }
}