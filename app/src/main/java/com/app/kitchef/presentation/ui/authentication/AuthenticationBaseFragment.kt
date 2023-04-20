package com.app.kitchef.presentation.ui.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.viewbinding.ViewBinding

abstract class AuthenticationBaseFragment<VBinding: ViewBinding> : Fragment() {

    protected val viewModel: AuthenticationViewModel by activityViewModels()

    protected lateinit var binding: VBinding
    protected abstract fun setViewBinding(): VBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setUpViews()
        observeViews()
        return binding.root
    }

    open fun setUpViews() {}

    open fun observeViews() {}

    private fun init() {
        binding = setViewBinding()
    }

    interface OnClickListener: View.OnClickListener
}