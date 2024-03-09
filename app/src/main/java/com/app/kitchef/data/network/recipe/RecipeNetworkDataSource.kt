package com.app.kitchef.data.network.recipe

import com.app.kitchef.data.db.entity.spoonacularModel.GetRandomRecipesInformationResponse
import com.app.kitchef.data.db.entity.spoonacularModel.GetRecipeDetailResponse
import com.app.kitchef.data.db.entity.spoonacularModel.GetRecipesByIngredientsResponseItem
import com.app.kitchef.data.network.ApiResponse
import kotlinx.coroutines.flow.Flow

interface RecipeNetworkDataSource {

    suspend fun getRandomRecipes(): Flow<ApiResponse<List<GetRandomRecipesInformationResponse>>>
    suspend fun getSearchedRecipes(): Flow<ApiResponse<List<GetRandomRecipesInformationResponse>>>
    suspend fun getRecipesByIngredients(ingredients: String): Flow<ApiResponse<List<GetRecipesByIngredientsResponseItem>>>
    suspend fun getRecipeDetail(recipeId: Int): Flow<ApiResponse<GetRecipeDetailResponse>>
}