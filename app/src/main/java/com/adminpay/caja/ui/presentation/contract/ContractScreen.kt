package com.adminpay.caja.ui.presentation.contract

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.unit.dp
import com.adminpay.caja.ui.presentation.contract.components.Buscador
import com.adminpay.caja.ui.presentation.contract.components.ClienteCard

@Composable
fun FacturacionScreen() {
    val tipos = listOf("V", "E", "J", "G", "P")
    var selectedTipo by remember { mutableStateOf("V") }
    var cedula by remember { mutableStateOf("") }

    val clientes = remember {
        listOf(
            Cliente(7363, "PRUEBA SISTEMA", "FREDDY", "V28458411", "04129950904", "Suspendido", "FREDDYCARRILLO1912@GMAIL.COM", "2436 Main Street, Springfield, IL 62704, United States", 862.45f),
            Cliente(7704, "PRUEBA SISTEMA", "FREDDY", "V28458411", "04129950904", "Cancelado", "FREDDYCARRILLO1912@GMAIL.COM", "2436 Main Street, Springfield, IL 62704, United States", 0f),
            Cliente(7705, "PRUEBA SISTEMA", "FREDDY", "V28458411", "04129950904", "Cancelado", "FREDDYCARRILLO1912@GMAIL.COM", "2436 Main Street, Springfield, IL 62704, United States", 0f),
            Cliente(7801, "PRUEBA SISTEMA", "FREDDY", "V28458411", "04129950904", "Cancelado", "FREDDYCARRILLO1912@GMAIL.COM", "2436 Main Street, Springfield, IL 62704, United States", 0f),
            Cliente(7962, "PRUEBA SISTEMA", "FREDDY", "V28458411", "04129950904", "Cancelado", "FREDDYCARRILLO1912@GMAIL.COM", "2436 Main Street, Springfield, IL 62704, United States", 0f)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(12.dp)
    ) {
        Buscador(selectedTipo, cedula, onCedulaChange = { cedula = it })

        Spacer(modifier = Modifier.height(12.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(clientes) { cliente ->
                ClienteCard(cliente)
            }
        }
    }
}


data class Cliente(
    val id: Int,
    val name: String,
    val last_name: String,
    val identification: String,
    val mobile: String,
    val status_name: String,
    val email: String,
    val address_tax: String,
    val debt_bs: Float
)
