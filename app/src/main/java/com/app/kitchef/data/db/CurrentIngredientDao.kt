package com.app.kitchef.data.db

import androidx.room.Dao
import androidx.room.Query
import com.app.kitchef.data.db.entity.ingredientModel.IngredientEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrentIngredientDao {

    @Query("select * from current_ingredient where ingredientId= :ingredientId")
    fun getIngredient(ingredientId: Int): Flow<IngredientEntity>
}