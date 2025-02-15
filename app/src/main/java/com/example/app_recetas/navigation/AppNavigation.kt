package com.example.app_recetas.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.app_recetas.data.DataStoreManager
import com.example.app_recetas.ui.screens.LoginScreen
import com.example.app_recetas.ui.screens.AddRecetaScreen
import com.example.app_recetas.ui.screens.DetalleRecetaScreen
import com.example.app_recetas.ui.screens.RecetasScreen
import com.example.app_recetas.viewmodel.RecetaViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    recetaViewModel: RecetaViewModel,
    dataStoreManager: DataStoreManager
) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController, dataStoreManager)
        }
        composable("home") {
            RecetasScreen(viewModel = recetaViewModel, navController = navController, dataStoreManager = dataStoreManager) // 🔹 Se pasa DataStoreManager
        }
        composable("addReceta") {
            AddRecetaScreen(navController, recetaViewModel)
        }
        composable("detalleReceta/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: return@composable
            DetalleRecetaScreen(recetaId = id, viewModel = recetaViewModel, navController = navController)
        }
    }
}
