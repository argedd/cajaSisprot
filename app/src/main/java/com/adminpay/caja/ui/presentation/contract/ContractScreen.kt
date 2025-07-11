package com.adminpay.caja.ui.presentation.contract

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.adminpay.caja.R
import com.adminpay.caja.ui.presentation.components.AppModalNotificationComponent
import com.adminpay.caja.ui.presentation.components.ErrorComponent
import com.adminpay.caja.ui.presentation.contract.components.Buscador
import com.adminpay.caja.ui.presentation.contract.components.ClienteCard
import com.adminpay.caja.utils.rememberScreenDimensions

@Composable
fun ContractScreen(
    navController: NavHostController,
    viewModel: ContractViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    var showErrorModal by remember { mutableStateOf(false) }
    val screen = rememberScreenDimensions()
    // Mostrar el modal solo cuando hay un mensaje de error
    if (state.errorMessage != null) {
        AppModalNotificationComponent (
            onDismiss = { viewModel.clearError() } // Al cerrar, reinicia error
        ) {
            ErrorComponent(
                message = state.errorMessage ?: "Error desconocido",
                screen = screen,
                onClose = { viewModel.clearError() }
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(12.dp)
    ) {

        Buscador(
            selectedTipo = state.tipo,
            cedula = state.cedula,
            onTipoSelected = { viewModel.onTipoChanged(it) },
            onCedulaChange = { viewModel.onCedulaChanged(it) },
            onBuscarClick = {
                if (state.cedula.length >= 6) {
                    viewModel.buscarContrato()
                }
            }
        )


        Spacer(modifier = Modifier.height(12.dp))

        if (state.contratos.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logocolor),
                    contentDescription = "Sin resultados"
                )
            }
        } else {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(state.contratos) { contrato ->
                    ClienteCard(
                        cliente = contrato,
                        navController = navController,

                    )
                }
            }

        }
    }
}
