package com.app.kitchef.domain.repository

import androidx.lifecycle.LiveData
import com.app.kitchef.data.db.entity.recipeModel.Recipe
import com.app.kitchef.domain.model.RecipeDetail
import com.app.kitchef.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    fun getRandomRecipe(): Flow<Resource<List<com.app.kitchef.domain.model.Recipe>>>
    fun getRecipesByIngredients(ingredients: String): Flow<Resource<List<com.app.kitchef.domain.model.Recipe>>>
    fun getRecipeDetail(recipeId: Int): Flow<Resource<RecipeDetail>>
    suspend fun persistFetchedCurrentRecipe(fetchedRecipe: Recipe)
    suspend fun getPersistedRecipe() : LiveData<Recipe>
}