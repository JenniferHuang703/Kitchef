package com.app.kitchef.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.kitchef.data.db.entity.recipeModel.CURRENT_RECIPE_ID
import com.app.kitchef.data.db.entity.recipeModel.Recipe

@Dao
interface CurrentRecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(recipeEntry: Recipe)

    @Query("select * from current_recipe where id=$CURRENT_RECIPE_ID")
    fun getRecipe(): LiveData<Recipe>
}