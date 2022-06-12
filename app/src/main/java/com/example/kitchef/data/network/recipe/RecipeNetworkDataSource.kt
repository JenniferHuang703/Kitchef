package com.example.kitchef.data.network.recipe

import androidx.lifecycle.LiveData

interface RecipeNetworkDataSource {

    val downloadedCurrentRecipe: LiveData<RecipeResponse>

    suspend fun fetchCurrentRecipe(
        ingredient: String,
        ingrNumber: Int
    )
}