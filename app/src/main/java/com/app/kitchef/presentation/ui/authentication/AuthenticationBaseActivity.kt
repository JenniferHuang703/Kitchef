package com.app.kitchef.presentation.ui.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.kitchef.R

class AuthenticationBaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication_base)
    }
}