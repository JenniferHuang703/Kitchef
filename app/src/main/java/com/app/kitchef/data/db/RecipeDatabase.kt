package com.app.kitchef.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.kitchef.data.db.entity.recipeModel.Recipe

@Database(
    entities = [Recipe::class],
    version = 1
)
@TypeConverters(RoomTypeConverter::class)
abstract class RecipeDatabase: RoomDatabase() {
    abstract fun currentRecipeDao(): CurrentRecipeDao

    companion object {
        @Volatile private var instance: RecipeDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                RecipeDatabase::class.java, "recipe.db")
                .build()
    }
}