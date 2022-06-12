package com.example.testing.data.network.recipe

import androidx.lifecycle.LiveData
import com.example.testing.data.network.ingredient.IngredientResponse

interface RecipeNetworkDataSource {

    val downloadedCurrentRecipe: LiveData<RecipeResponse>

    suspend fun fetchCurrentRecipe(
        ingredient: String,
        ingrNumber: Int
    )
}