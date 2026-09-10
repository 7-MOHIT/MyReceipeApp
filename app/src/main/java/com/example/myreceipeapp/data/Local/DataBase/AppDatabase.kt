package com.example.myreceipeapp.data.Local.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myreceipeapp.data.Local.DAO.RecipeDao
import com.example.myreceipeapp.data.Local.ENTITY.RECIPE.RecipeEntity
import com.example.myreceipeapp.data.Local.convertor.Convertors

//its a database class.
@Database(
    entities = [RecipeEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Convertors::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: android.content.Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: databaseBuilder(
                    context.applicationContext,
                    klass = AppDatabase::class.java,
                    name = "recipe_app_database"
                ).build().also { INSTANCE = it }
            }
        }


    }
}