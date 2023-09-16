package com.app.kitchef.data.network.ingredient

import com.app.kitchef.data.db.entity.spoonacularModel.IngredientSearchInformationResponse
import com.app.kitchef.data.network.ApiResponse
import kotlinx.coroutines.flow.Flow

interface IngredientNetworkDataSource {

    suspend fun getSearchedIngredient(ingredient: String): Flow<ApiResponse<IngredientSearchInformationResponse>>
}