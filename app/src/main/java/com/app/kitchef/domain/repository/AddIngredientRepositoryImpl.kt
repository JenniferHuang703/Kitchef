package com.app.kitchef.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.kitchef.data.network.ingredient.IngredientNetworkDataSource
import com.app.kitchef.data.network.ingredient.IngredientResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddIngredientRepositoryImpl(
//    private val currentIngredientDao: CurrentIngredientDao,
    private val ingredientNetworkDataSource: IngredientNetworkDataSource
) : AddIngredientRepository {

    private val _searchedIngredientInfo = MutableLiveData<IngredientResponse>()
    override val searchedIngredientInfo: LiveData<IngredientResponse>
        get() = _searchedIngredientInfo

    init {
        ingredientNetworkDataSource.downloadedCurrentIngredient.observeForever { newCurrentIngredient ->
            _searchedIngredientInfo.postValue(newCurrentIngredient)
        }
    }

    override suspend fun fetchIngredient(ingredient: String) {
        ingredientNetworkDataSource.fetchCurrentIngredient(ingredient, "cooking")
    }

    override suspend fun getCurrentIngredient(): LiveData<out IngredientResponse> {
        return withContext(Dispatchers.IO) {
            return@withContext searchedIngredientInfo
        }
    }


//    init {
//        ingredientNetworkDataSource.downloadedCurrrentIngredient.observeForever{ newCurrentIngredient ->
//            persistFetchedCurrentIngredient(newCurrentIngredient)
//        }
//    }
//
//    override suspend fun getCurrentIngredient(): LiveData<out FoodX> {
//        return withContext(Dispatchers.IO) {
//            return@withContext currentIngredientDao.getIngredient()
//        }
//    }
//
//    private fun persistFetchedCurrentIngredient(fetchedIngredient: IngredientResponse) {
//        GlobalScope.launch(Dispatchers.IO) {
//            currentIngredientDao.upsert(fetchedIngredient.parsed[0].food)
//        }
//    }

}