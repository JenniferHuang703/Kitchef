package com.app.kitchef.domain.repository

import androidx.lifecycle.LiveData
import com.app.kitchef.data.network.ingredient.IngredientResponse

interface AddIngredientRepository {

    val searchedIngredientInfo: LiveData<IngredientResponse>

    suspend fun getCurrentIngredient(): LiveData<out IngredientResponse>
    suspend fun fetchIngredient(ingredient: String)
}

