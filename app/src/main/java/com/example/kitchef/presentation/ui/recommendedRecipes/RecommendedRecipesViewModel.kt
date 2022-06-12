package com.example.kitchef.presentation.ui.recommendedRecipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kitchef.domain.repository.RecipeRepository
import com.example.kitchef.internal.lazyDeferred
import kotlinx.coroutines.launch

class RecommendedRecipesViewModel(
    private val recipeRepository: RecipeRepository
): ViewModel() {

    val recipe by lazyDeferred {
        recipeRepository.getCurrentRecipe()
    }

    fun fetchRecipes(ingredient: String) = viewModelScope.launch  {
        recipeRepository.fetchRecipe(ingredient)
    }

}