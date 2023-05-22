package com.app.kitchef.domain.repository

import androidx.lifecycle.LiveData
import com.app.kitchef.data.db.CurrentRecipeDao
import com.app.kitchef.data.db.entity.recipeModel.Recipe
import com.app.kitchef.data.network.ApiResponse
import com.app.kitchef.data.network.recipe.RecipeNetworkDataSource
import com.app.kitchef.domain.utils.DataMapper
import com.app.kitchef.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecipeRepositoryImpl(
    private val currentRecipeDao: CurrentRecipeDao,
    private val recipeNetworkDataSource: RecipeNetworkDataSource
): RecipeRepository {

    override fun fetchRecipe(ingredient: String, ingrNb: Int): Flow<Resource<List<Recipe>>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = recipeNetworkDataSource.fetchCurrentRecipe(ingredient, ingrNb).first()) {
            is ApiResponse.Success -> {
                val data = apiResponse.data
                emit(Resource.Success(DataMapper.mapRecipeHitResponseToRecipe(data)))
            }
            is ApiResponse.Empty -> {
                emit(Resource.Success(listOf()))
            }
            is ApiResponse.Error -> {
                emit(Resource.Error(apiResponse.errorMessage))
            }
        }
    }

    override fun getRandomRecipe(): Flow<Resource<List<com.app.kitchef.domain.model.Recipe>>> = flow {
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

    override suspend fun persistFetchedCurrentRecipe(fetchedRecipe: Recipe) {
        GlobalScope.launch(Dispatchers.IO) {
            currentRecipeDao.upsert(fetchedRecipe)
        }
    }

    override suspend fun getPersistedRecipe() : LiveData<Recipe> {
        return withContext(Dispatchers.IO) {
            return@withContext currentRecipeDao.getRecipe()
        }
    }
}