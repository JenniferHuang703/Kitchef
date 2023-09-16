package com.app.kitchef.domain.repository

import com.app.kitchef.data.network.ApiResponse
import com.app.kitchef.data.network.ingredient.IngredientNetworkDataSource
import com.app.kitchef.domain.model.Ingredient
import com.app.kitchef.domain.utils.DataMapper
import com.app.kitchef.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class AddIngredientRepositoryImpl(
    private val ingredientNetworkDataSource: IngredientNetworkDataSource
) : AddIngredientRepository {

    override suspend fun fetchIngredient(ingredient: String): Flow<Resource<Ingredient>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = ingredientNetworkDataSource.getSearchedIngredient(ingredient).first()) {
            is ApiResponse.Success -> {
                val data = apiResponse.data
                emit(Resource.Success(DataMapper.mapIngredientSearchResponseToIngredient(data)))
            }

            is ApiResponse.Empty -> {
                emit(Resource.Success(Ingredient(0,"","")))
            }

            is ApiResponse.Error -> {
                emit(Resource.Error(apiResponse.errorMessage))
            }
        }
    }

}