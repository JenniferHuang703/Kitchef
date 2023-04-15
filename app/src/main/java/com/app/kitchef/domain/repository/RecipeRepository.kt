package com.app.kitchef.domain.repository

import androidx.lifecycle.LiveData
import com.app.kitchef.data.db.entity.recipeModel.Recipe
import com.app.kitchef.data.network.recipe.RecipeResponse

interface RecipeRepository {

    val recipeInfo: LiveData<RecipeResponse>

    suspend fun getCurrentRecipe(): LiveData<out RecipeResponse>
    suspend fun fetchRecipe(ingredient: String, ingrNb: Int)
    suspend fun persistFetchedCurrentRecipe(fetchedRecipe: Recipe)
    suspend fun getPersistedRecipe() : LiveData<Recipe>
}