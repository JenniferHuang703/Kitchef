package com.app.kitchef.presentation.ui.recommendedRecipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.kitchef.domain.repository.RecipeRepository
import com.app.kitchef.internal.lazyDeferred
import kotlinx.coroutines.launch

class RecommendedRecipesViewModel(
    private val recipeRepository: RecipeRepository
): ViewModel() {

    val recipe by lazyDeferred {
        recipeRepository.getCurrentRecipe()
    }

    fun fetchRecipes(ingredient: String, ingrNb: Int) = viewModelScope.launch  {
        recipeRepository.fetchRecipe(ingredient, ingrNb)
    }

}