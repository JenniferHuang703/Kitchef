package com.app.kitchef.presentation.ui.authentication

import android.view.View
import androidx.navigation.fragment.findNavController
import com.app.kitchef.databinding.FragmentSignUpBinding

class SignUpFragment :AuthenticationBaseFragment<FragmentSignUpBinding>() {

    override fun setViewBinding(): FragmentSignUpBinding {
        return FragmentSignUpBinding.inflate(layoutInflater)
    }

    override fun setUpViews() {
        super.setUpViews()

        binding.signUpBtn.setOnClickListener(object : OnClickListener {
            override fun onClick(view: View?) {
                handleSignUp()
                launchMainActivity()
            }
        })
    }

    private fun handleSignUp() {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        viewModel.signUp(email, password)
    }

    private fun launchMainActivity(){
        val directions = SignUpFragmentDirections.actionSignUpFragmentToMainActivity()
        findNavController().navigate(directions)
    }
}