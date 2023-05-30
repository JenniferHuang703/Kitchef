package com.app.kitchef.data.network.recipe

import com.app.kitchef.data.db.entity.spoonacularModel.GetRandomRecipesInformationResponse
import com.app.kitchef.data.db.entity.spoonacularModel.GetRecipeDetailResponse
import com.app.kitchef.data.db.entity.spoonacularModel.GetRecipesByIngredientsResponseItem
import com.app.kitchef.data.network.ApiResponse
import com.app.kitchef.domain.api.SpoonacularApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class RecipeNetworkDataSourceImpl(
    private val spoonacularApiService: SpoonacularApiService
) : RecipeNetworkDataSource {

    override suspend fun getRandomRecipes(): Flow<ApiResponse<List<GetRandomRecipesInformationResponse>>> {
        return flow {
            try {
                val response = spoonacularApiService.getRandomRecipes(15)
                val recipes = response.recipes
                if (recipes.isNotEmpty()) {
                    emit(ApiResponse.Success(recipes))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (exception: Exception) {
                emit(ApiResponse.Error("get random recipes went wrong: $exception"))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getRecipesByIngredients(ingredients: String): Flow<ApiResponse<List<GetRecipesByIngredientsResponseItem>>> {
        return flow {
            try {
                val response = spoonacularApiService.getRecipesByIngredients(ingredients, 5)
                val recipes = response.toList()
                if (recipes.isNotEmpty()) {
                    emit(ApiResponse.Success(recipes))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (exception: Exception) {
                emit(ApiResponse.Error("get recipes by ingredients went wrong: $exception"))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getRecipeDetail(recipeId: Int): Flow<ApiResponse<GetRecipeDetailResponse>> {
        return flow {
            try {
                val response = spoonacularApiService.getRecipeDetail(recipeId, false)
                emit(ApiResponse.Success(response))
            } catch (exception: Exception) {
                emit(ApiResponse.Error("get recipe detail went wrong: $exception"))
            }
        }.flowOn(Dispatchers.IO)
    }
}