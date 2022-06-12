package com.example.testing.domain.repository

import androidx.lifecycle.LiveData
import com.example.testing.data.network.ingredient.IngredientResponse

interface AddIngredientRepository {

    val searchedIngredientInfo: LiveData<IngredientResponse>

    suspend fun getCurrentIngredient(): LiveData<out IngredientResponse>
    suspend fun fetchIngredient(ingredient: String)
}

