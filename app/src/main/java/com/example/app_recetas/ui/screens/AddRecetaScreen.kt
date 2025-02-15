package com.example.app_recetas.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.app_recetas.R
import com.example.app_recetas.viewmodel.RecetaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecetaScreen(navController: NavHostController, recetaViewModel: RecetaViewModel) {
    val context = LocalContext.current
    var titulo by remember { mutableStateOf(TextFieldValue("")) }
    var descripcion by remember { mutableStateOf(TextFieldValue("")) }
    var tiempoPreparacion by remember { mutableStateOf("") }
    var ingredientes by remember { mutableStateOf(TextFieldValue("")) }
    var instrucciones by remember { mutableStateOf(TextFieldValue("")) }
    var esFavorita by remember { mutableStateOf(false) }
    var imagenUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
        imagenUri = uri
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agregar Receta") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clickable { launcher.launch("image/*") }
                ) {
                    imagenUri?.let {
                        AsyncImage(
                            model = it,
                            contentDescription = "Imagen seleccionada",
                            modifier = Modifier.size(100.dp)
                        )
                    } ?: Icon(
                        painter = painterResource(id = R.drawable.photo),
                        contentDescription = "Seleccionar Imagen",
                        modifier = Modifier.size(100.dp),
                        tint = Color.Gray
                    )
                }

                // 🔹 Input de título
                OutlinedTextField(
                    value = titulo.text,
                    onValueChange = { titulo = TextFieldValue(it) },
                    label = { Text("Título") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = descripcion.text,
                    onValueChange = { descripcion = TextFieldValue(it) },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = tiempoPreparacion,
                    onValueChange = { if (it.all { char -> char.isDigit() }) tiempoPreparacion = it },
                    label = { Text("Tiempo (min)") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = ingredientes.text,
                    onValueChange = { ingredientes = TextFieldValue(it) },
                    label = { Text("Ingredientes") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = instrucciones.text,
                    onValueChange = { instrucciones = TextFieldValue(it) },
                    label = { Text("Instrucciones") },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = esFavorita, onCheckedChange = { esFavorita = it })
                    Text("Marcar como favorita")
                }

                Button(
                    onClick = {
                        if (titulo.text.isNotEmpty() && descripcion.text.isNotEmpty() &&
                            tiempoPreparacion.isNotEmpty() && ingredientes.text.isNotEmpty() &&
                            instrucciones.text.isNotEmpty()
                        ) {
                            val rutaImagen = imagenUri?.let { uri -> recetaViewModel.guardarImagen(context, uri) }
                            recetaViewModel.agregarReceta(
                                titulo.text,
                                descripcion.text,
                                tiempoPreparacion.toInt(),
                                ingredientes.text,
                                instrucciones.text,
                                esFavorita,
                                rutaImagen
                            )
                            navController.navigate("home")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Guardar Receta", color = Color.White)
                }
            }
        }
    )
}
