package com.app.kitchef.presentation.ui.recipeBook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.kitchef.databinding.FragmentRecipeBookBinding
import com.app.kitchef.domain.utils.Resource
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.navigation.fragment.findNavController
import com.app.kitchef.domain.model.Recipe

class RecipeBookFragment : Fragment() {

    private val viewModel by viewModel<RecipeBookViewModel>()
    private var _binding: FragmentRecipeBookBinding? = null
    private var recipesList = ArrayList<Recipe>()
    private val binding get() = _binding!!
    private lateinit var recipeBookAdapter: RecipeBookAdapter

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
                        resource.data?.let{
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

    private fun handleRecyclerView() {
        val rv = binding.recyclerview
        recipeBookAdapter = RecipeBookAdapter(recipesList)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = recipeBookAdapter

        recipeBookAdapter.setOnClickListener(object :
            RecipeBookAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val action = RecipeBookFragmentDirections.actionRecipeBookFragmentToRecipeDetailFragment()
                action.recipeId = recipesList[position].recipeId
                findNavController().navigate(action)
            }
        })
    }
}