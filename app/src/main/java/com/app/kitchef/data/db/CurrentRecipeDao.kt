package com.app.kitchef.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.kitchef.domain.model.CURRENT_RECIPE_ID
import com.app.kitchef.domain.model.RecipeDetail

@Dao
interface CurrentRecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(recipeEntry: RecipeDetail)

    @Query("select * from current_recipe_detail where id=$CURRENT_RECIPE_ID")
    fun getRecipe(): LiveData<RecipeDetail>
}