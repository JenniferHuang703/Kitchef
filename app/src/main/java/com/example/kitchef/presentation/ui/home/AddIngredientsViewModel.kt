package com.example.kitchef.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kitchef.domain.model.Ingredient
import com.example.kitchef.domain.repository.AddIngredientRepository
import com.example.kitchef.internal.lazyDeferred
import kotlinx.coroutines.launch

class AddIngredientsViewModel(
    private val ingredientRepository: AddIngredientRepository
) : ViewModel() {

    private var tempList = ArrayList<Ingredient>()
    private val _tempIngredientList = MutableLiveData<ArrayList<Ingredient>>()
    val tempIngredientsList: LiveData<ArrayList<Ingredient>> = _tempIngredientList
    private val _modifiedIngredientList = MutableLiveData<ArrayList<Ingredient>>()
    val modifiedIngredientList: LiveData<ArrayList<Ingredient>> = _modifiedIngredientList

    val ingredient by lazyDeferred {
        ingredientRepository.getCurrentIngredient()
    }

//    suspend fun fetch(ingredient: String) {
//        ingredientRepository.fetchIngredient(ingredient)
//    }

    fun searchIngredientOnQueryTextSubmit(query: String?, ingredientsList: ArrayList<Ingredient>) = viewModelScope.launch {
        val containText = ingredientsList.filter { it.title.lowercase() == query }
        if (containText.isNotEmpty()) {
            tempList.add(containText[0])
            _tempIngredientList.value = tempList
        } else {
            ingredientRepository.fetchIngredient(query ?: "broccoli")
        }
    }

    fun searchIngredientOnQueryTextChange(newText: String?, ingredientsList: ArrayList<Ingredient>) {
        tempList.clear()
        val searchText = newText!!.lowercase()
        if (searchText.isNotEmpty()) {
            ingredientsList.forEach { ingredient ->
                if (ingredient.title.lowercase().contains(searchText)) {
                    tempList.add(ingredient)
                }
            }
            _tempIngredientList.value = tempList
        } else {
            tempList.clear()
            tempList.addAll(ingredientsList)
            _tempIngredientList.value = tempList
        }
    }

    fun modifyIngredientList(ingredientList: ArrayList<Ingredient>) {
        _modifiedIngredientList.value = ingredientList
    }
}