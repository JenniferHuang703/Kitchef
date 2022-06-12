package com.example.testing.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testing.data.db.entity.ingredientModel.CURRENT_INGREDIENT_ID
import com.example.testing.data.db.entity.ingredientModel.FoodX

@Dao
interface CurrentIngredientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(ingredientEntry: FoodX)

    @Query("select * from current_ingredient where id=$CURRENT_INGREDIENT_ID")
    fun getIngredient(): LiveData<FoodX>
}