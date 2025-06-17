package com.adminpay.caja.ui.presentation.checkout.components.paymentMethods.pos

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adminpay.caja.R
import com.adminpay.caja.ui.presentation.checkout.CheckoutSharedViewModel
import com.adminpay.caja.ui.presentation.components.InputComponent

@Composable
fun PosScreen(sharedViewModel: CheckoutSharedViewModel) {
    var cedula by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Imagen centrada
        Image(
            painter = painterResource(id = R.drawable.tarjetas),
            contentDescription = "Confirmación de tarjeta",
            modifier = Modifier
                .height(50.dp) // Altura más razonable
                .padding(top = 8.dp, bottom = 8.dp) // Reducir espacio arriba y abajo
        )

        // Título
        Text(
            text = "Confirmar cédula de la tarjeta",
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Formulario
        InputComponent(
            value = cedula,
            onValueChange = { cedula = it },
            placeholder = "Cédula de la Tarjeta",
            leadingIcon = Icons.Default.Badge,
            onTrailingIconClick = {
                // Mostrar DatePickerDialog
            },
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botón Validar Pago
        Button(
            onClick = {
                // Acción de validación
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("Cargar Pago", color = Color.White)
        }
    }
}
