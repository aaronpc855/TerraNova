package com.example.appnavegacionpa2.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Función @Composable que dibuja la vista de la parte A: suma de cifras
@Composable
fun SumaCifrasScreen(modifier: Modifier = Modifier) {
    var numero by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("") }

    // Column apila los componentes en vertical y los centra
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Título de la vista
        Text(
            text = "Suma de cifras",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = numero,
            onValueChange = {
                // Estructura de control: solo acepta dígitos y máximo 3 caracteres
                if (it.length <= 3 && it.all { c -> c.isDigit() }) {
                    numero = it
                }
            },
            label = { Text("Número de tres cifras") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Estructura de control: valida que el número tenga tres cifras
                if (numero.length == 3) {
                    val cifras = numero.map { it.digitToInt() }
                    mensaje = ""
                    resultado = "Suma de cifras: ${cifras.sum()}\nCifra mayor: ${cifras.max()}"
                } else {
                    resultado = ""
                    mensaje = "Ingresa exactamente tres cifras"
                }
            }
        ) {
            Text("Calcular")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (mensaje.isNotEmpty()) {
            Text(text = mensaje, color = MaterialTheme.colorScheme.error)
        }
        if (resultado.isNotEmpty()) {
            Text(text = resultado, style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SumaCifrasScreenPreview() {
    SumaCifrasScreen()
}