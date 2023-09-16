package com.app.kitchef.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.kitchef.data.db.entity.ingredientModel.IngredientEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrentIngredientDao {

    @Query("SELECT * FROM current_ingredient")
    fun getAllIngredient(): Flow<List<IngredientEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setIngredientToList(ingredient: IngredientEntity)

    @Query("select * from current_ingredient where ingredientId= :ingredientId")
    fun getIngredient(ingredientId: Int): Flow<IngredientEntity>

    @Query("DELETE FROM current_ingredient WHERE ingredientId = :ingredientId")
    fun removeIngredient(ingredientId: Int)
}