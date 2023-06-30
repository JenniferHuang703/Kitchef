package com.app.kitchef.presentation.ui.recipeDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.app.kitchef.domain.model.FavoriteRecipe
import com.app.kitchef.domain.model.RecipeDetail
import com.app.kitchef.domain.repository.RecipeRepository
import com.app.kitchef.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class RecipeDetailViewModel(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    val favoriteMediatorLiveData = MediatorLiveData<List<FavoriteRecipe>>()

    private var currentFavoriteRecipeList: LiveData<List<FavoriteRecipe>> =
        recipeRepository.getPersistedFavoriteRecipeList().asLiveData()

    init {
        favoriteMediatorLiveData.addSource(currentFavoriteRecipeList) {
            favoriteMediatorLiveData.value = it
        }
    }

    fun getRecipeDetail(recipeId: Int): Flow<Resource<RecipeDetail>> = flow {
        recipeRepository.getRecipeDetail(recipeId).collect {
            emit(it)
        }
    }

    fun persistRecipe(recipe: RecipeDetail) = viewModelScope.launch(Dispatchers.IO) {
        recipeRepository.persistFetchedCurrentRecipe(recipe)
    }

    fun removeFavoriteRecipe(recipeId: Int) = viewModelScope.launch(Dispatchers.IO) {
        recipeRepository.removeFavoriteRecipe(recipeId)
    }
}