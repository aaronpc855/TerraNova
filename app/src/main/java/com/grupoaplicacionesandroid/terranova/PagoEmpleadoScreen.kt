package com.example.appnavegacionpa2.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

// Vista e: formulario para calcular el monto a pagar a un empleado
@Composable
fun PagoEmpleadoScreen(navController: NavController, modifier: Modifier = Modifier) {
    // Estados: datos que escribe el usuario, mensaje de error y resultado
    var sueldo by remember { mutableStateOf("") }
    var hijos by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Pago a empleado",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo para el sueldo base
        OutlinedTextField(
            value = sueldo,
            onValueChange = {
                // Solo acepta dígitos, punto y coma
                if (it.all { c -> c.isDigit() || c == '.' || c == ',' }) {
                    sueldo = it
                }
            },
            label = { Text("Sueldo base (S/)") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo para el número de hijos (solo dígitos)
        OutlinedTextField(
            value = hijos,
            onValueChange = {
                if (it.all { c -> c.isDigit() }) {
                    hijos = it
                }
            },
            label = { Text("Número de hijos") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Button(
                onClick = {
                    val sueldoBase = sueldo.replace(',', '.').toDoubleOrNull()
                    val numHijos = hijos.toIntOrNull()

                    if (sueldoBase == null || numHijos == null) {
                        // Validación: ambos campos deben estar completos
                        resultado = ""
                        mensaje = "Ingresa el sueldo base y el número de hijos"
                    } else {
                        // Bonificación: 100 soles por hijo, máximo 400
                        var bonificacion = numHijos * 100.0
                        if (bonificacion > 400.0) {
                            bonificacion = 400.0
                        }
                        // Descuento: 8% del sueldo base (seguro)
                        val descuento = sueldoBase * 0.08
                        // Monto a pagar total
                        val total = sueldoBase + bonificacion - descuento

                        val bonifTexto = String.format("%.2f", bonificacion)
                        val descTexto = String.format("%.2f", descuento)
                        val totalTexto = String.format("%.2f", total)

                        mensaje = ""
                        resultado = "Bonificación: S/ $bonifTexto\n" +
                                "Descuento (8%): S/ $descTexto\n" +
                                "Monto total a pagar: S/ $totalTexto"
                    }
                }
            ) {
                Text("Calcular")
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Navegación: vuelve a la vista anterior
            Button(onClick = { navController.popBackStack() }) {
                Text("Regresar")
            }
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
