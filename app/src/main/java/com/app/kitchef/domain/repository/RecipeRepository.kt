package com.app.kitchef.domain.repository

import androidx.lifecycle.LiveData
import com.app.kitchef.data.db.entity.recipeModel.Recipe
import com.app.kitchef.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    fun fetchRecipe(ingredient: String, ingrNb: Int): Flow<Resource<List<Recipe>>>
    fun getRandomRecipe(): Flow<Resource<List<com.app.kitchef.domain.model.Recipe>>>
    suspend fun persistFetchedCurrentRecipe(fetchedRecipe: Recipe)
    suspend fun getPersistedRecipe() : LiveData<Recipe>
}