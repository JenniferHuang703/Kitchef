package com.example.kitchef.data.network.ingredient

import androidx.lifecycle.LiveData

interface IngredientNetworkDataSource {

    val downloadedCurrentIngredient: LiveData<IngredientResponse>

    suspend fun fetchCurrentIngredient(
        ingredient: String,
        nutritionType: String
    )
}