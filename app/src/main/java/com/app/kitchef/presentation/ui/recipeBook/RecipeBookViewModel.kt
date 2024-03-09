package com.app.kitchef.presentation.ui.recipeBook

import androidx.lifecycle.ViewModel
import com.app.kitchef.domain.model.Recipe
import com.app.kitchef.domain.repository.RecipeRepository
import com.app.kitchef.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RecipeBookViewModel( private val recipeRepository: RecipeRepository): ViewModel() {

    fun getRandomRecipe(): Flow<Resource<List<Recipe>>> = flow {
        recipeRepository.getRandomRecipe().collect {
            emit(it)
        }
    }

    fun getSearchedRecipe(textInput: String): Flow<Resource<List<Recipe>>> =
        flow {
            recipeRepository.getRecipe(textInput).collect {
                emit(it)
            }
        }
}