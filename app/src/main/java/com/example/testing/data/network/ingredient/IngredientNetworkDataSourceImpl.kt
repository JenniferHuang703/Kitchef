package com.example.testing.data.network.ingredient

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testing.domain.api.IngredientApiService
import com.example.testing.internal.NoConnectivityException

class IngredientNetworkDataSourceImpl(
    private val ingredientApiService: IngredientApiService
) : IngredientNetworkDataSource {

    private val _downloadedCurrentIngredient= MutableLiveData<IngredientResponse>()
    override val downloadedCurrentIngredient: LiveData<IngredientResponse>
        get() = _downloadedCurrentIngredient

    override suspend fun fetchCurrentIngredient(ingredient: String, nutritionType: String) {
        try {
            val fetchCurrentIngredient = ingredientApiService
                .getIngredient(ingredient, nutritionType)
                .await()
            _downloadedCurrentIngredient.postValue(fetchCurrentIngredient)
        }
        catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection", e)
        }
    }
}