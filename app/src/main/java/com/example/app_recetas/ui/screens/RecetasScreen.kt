package com.example.app_recetas.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.app_recetas.R
import com.example.app_recetas.data.DataStoreManager
import com.example.app_recetas.model.Receta
import com.example.app_recetas.viewmodel.RecetaViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecetasScreen(viewModel: RecetaViewModel, navController: NavController, dataStoreManager: DataStoreManager) {
    val recetas by viewModel.recetas.collectAsState(initial = emptyList())

    var mostrarFavoritas by remember { mutableStateOf(false) }
    var ordenarPorTiempo by remember { mutableStateOf(false) }

    val recetasFiltradas = recetas
        .filter { if (mostrarFavoritas) it.esFavorita else true }
        .sortedBy { if (ordenarPorTiempo) it.tiempoPreparacion else 0 }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Recetas", style = MaterialTheme.typography.headlineMedium) },
                actions = {
                    IconButton(onClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            dataStoreManager.clearLoginState()
                            withContext(Dispatchers.Main) {
                                navController.navigate("login") {
                                    popUpTo("recetas") { inclusive = true }
                                }
                            }
                        }
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Cerrar sesión")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("addReceta") },
                containerColor = Color(0xFF7E57C2)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Receta", tint = Color.White)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Filtros", fontSize = 18.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Switch(checked = mostrarFavoritas, onCheckedChange = { mostrarFavoritas = it })
                    Text("Solo Favoritas")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Switch(checked = ordenarPorTiempo, onCheckedChange = { ordenarPorTiempo = it })
                    Text("Ordenar por Tiempo")
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(recetasFiltradas) { receta ->
                    RecetaCard(
                        receta,
                        onFavoritoClick = { viewModel.marcarComoFavorita(receta.id, !receta.esFavorita) },
                        onClick = { navController.navigate("detalleReceta/${receta.id}") }
                    )
                }
            }
        }
    }
}

@Composable
fun RecetaCard(
    receta: Receta,
    onFavoritoClick: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
            .clip(RectangleShape),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5EFFF)),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RecetaImagen(receta.imagenUri)

            Column(modifier = Modifier.weight(1f).padding(start = 16.dp)) {
                Text(receta.titulo, fontSize = 22.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Star, contentDescription = "Tiempo", tint = Color.Gray, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("${receta.tiempoPreparacion} min", fontSize = 14.sp, color = Color.Gray)
                }
                Text("${receta.ingredientes.take(30)}...", fontSize = 14.sp, color = Color.Gray)

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onFavoritoClick) {
                        Icon(
                            imageVector = if (receta.esFavorita) Icons.Filled.Star else Icons.Outlined.Star,
                            contentDescription = "Favorita",
                            tint = if (receta.esFavorita) Color(0xFFFFC107) else Color.Gray
                        )
                    }
                    Text("Favorita", fontSize = 14.sp, color = Color.Gray)
                }
            }
        }
    }
}

@Composable
fun RecetaImagen(imagenUri: String?) {
    AsyncImage(
        model = imagenUri ?: R.drawable.placeholder_image,
        contentDescription = "Imagen de receta",
        modifier = Modifier
            .size(80.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(Color(0xFFEEEEEE))
    )
}
