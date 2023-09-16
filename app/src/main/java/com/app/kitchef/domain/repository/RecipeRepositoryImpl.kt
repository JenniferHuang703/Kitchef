package com.app.kitchef.domain.repository

import com.app.kitchef.data.db.CurrentRecipeDao
import com.app.kitchef.data.network.ApiResponse
import com.app.kitchef.data.network.recipe.RecipeNetworkDataSource
import com.app.kitchef.domain.model.FavoriteRecipe
import com.app.kitchef.domain.model.Recipe
import com.app.kitchef.domain.model.RecipeDetail
import com.app.kitchef.domain.utils.DataMapper
import com.app.kitchef.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class RecipeRepositoryImpl(
    private val currentRecipeDao: CurrentRecipeDao,
    private val recipeNetworkDataSource: RecipeNetworkDataSource
) : RecipeRepository {

    override fun getRandomRecipe(): Flow<Resource<List<Recipe>>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = recipeNetworkDataSource.getRandomRecipes().first()) {
            is ApiResponse.Success -> {
                val data = apiResponse.data
                emit(Resource.Success(DataMapper.mapRecipeResponseToRecipe(data)))
            }

            is ApiResponse.Empty -> {
                emit(Resource.Success(listOf()))
            }

            is ApiResponse.Error -> {
                emit(Resource.Error(apiResponse.errorMessage))
            }
        }
    }

    override fun getRecipesByIngredients(ingredients: String): Flow<Resource<List<Recipe>>> = flow {
        emit(Resource.Loading())
        when (val apiResponse =
            recipeNetworkDataSource.getRecipesByIngredients(ingredients).first()) {
            is ApiResponse.Success -> {
                val data = apiResponse.data
                emit(Resource.Success(DataMapper.mapRecipesByIngredientsResponseToRecipe(data)))
            }

            is ApiResponse.Empty -> {
                emit(Resource.Success(listOf()))
            }

            is ApiResponse.Error -> {
                emit(Resource.Error(apiResponse.errorMessage))
            }
        }
    }

    override fun getRecipeDetail(recipeId: Int): Flow<Resource<RecipeDetail>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = recipeNetworkDataSource.getRecipeDetail(recipeId).first()) {
            is ApiResponse.Success -> {
                val data = apiResponse.data
                emit(Resource.Success(DataMapper.mapRecipeDetail(data)))
            }

            is ApiResponse.Empty -> {
                emit(Resource.Error("Error"))
            }

            is ApiResponse.Error -> {
                emit(Resource.Error(apiResponse.errorMessage))
            }
        }
    }

    override suspend fun persistFetchedCurrentRecipe(fetchedRecipe: RecipeDetail) {
        currentRecipeDao.setFavoriteRecipe(DataMapper.changeRecipeDetailToFavoriteRecipe(fetchedRecipe))
    }

    override suspend fun removeFavoriteRecipe(recipeId: Int) {
        currentRecipeDao.removeFavoriteRecipe(recipeId)
    }

    override fun getPersistedFavoriteRecipeList(): Flow<List<FavoriteRecipe>> {
        return currentRecipeDao.getAllRecipe().map {
            DataMapper.mapFavoriteRecipeEntityToFavoriteRecipe(it)
        }
    }
}