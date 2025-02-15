package com.example.app_recetas.data
import androidx.room.*
import com.example.app_recetas.model.Receta
import kotlinx.coroutines.flow.Flow

@Dao
interface RecetaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarReceta(receta: Receta)

    @Query("SELECT * FROM recetas ORDER BY tiempoPreparacion ASC")
    fun obtenerRecetas(): Flow<List<Receta>>


    @Query("UPDATE recetas SET esFavorita = :esFavorita WHERE id = :id")
    suspend fun actualizarFavorito(id: Int, esFavorita: Boolean)

    @Query("SELECT * FROM recetas WHERE id = :id LIMIT 1")
    suspend fun obtenerRecetaPorId(id: Int): Receta?

}

