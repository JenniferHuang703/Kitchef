package com.example.kitchef.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kitchef.data.db.entity.ingredientModel.FoodX

@Database(
    entities = [FoodX::class],
    version = 1
)
abstract class IngredientDatabase: RoomDatabase() {
    abstract fun currentIngredientDao(): CurrentIngredientDao

    companion object {
        @Volatile private var instance: IngredientDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                IngredientDatabase::class.java, "ingredient.db")
                .build()
    }
}