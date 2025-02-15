package com.example.app_recetas.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.app_recetas.data.RecetaDao

class RecetaViewModelFactory(private val recetaDao: RecetaDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecetaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecetaViewModel(recetaDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
