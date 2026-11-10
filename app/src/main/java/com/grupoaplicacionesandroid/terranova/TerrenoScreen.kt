package com.grupoaplicacionesandroid.terranova

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TerrenoScreen(navController: NavController) {
    var largoStr by remember { mutableStateOf("") }
    var anchoStr by remember { mutableStateOf("") }
    var precioM2Str by remember { mutableStateOf("") }

    var perimetro by remember { mutableStateOf(0.0) }
    var area by remember { mutableStateOf(0.0) }
    var costoTotal by remember { mutableStateOf(0.0) }
    var mostrarResultados by remember { mutableStateOf(false) }
    var mensajeError by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cotización de Terreno", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    TextButton(onClick = { navController.popBackStack() }) {
                        Text("← Volver", fontWeight = FontWeight.Bold)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(MaterialTheme.shapes.extraLarge)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "m²",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Text(
                text = "Cálculo de Área, Perímetro y Costo",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(4.dp))

            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    OutlinedTextField(
                        value = largoStr,
                        onValueChange = { largoStr = it },
                        label = { Text("Largo del terreno (metros)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = MaterialTheme.shapes.medium
                    )

                    OutlinedTextField(
                        value = anchoStr,
                        onValueChange = { anchoStr = it },
                        label = { Text("Ancho del terreno (metros)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = MaterialTheme.shapes.medium
                    )

                    OutlinedTextField(
                        value = precioM2Str,
                        onValueChange = { precioM2Str = it },
                        label = { Text("Precio por metro cuadrado ($)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = MaterialTheme.shapes.medium
                    )
                }
            }

            if (mensajeError.isNotEmpty()) {
                Text(
                    text = mensajeError,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Button(
                onClick = {
                    val largo = largoStr.toDoubleOrNull()
                    val ancho = anchoStr.toDoubleOrNull()
                    val precioM2 = precioM2Str.toDoubleOrNull()

                    if (largo != null && ancho != null && precioM2 != null && largo > 0 && ancho > 0 && precioM2 > 0) {
                        perimetro = 2 * (largo + ancho)
                        area = largo * ancho
                        costoTotal = area * precioM2
                        mostrarResultados = true
                        mensajeError = ""
                    } else {
                        mensajeError = "⚠️ Ingrese valores numéricos válidos mayores a cero."
                        mostrarResultados = false
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = "Calcular Terreno", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            if (mostrarResultados) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    ),
                    shape = MaterialTheme.shapes.large
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "Resultados Obtenidos",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )

                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                        ResultadoFila(label = "Perímetro Total:", valor = "${"%.2f".format(perimetro)} m")
                        ResultadoFila(label = "Área Total:", valor = "${"%.2f".format(area)} m²")
                        ResultadoFila(label = "Costo Estimado:", valor = "$ ${"%.2f".format(costoTotal)}")
                    }
                }
            }
        }
    }
}

@Composable
fun ResultadoFila(label: String, valor: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 15.sp,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
        Text(
            text = valor,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}