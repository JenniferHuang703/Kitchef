package com.example.testing.presentation.ui.recommendedRecipes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testing.R
import com.example.testing.common.StaticIngredientList
import com.example.testing.common.StaticRecipeList
import com.example.testing.data.network.ConnectivityInterceptorImpl
import com.example.testing.data.network.recipe.RecipeNetworkDataSourceImpl
import com.example.testing.domain.api.RecipeApiService
import com.example.testing.domain.model.Ingredient
import com.example.testing.domain.model.Recipe
import com.example.testing.presentation.ui.base.ScopeFragment
import com.example.testing.presentation.ui.home.AddIngredientsViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
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
    private var addedIngredientList = ArrayList<Ingredient>()

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
        viewModel.fetchRecipes(addedIngredientList[0].title)

        bindUI()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        addRecipeItemToList()
        addedIngredientList.addAll(args.ingredientList)
        handleRecyclerView(view)
    }

    private fun handleRecyclerView(view: View) {
        rv = view.findViewById(R.id.recyclerview)
        recommendedRecipesAdapter = RecommendedRecipesAdapter(recipesList)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = recommendedRecipesAdapter

        recommendedRecipesAdapter.setOnClickListener(object :
            RecommendedRecipesAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                navController?.navigate(R.id.action_recommendedRecipesFragment_to_recipeDetailFragment)
            }
        })
    }

    private fun bindUI() = launch{
        val currentRecipe = viewModel.recipe.await()

        currentRecipe.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer
            val recipe = Recipe(it.hits[0].recipe.label, it.hits[0].recipe.image)
            recipesList.clear()
            recipesList.add(recipe)
            recyclerview.adapter?.notifyDataSetChanged()
        })
    }

    private fun addRecipeItemToList() {
        if (recipesList.isEmpty())
            recipesList.addAll(StaticRecipeList.predefinedRecipeList)
    }

    private fun fetchData() {
        val apiService = RecipeApiService(ConnectivityInterceptorImpl(this.requireContext()))
        val recipeNetworkDataSource = RecipeNetworkDataSourceImpl(apiService)

        recipeNetworkDataSource.downloadedCurrentRecipe.observe(viewLifecycleOwner, Observer {
            Log.d("jen", it.hits[0].recipe.label)
            Log.d("jen", it.hits[0].recipe.image)
        })

        GlobalScope.launch(Dispatchers.Main) {
            recipeNetworkDataSource.fetchCurrentRecipe("Carrot", 2)
        }
    }
}