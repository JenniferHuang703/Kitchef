package com.app.kitchef.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.kitchef.domain.model.Ingredient
import com.app.kitchef.domain.repository.AddIngredientRepository
import com.app.kitchef.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class AddIngredientsViewModel(
    private val ingredientRepository: AddIngredientRepository
) : ViewModel() {

    private var tempList = ArrayList<Ingredient>()
    private val _tempIngredientList = MutableLiveData<ArrayList<Ingredient>>()
    val tempIngredientsList: LiveData<ArrayList<Ingredient>> = _tempIngredientList
    private val _modifiedIngredientList = MutableLiveData<ArrayList<Ingredient>>()
    val modifiedIngredientList: LiveData<ArrayList<Ingredient>> = _modifiedIngredientList
    private var ingList = ArrayList<Ingredient>()

    fun getSearchedIngredient(ingredient: String): Flow<Resource<Ingredient>> =
        flow {
            ingredientRepository.fetchIngredient(ingredient).collect {
                emit(it)
            }
        }

    fun modifyIngredientList(ingredientList: ArrayList<Ingredient>) {
//        _modifiedIngredientList.value = ingredientList
        ingList.clear()
        ingList.addAll(ingredientList)
    }

    fun getModifiedIngredientList() = ingList

}