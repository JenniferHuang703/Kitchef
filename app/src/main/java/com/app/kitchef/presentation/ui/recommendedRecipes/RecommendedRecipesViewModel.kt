package com.app.kitchef.presentation.ui.recommendedRecipes

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
        recipeRepository.getRecipesByIngredients(ingredient).collect {
            emit(it)
        }
    }
}