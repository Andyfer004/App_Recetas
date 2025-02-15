package com.example.app_recetas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.app_recetas.data.RecetaDatabase
import com.example.app_recetas.data.DataStoreManager
import com.example.app_recetas.navigation.AppNavigation
import com.example.app_recetas.viewmodel.RecetaViewModel
import com.example.app_recetas.viewmodel.RecetaViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val dataStoreManager = DataStoreManager(applicationContext) // ✅ Agregado DataStoreManager
            val recetaDao = RecetaDatabase.getDatabase(applicationContext).recetaDao()
            val recetaViewModel: RecetaViewModel by viewModels {
                RecetaViewModelFactory(recetaDao)
            }
            AppNavigation(navController, recetaViewModel, dataStoreManager) // ✅ Pasando dataStoreManager
        }
    }
}
