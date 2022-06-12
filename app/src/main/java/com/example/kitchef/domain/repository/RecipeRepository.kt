package com.example.kitchef.domain.repository

import androidx.lifecycle.LiveData
import com.example.kitchef.data.network.recipe.RecipeResponse

interface RecipeRepository {

    val recipeInfo: LiveData<RecipeResponse>

    suspend fun getCurrentRecipe(): LiveData<out RecipeResponse>
    suspend fun fetchRecipe(ingredient: String, ingrNb: Int)
}