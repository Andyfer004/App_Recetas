package com.example.app_recetas.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.app_recetas.model.Receta

@Database(entities = [Receta::class], version = 1, exportSchema = false)
abstract class RecetaDatabase : RoomDatabase() {
    abstract fun recetaDao(): RecetaDao

    companion object {
        @Volatile
        private var INSTANCE: RecetaDatabase? = null

        fun getDatabase(context: Context): RecetaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecetaDatabase::class.java,
                    "receta_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
