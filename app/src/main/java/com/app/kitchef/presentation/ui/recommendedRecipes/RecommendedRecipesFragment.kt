package com.app.kitchef.presentation.ui.recommendedRecipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.kitchef.databinding.FragmentRecommendedRecipesBinding
import com.app.kitchef.domain.model.Recipe
import com.app.kitchef.domain.utils.Resource
import com.app.kitchef.presentation.ui.base.ScopeFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RecommendedRecipesFragment : ScopeFragment() {

    private val viewModel by viewModel<RecommendedRecipesViewModel>()
    private lateinit var rv: RecyclerView
    private lateinit var recommendedRecipesAdapter: RecommendedRecipesAdapter
    private var recipesList = ArrayList<Recipe>()
    private var navController: NavController? = null
    private val args by navArgs<RecommendedRecipesFragmentArgs>()
    private var addedIngredientList = ""
    private var _binding: FragmentRecommendedRecipesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecommendedRecipesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        convertIngredientToURL(args.ingredientList)
        observers()
        handleRecyclerView()
        onClickListener()
    }

    private fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.fetchRecipes(addedIngredientList).collect { resource ->
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
    }

    private fun convertIngredientToURL(ingredientList: Array<String>) {
        var ingr_str = ""

        ingredientList.forEach {
            if(it != ingredientList.first())
                ingr_str = "$ingr_str,"
            ingr_str += it.lowercase()
        }

        addedIngredientList = ingr_str
    }

    private fun handleRecyclerView() {
        rv = binding.recyclerview
        recommendedRecipesAdapter = RecommendedRecipesAdapter(recipesList)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = recommendedRecipesAdapter

        recommendedRecipesAdapter.setOnClickListener(object :
            RecommendedRecipesAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val action = RecommendedRecipesFragmentDirections.actionRecommendedRecipesFragmentToRecipeDetailFragment()
                action.recipeId = recipesList[position].recipeId
                findNavController().navigate(action)
            }
        })
    }

    private fun onClickListener() {
        binding.topAppBar.toolbar.title = "Recommended Recipes"

        binding.topAppBar.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}