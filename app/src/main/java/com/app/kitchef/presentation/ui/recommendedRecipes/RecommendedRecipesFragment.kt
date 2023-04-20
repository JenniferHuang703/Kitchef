package com.app.kitchef.presentation.ui.recommendedRecipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.kitchef.R
import com.app.kitchef.data.db.entity.recipeModel.Recipe
import com.app.kitchef.databinding.FragmentRecommendedRecipesBinding
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
    ): View? {
        _binding = FragmentRecommendedRecipesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var ingrNb = 2
        if (args.ingredientList.size != 1)
            ingrNb = args.ingredientList.size

        viewModel.fetchRecipes(addedIngredientList, ingrNb)

        bindUI()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        convertIngredientToURL(args.ingredientList)
        handleRecyclerView(view)
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

    private fun handleRecyclerView(view: View) {
        rv = view.findViewById(R.id.recyclerview)
        recommendedRecipesAdapter = RecommendedRecipesAdapter(recipesList)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = recommendedRecipesAdapter

        recommendedRecipesAdapter.setOnClickListener(object :
            RecommendedRecipesAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val directions = RecommendedRecipesFragmentDirections.actionRecommendedRecipesFragmentToRecipeDetailFragment(
                    recipesList[position]
                )
                findNavController().navigate(directions)
            }
        })
    }

    private fun bindUI() = launch{
        val currentRecipe = viewModel.recipe.await()

        currentRecipe.observe(viewLifecycleOwner, Observer { recipeResponse ->
            if (recipeResponse == null) return@Observer
            recipesList.clear()
            recipeResponse.hits.forEach {
                val recipe = Recipe(it.recipe.cuisineType,it.recipe.image, it.recipe.ingredientLines,it.recipe.label )
                recipesList.add(recipe)
            }
            binding.recyclerview.adapter?.notifyDataSetChanged()
        })
    }

//    private fun fetchData() {
//        val apiService = RecipeApiService(ConnectivityInterceptorImpl(this.requireContext()))
//        val recipeNetworkDataSource = RecipeNetworkDataSourceImpl(apiService)
//
//        recipeNetworkDataSource.downloadedCurrentRecipe.observe(viewLifecycleOwner, Observer { recipeResponse ->
//            recipeResponse.hits.forEach {
//                val recipe = Recipe(it.recipe.cuisineType,it.recipe.image, it.recipe.ingredientLines,it.recipe.label )
//                recipesList.add(recipe)
//                Log.d("jennifer", recipe.toString())
//            }
//        })
//
//
//        GlobalScope.launch(Dispatchers.Main) {
//            recipeNetworkDataSource.fetchCurrentRecipe("Carrot%2CEgg", 2)
//        }
//    }
}