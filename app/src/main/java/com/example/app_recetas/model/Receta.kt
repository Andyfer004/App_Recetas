package com.example.app_recetas.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recetas")
data class Receta(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val titulo: String,
    val descripcion: String,
    val tiempoPreparacion: Int,
    val esFavorita: Boolean = false,
    val imagenUri: String? = null,
    val ingredientes: String,
    val instrucciones: String
)
