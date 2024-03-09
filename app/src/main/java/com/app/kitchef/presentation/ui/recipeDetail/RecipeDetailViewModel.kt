package com.app.kitchef.presentation.ui.recipeDetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.app.kitchef.data.Result
import com.app.kitchef.domain.model.FavoriteRecipe
import com.app.kitchef.domain.model.RecipeDetail
import com.app.kitchef.domain.repository.RecipeRepository
import com.app.kitchef.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

private const val TAG = "RecipeDetailViewModel"

class RecipeDetailViewModel(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    val favoriteMediatorLiveData = MediatorLiveData<List<FavoriteRecipe>>()

    private var currentFavoriteRecipeList: LiveData<List<FavoriteRecipe>> =
        recipeRepository.getPersistedFavoriteRecipeList().asLiveData()

    private val _isLiked = MutableLiveData<Boolean>()
    val isLiked: LiveData<Boolean> get() = _isLiked

    init {
        _isLiked.value = false
        favoriteMediatorLiveData.addSource(currentFavoriteRecipeList) {
            favoriteMediatorLiveData.value = it
        }
    }

    fun getRecipeDetail(recipeId: Int): Flow<Resource<RecipeDetail>> = flow {
        recipeRepository.getRecipeDetail(recipeId).collect {
            emit(it)
        }
    }

    fun setLike(recipeId: Int) {
        viewModelScope.launch {
            recipeRepository.getPersistedFavoriteRecipeList().collect { favoriteRecipeList ->
                _isLiked.value =
                    favoriteRecipeList.any { favoriteRecipe -> favoriteRecipe.dishId == recipeId }
            }
            Log.d(TAG, "Setting Like: Success, value = ${_isLiked.value}")
        }
    }

    fun toggleLikeRecipe(recipe: RecipeDetail) = viewModelScope.launch(Dispatchers.IO) {
        if (_isLiked.value == true) {
            recipeRepository.removeFavoriteRecipe(recipe.dishId)
        } else {
            recipeRepository.persistFetchedCurrentRecipe(recipe)
        }
    }

}