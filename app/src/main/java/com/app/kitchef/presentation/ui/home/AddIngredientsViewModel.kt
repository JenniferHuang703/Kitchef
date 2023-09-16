package com.app.kitchef.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.app.kitchef.domain.model.Ingredient
import com.app.kitchef.domain.repository.AddIngredientRepository
import com.app.kitchef.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class AddIngredientsViewModel(
    private val ingredientRepository: AddIngredientRepository
) : ViewModel() {

    val ingredientMediatorLiveData = MediatorLiveData<List<Ingredient>>()

    private var currentIngredientList: LiveData<List<Ingredient>> =
        ingredientRepository.getPersistedIngredientList().asLiveData()

    init {
        ingredientMediatorLiveData.addSource(currentIngredientList) {
            ingredientMediatorLiveData.value = it
        }
    }

    fun getSearchedIngredient(ingredient: String): Flow<Resource<Ingredient>> =
        flow {
            ingredientRepository.fetchIngredient(ingredient).collect {
                emit(it)
            }
        }

    fun toggleAddIngredientToList(ingredient: Ingredient) = viewModelScope.launch(Dispatchers.IO) {
        ingredientRepository.persistAddedIngredient(ingredient)
    }

    fun toggleRemoveIngredientToList(ingredientId: Int) = viewModelScope.launch(Dispatchers.IO) {
        ingredientRepository.removePersistedIngredient(ingredientId)
    }
}