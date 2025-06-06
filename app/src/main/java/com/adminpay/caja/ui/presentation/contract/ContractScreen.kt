package com.adminpay.caja.ui.presentation.contract

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.adminpay.caja.R
import com.adminpay.caja.ui.presentation.contract.components.Buscador
import com.adminpay.caja.ui.presentation.contract.components.ClienteCard

@Composable
fun ContractScreen(
    navController: NavHostController,
    viewModel: ContractViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

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
            onCedulaChange = {
                viewModel.onCedulaChanged(it)
                if (it.length >= 6) {
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
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.contratos) { contrato ->
                    ClienteCard(contrato, navController)
                }
            }
        }
    }
}
