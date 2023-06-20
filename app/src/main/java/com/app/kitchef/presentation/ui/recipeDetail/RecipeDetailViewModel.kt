package com.app.kitchef.presentation.ui.recipeDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.kitchef.domain.model.Recipe
import com.app.kitchef.domain.model.RecipeDetail
import com.app.kitchef.domain.repository.RecipeRepository
import com.app.kitchef.domain.utils.Resource
import com.app.kitchef.internal.lazyDeferred
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class RecipeDetailViewModel(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    val recipe by lazyDeferred {
        recipeRepository.getPersistedRecipe()
    }

    fun getRecipeDetail(recipeId: Int): Flow<Resource<RecipeDetail>> = flow {
        recipeRepository.getRecipeDetail(recipeId).collect {
            emit(it)
        }
    }

    fun persistRecipe(recipe: RecipeDetail) = viewModelScope.launch {
        recipeRepository.persistFetchedCurrentRecipe(recipe)
    }
}