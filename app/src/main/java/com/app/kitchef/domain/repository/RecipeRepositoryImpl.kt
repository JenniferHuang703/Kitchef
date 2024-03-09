package com.app.kitchef.domain.repository

import android.util.Log
import com.app.kitchef.data.Result
import com.app.kitchef.data.db.CurrentRecipeDao
import com.app.kitchef.data.network.ApiResponse
import com.app.kitchef.data.network.recipe.RecipeNetworkDataSource
import com.app.kitchef.domain.model.FavoriteRecipe
import com.app.kitchef.domain.model.Recipe
import com.app.kitchef.domain.model.RecipeDetail
import com.app.kitchef.domain.utils.DataMapper
import com.app.kitchef.domain.utils.Resource
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.supervisorScope

class RecipeRepositoryImpl(
    private val currentRecipeDao: CurrentRecipeDao,
    private val recipeNetworkDataSource: RecipeNetworkDataSource
) : RecipeRepository {

    companion object {
        private const val TAG = "RecipeRepositoryImpl"
    }

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

    //TODO get recipe with searched text input
    override fun getRecipe(textInput: String): Flow<Resource<List<Recipe>>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = recipeNetworkDataSource.getSearchedRecipes().first()) {
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

    override suspend fun persistFetchedCurrentRecipe(fetchedRecipe: RecipeDetail): Result<Boolean> {
        return supervisorScope {
            val localRes = async {
                Log.d(TAG, "persistFetchedCurrentRecipe: updating recipe to local source")
                currentRecipeDao.setFavoriteRecipe(DataMapper.changeRecipeDetailToFavoriteRecipe(fetchedRecipe))
            }
            try {
                localRes.await()
                Result.Success(true)
            } catch (e: Exception) {
                Error(e)
                Result.Success(false)
            }
        }
    }

    override suspend fun removeFavoriteRecipe(recipeId: Int): Result<Boolean>  {
        return supervisorScope {
            //TODO remove remote if auth is setup
            val localRes = async {
                Log.d(TAG, "onDislikeProduct: updating product to local source")
                currentRecipeDao.removeFavoriteRecipe(recipeId)
            }
            try {
                localRes.await()
                Result.Success(true)
            } catch (e: Exception) {
                Error(e)
                Result.Success(false)
            }
        }
    }

    override fun getPersistedFavoriteRecipeList(): Flow<List<FavoriteRecipe>> {
        return currentRecipeDao.getAllRecipe().map {
            DataMapper.mapFavoriteRecipeEntityToFavoriteRecipe(it)
        }
    }
}