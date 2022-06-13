package com.example.kitchef.presentation.ui.recipeDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kitchef.data.db.entity.recipeModel.Recipe
import com.example.kitchef.domain.repository.RecipeRepository
import com.example.kitchef.internal.lazyDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecipeDetailViewModel(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    val recipe by lazyDeferred {
        recipeRepository.getPersistedRecipe()
    }

    fun persistRecipe(recipe: Recipe) = viewModelScope.launch {
        recipeRepository.persistFetchedCurrentRecipe(recipe)
    }
}