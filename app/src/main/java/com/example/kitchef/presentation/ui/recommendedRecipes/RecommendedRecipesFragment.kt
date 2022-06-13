package com.example.kitchef.presentation.ui.recommendedRecipes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kitchef.R
import com.example.kitchef.data.db.entity.recipeModel.Recipe
import com.example.kitchef.data.network.ConnectivityInterceptorImpl
import com.example.kitchef.data.network.recipe.RecipeNetworkDataSourceImpl
import com.example.kitchef.domain.api.RecipeApiService
import com.example.kitchef.domain.model.Ingredient
import com.example.kitchef.presentation.ui.base.ScopeFragment
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class RecommendedRecipesFragment : ScopeFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: RecommendedRecipesViewModelFactory by instance()
    private lateinit var viewModel: RecommendedRecipesViewModel
    private lateinit var rv: RecyclerView
    private lateinit var recommendedRecipesAdapter: RecommendedRecipesAdapter
    private var recipesList = ArrayList<Recipe>()
    private var navController: NavController? = null
    private val args by navArgs<RecommendedRecipesFragmentArgs>()
    private var addedIngredientList = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recommended_recipes, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(RecommendedRecipesViewModel::class.java)

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
            recyclerview.adapter?.notifyDataSetChanged()
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