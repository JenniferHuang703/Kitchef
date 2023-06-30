package com.app.kitchef.domain.repository

import com.app.kitchef.domain.model.FavoriteRecipe
import com.app.kitchef.domain.model.Recipe
import com.app.kitchef.domain.model.RecipeDetail
import com.app.kitchef.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    fun getRandomRecipe(): Flow<Resource<List<Recipe>>>
    fun getRecipesByIngredients(ingredients: String): Flow<Resource<List<Recipe>>>
    fun getRecipeDetail(recipeId: Int): Flow<Resource<RecipeDetail>>
    suspend fun persistFetchedCurrentRecipe(fetchedRecipe: RecipeDetail)
    suspend fun removeFavoriteRecipe(recipeId: Int)
    fun getPersistedFavoriteRecipeList() : Flow<List<FavoriteRecipe>>
}