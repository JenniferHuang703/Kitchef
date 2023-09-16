package com.app.kitchef.domain.repository

import com.app.kitchef.domain.model.Ingredient
import com.app.kitchef.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AddIngredientRepository {

    suspend fun fetchIngredient(ingredient: String): Flow<Resource<Ingredient>>
    suspend fun persistAddedIngredient(ingredient: Ingredient)
    suspend fun removePersistedIngredient(ingredientId: Int)
    fun getPersistedIngredientList() : Flow<List<Ingredient>>
}

