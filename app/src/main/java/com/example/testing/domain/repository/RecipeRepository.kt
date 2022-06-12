package com.example.testing.domain.repository

import androidx.lifecycle.LiveData
import com.example.testing.data.network.ingredient.IngredientResponse
import com.example.testing.data.network.recipe.RecipeResponse

interface RecipeRepository {

    val recipeInfo: LiveData<RecipeResponse>

    suspend fun getCurrentRecipe(): LiveData<out RecipeResponse>
    suspend fun fetchRecipe(ingredient: String)
}