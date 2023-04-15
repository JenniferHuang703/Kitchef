package com.app.kitchef.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.kitchef.data.db.CurrentRecipeDao
import com.app.kitchef.data.db.entity.recipeModel.Recipe
import com.app.kitchef.data.network.recipe.RecipeNetworkDataSource
import com.app.kitchef.data.network.recipe.RecipeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecipeRepositoryImpl(
    private val currentRecipeDao: CurrentRecipeDao,
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

    override suspend fun fetchRecipe(ingredient: String, ingrNb: Int) {
        recipeNetworkDataSource.fetchCurrentRecipe(ingredient, ingrNb)
    }

    override suspend fun getCurrentRecipe(): LiveData<out RecipeResponse> {
        return withContext(Dispatchers.IO) {
            return@withContext recipeInfo
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