package com.app.kitchef.presentation.ui.recommendedRecipes

import android.util.Log
import androidx.lifecycle.ViewModel
import com.app.kitchef.domain.model.Recipe
import com.app.kitchef.domain.repository.RecipeRepository
import com.app.kitchef.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RecommendedRecipesViewModel(
    private val recipeRepository: RecipeRepository
): ViewModel() {

    fun fetchRecipes(ingredient: String) : Flow<Resource<List<Recipe>>> = flow  {
//        recipeRepository.fetchRecipe(ingredient, ingrNb).collect {
//            Log.d("jen", it.data.toString())
//            emit(it)
//        }

        recipeRepository.getRecipesByIngredients(ingredient).collect {
            Log.d("jen", it.data.toString())
            emit(it)
        }
    }
}