package com.adminpay.caja.ui.presentation.contract

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FacturacionScreen() {
    val tipos = listOf("V", "E", "J", "G", "P")
    var selectedTipo by remember { mutableStateOf("V") }
    var cedula by remember { mutableStateOf("") }

    val clientes = remember {
        listOf(
            Cliente(7363, "PRUEBA FREDDY", "FREDDY", "V28458411", "04129950904", "Suspendido", "FREDDYCARRILLO1912@GMAIL.COM", "SAFSADF", 862.45f),
            Cliente(7704, "PRUEBA FREDDY", "FREDDY", "V28458411", "04129950904", "Cancelado", "FREDDYCARRILLO1912@GMAIL.COM", "PRUEBA", 0f),
            Cliente(7705, "PRUEBA FREDDY", "FREDDY", "V28458411", "04129950904", "Cancelado", "FREDDYCARRILLO1912@GMAIL.COM", "PRUEBA", 0f),
            Cliente(7801, "PRUEBA FREDDY", "FREDDY", "V28458411", "04129950904", "Cancelado", "FREDDYCARRILLO1912@GMAIL.COM", "TURMERO", 0f),
            Cliente(7962, "PRUEBA FREDDY", "FREDDY", "V28458411", "04129950904", "Cancelado", "FREDDYCARRILLO1912@GMAIL.COM", "PRUEBA", 0f)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            ExposedDropdownMenuBox(
                expanded = false,
                onExpandedChange = {}
            ) {
                TextField(
                    value = selectedTipo,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Tipo") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.width(80.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            TextField(
                value = cedula,
                onValueChange = { cedula = it },
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = null)
                },
                trailingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null)
                },
                placeholder = { Text("Cédula o RIF") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(clientes) { cliente ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (cliente.debt_bs > 0f) Color(0xFFFFF3E0) else Color(0xFFE0F7FA)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFF004C72), RoundedCornerShape(12.dp))
                                .padding(8.dp)
                        ) {
                            Text(
                                text = cliente.name,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Description, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text("Contrato: ${cliente.id}")
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.LocationOn, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text("Dirección: ${cliente.address_tax}")
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.AccountBox, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text("Estado: ${cliente.status_name}")
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Money, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text("Monto: ${cliente.debt_bs} Bs")
                        }
                    }
                }
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
