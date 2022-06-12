package com.example.kitchef.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kitchef.data.network.recipe.RecipeNetworkDataSource
import com.example.kitchef.data.network.recipe.RecipeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipeRepositoryImpl(
    private val recipeNetworkDataSource: RecipeNetworkDataSource
): RecipeRepository {

    private val _recipeInfo = MutableLiveData<RecipeResponse>()
    override val recipeInfo: LiveData<RecipeResponse>
        get() = _recipeInfo

    init {
        recipeNetworkDataSource.downloadedCurrentRecipe.observeForever { currentRecipe ->
            _recipeInfo.postValue(currentRecipe)
        }
    }

    override suspend fun fetchRecipe(ingredient: String) {
        recipeNetworkDataSource.fetchCurrentRecipe(ingredient, 2)
    }

    override suspend fun getCurrentRecipe(): LiveData<out RecipeResponse> {
        return withContext(Dispatchers.IO) {
            return@withContext recipeInfo
        }
    }

}