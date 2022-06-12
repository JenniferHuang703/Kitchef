package com.example.testing.data.network.recipe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testing.domain.api.RecipeApiService
import com.example.testing.internal.NoConnectivityException

class RecipeNetworkDataSourceImpl(
    private val recipeApiService: RecipeApiService
) : RecipeNetworkDataSource {

    private val _downloadedCurrentRecipe = MutableLiveData<RecipeResponse>()
    override val downloadedCurrentRecipe: LiveData<RecipeResponse>
        get() = _downloadedCurrentRecipe

    override suspend fun fetchCurrentRecipe(ingredient: String, ingrNumber: Int) {
        try {
            val fetchCurrentIngredient = recipeApiService
                .getRecipe(ingredient, ingrNumber)
                .await()
            _downloadedCurrentRecipe.postValue(fetchCurrentIngredient)
        }
        catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection", e)
        }
    }


}