package com.example.kitchef.presentation.ui.recipeDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kitchef.domain.repository.RecipeRepository

class RecipeDetailViewModelFactory (
    private val recipeRepository: RecipeRepository
    ) : ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return RecipeDetailViewModel(recipeRepository) as T
        }
}