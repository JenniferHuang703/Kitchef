package com.example.testing.presentation.ui.recommendedRecipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testing.domain.repository.RecipeRepository

class RecommendedRecipesViewModelFactory(
    private val recipeRepository: RecipeRepository
): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RecommendedRecipesViewModel(recipeRepository) as T
    }

}