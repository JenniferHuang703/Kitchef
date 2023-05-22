package com.app.kitchef.data.network.recipe

import com.app.kitchef.data.db.entity.recipeModel.Hit
import com.app.kitchef.data.db.entity.spoonacularModel.GetRandomRecipesInformationResponse
import com.app.kitchef.data.network.ApiResponse
import com.app.kitchef.domain.api.RecipeApiService
import com.app.kitchef.domain.api.SpoonacularApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class RecipeNetworkDataSourceImpl(
    private val recipeApiService: RecipeApiService,
    private val spoonacularApiService: SpoonacularApiService
) : RecipeNetworkDataSource {

    override suspend fun  fetchCurrentRecipe(ingredient: String, ingrNumber: Int): Flow<ApiResponse<List<Hit>>> {
        return flow {
            try {
                val response = recipeApiService.getRecipe(ingredient, ingrNumber)
                val recipes = response.hits
                if(recipes.isNotEmpty()) {
                    emit(ApiResponse.Success(recipes))
                } else {
                    emit(ApiResponse.Empty)
                }
            }
            catch (exception: Exception) {
                emit(ApiResponse.Error("get random recipes went wrong: $exception"))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getRandomRecipes(): Flow<ApiResponse<List<GetRandomRecipesInformationResponse>>> {
        return flow {
            try {
                val response = spoonacularApiService.getRandomRecipes(15)
                val recipes = response.recipes
                if(recipes.isNotEmpty()) {
                    emit(ApiResponse.Success(recipes))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (exception: Exception) {
                emit(ApiResponse.Error("get random recipes went wrong: $exception"))
            }
        }.flowOn(Dispatchers.IO)
    }
}