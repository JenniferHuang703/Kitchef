package com.app.kitchef.presentation.ui.recipeDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.kitchef.data.db.entity.recipeModel.Recipe
import com.app.kitchef.domain.repository.RecipeRepository
import com.app.kitchef.internal.lazyDeferred
import kotlinx.coroutines.launch

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