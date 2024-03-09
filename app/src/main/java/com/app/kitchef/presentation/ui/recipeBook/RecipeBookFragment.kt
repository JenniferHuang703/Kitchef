package com.app.kitchef.presentation.ui.recipeBook

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.kitchef.databinding.FragmentRecipeBookBinding
import com.app.kitchef.domain.utils.Resource
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.navigation.fragment.findNavController
import com.app.kitchef.common.StaticIngredientList
import com.app.kitchef.domain.model.Ingredient
import com.app.kitchef.domain.model.Recipe
import com.app.kitchef.presentation.ui.MyOnFocusChangeListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay

class RecipeBookFragment : Fragment() {

    private val viewModel by viewModel<RecipeBookViewModel>()
    private var _binding: FragmentRecipeBookBinding? = null
    private var recipesList = ArrayList<Recipe>()
    private val binding get() = _binding!!
    private lateinit var recipeBookAdapter: RecipeBookAdapter
    private val focusChangeListener = MyOnFocusChangeListener()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getRandomRecipe().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                    }

                    is Resource.Success -> {
                        resource.data?.let {
                            recipesList.addAll(it)
                        }
                        binding.recyclerview.adapter?.notifyDataSetChanged()
                    }

                    is Resource.Error -> {
                        Toast.makeText(requireContext(), "${resource.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }

        handleRecyclerView()
    }

    private fun setHomeTopBar() {
        var lastInput = ""
        val debounceJob: Job? = null
        val uiScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
        binding.homeTopBar.searchFieldTextInput.onFocusChangeListener = focusChangeListener
        binding.homeTopBar.searchFieldTextInput.doAfterTextChanged { editable ->
            if (editable != null) {
                val newtInput = editable.toString()
                debounceJob?.cancel()
                if (lastInput != newtInput && newtInput != "") {
                    lastInput = newtInput
                    uiScope.launch {
                        delay(500)
                        if (lastInput == newtInput) {
                            performSearch(newtInput)
                        }
                    }
                }
            }
        }
        binding.homeTopBar.searchFieldTextInput.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                textView.clearFocus()
                val inputManager =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(textView.windowToken, 0)
                performSearch(textView.text.toString())
                true
            } else {
                false
            }
        }
        binding.homeTopBar.searchFieldTextLayout.setEndIconOnClickListener {
            it.clearFocus()
            binding.homeTopBar.searchFieldTextInput.setText("")
            val inputManager =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(it.windowToken, 0)
            binding.recyclerview.adapter?.notifyDataSetChanged()
        }
    }

    private fun performSearch(query: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getSearchedRecipe(query).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {

                    }

                    is Resource.Success -> {
                        resource.data?.let {

                        }
                        binding.recyclerview.adapter?.notifyDataSetChanged()
                    }

                    is Resource.Error -> {
                        Toast.makeText(requireContext(), "${resource.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    private fun handleRecyclerView() {
        val rv = binding.recyclerview
        recipeBookAdapter = RecipeBookAdapter(recipesList)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = recipeBookAdapter

        recipeBookAdapter.setOnClickListener(object :
            RecipeBookAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val action =
                    RecipeBookFragmentDirections.actionRecipeBookFragmentToRecipeDetailFragment()
                action.recipeId = recipesList[position].recipeId
                findNavController().navigate(action)
            }
        })
    }
}