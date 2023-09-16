package com.app.kitchef.data.network.ingredient

import com.app.kitchef.data.db.entity.spoonacularModel.IngredientSearchInformationResponse
import com.app.kitchef.data.db.entity.spoonacularModel.IngredientSearchResponse
import com.app.kitchef.data.network.ApiResponse
import com.app.kitchef.domain.api.SpoonacularIngredientApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class IngredientNetworkDataSourceImpl(
    private val ingredientApiService: SpoonacularIngredientApiService
) : IngredientNetworkDataSource {

    override suspend fun getSearchedIngredient(ingredient: String): Flow<ApiResponse<IngredientSearchInformationResponse>> {
        return flow {
            try {
                val response = ingredientApiService.getSearchedIngredient(ingredient, 1)
                val ingredients = response.results
                if (ingredients.isNotEmpty()) {
                    emit(ApiResponse.Success(ingredients[0]))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (exception: Exception) {
                emit(ApiResponse.Error("get searched ingredient went wrong: $exception"))
            }
        }.flowOn(Dispatchers.IO)




    }
}