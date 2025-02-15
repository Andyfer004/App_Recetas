package com.example.app_recetas.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_recetas.data.RecetaDao
import com.example.app_recetas.model.Receta
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class RecetaViewModel(private val recetaDao: RecetaDao) : ViewModel() {

    val recetas: Flow<List<Receta>> = recetaDao.obtenerRecetas().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    init {
        viewModelScope.launch {
            recetas.collect { lista ->
                println("Lista de recetas obtenida: $lista")
            }
        }
    }

    fun agregarReceta(
        titulo: String,
        descripcion: String,
        tiempo: Int,
        ingredientes: String,
        instrucciones: String,
        esFavorita: Boolean,
        imagenUri: String?
    ) {
        viewModelScope.launch {
            val receta = Receta(
                titulo = titulo,
                descripcion = descripcion,
                tiempoPreparacion = tiempo,
                ingredientes = ingredientes,
                instrucciones = instrucciones,
                esFavorita = esFavorita,
                imagenUri = imagenUri
            )
            recetaDao.insertarReceta(receta)
        }
    }

    fun marcarComoFavorita(id: Int, esFavorita: Boolean) {
        viewModelScope.launch {
            recetaDao.actualizarFavorito(id, esFavorita)
        }
    }

    fun guardarImagen(context: Context, uri: Uri): String {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val nombreArchivo = "receta_${System.currentTimeMillis()}.jpg"
        val archivo = File(context.filesDir, nombreArchivo)

        inputStream.use { input ->
            FileOutputStream(archivo).use { output ->
                input?.copyTo(output)
            }
        }

        return archivo.absolutePath
    }

    fun obtenerRecetaPorId(id: Int, callback: (Receta?) -> Unit) {
        viewModelScope.launch {
            val receta = recetaDao.obtenerRecetaPorId(id)
            callback(receta)
        }
    }


}
