package com.app.kitchef.presentation.ui.authentication

import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.navigation.fragment.findNavController
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
                launchMainActivity()
            }
        })
        setUpClickableSignUpText()
    }

    private fun setUpClickableSignUpText() {
        val loginText = getString(R.string.sign_up_message)
        val ss = SpannableString(loginText)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
            }
        }

        ss.setSpan(clickableSpan, 23, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.signUpBtn.apply {
            text = ss
            movementMethod = LinkMovementMethod.getInstance()
        }
    }

    private fun handleLogin() {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        viewModel.login(email, password)
    }

    private fun launchMainActivity(){
        val directions = LoginFragmentDirections.actionLoginFragmentToMainActivity()
        findNavController().navigate(directions)
    }
}