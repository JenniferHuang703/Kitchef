package com.app.kitchef.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.kitchef.data.db.entity.recipeModel.FavoriteRecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrentRecipeDao {

    @Query("SELECT * FROM favorite_recipe")
    fun getAllRecipe(): Flow<List<FavoriteRecipeEntity>>

    @Query("SELECT * FROM favorite_recipe where recipeId= :recipeId")
    fun getRecipe(recipeId: Int): Flow<FavoriteRecipeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setFavoriteRecipe(favoriteRecipe: FavoriteRecipeEntity)

    @Query("DELETE FROM favorite_recipe WHERE recipeId = :recipeId")
    fun removeFavoriteRecipe(recipeId: Int)
}