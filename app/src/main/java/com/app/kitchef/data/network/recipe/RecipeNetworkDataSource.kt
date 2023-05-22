package com.app.kitchef.data.network.recipe

import com.app.kitchef.data.db.entity.recipeModel.Hit
import com.app.kitchef.data.db.entity.spoonacularModel.GetRandomRecipesInformationResponse
import com.app.kitchef.data.network.ApiResponse
import kotlinx.coroutines.flow.Flow

interface RecipeNetworkDataSource {

    suspend fun fetchCurrentRecipe(ingredient: String, ingrNumber: Int): Flow<ApiResponse<List<Hit>>>

    suspend fun getRandomRecipes(): Flow<ApiResponse<List<GetRandomRecipesInformationResponse>>>
}