package com.adminpay.caja.ui.presentation.finish

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.adminpay.caja.R
import com.adminpay.caja.ui.navigation.Routes
import com.adminpay.caja.utils.rememberScreenDimensions

@Composable
fun FinishScreen(navController: NavHostController) {
    val screen = rememberScreenDimensions()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 60.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo superior
        Image(
            modifier = Modifier
                .width(250.dp),
            painter = painterResource(id = R.drawable.logocolor),
            contentDescription = "Logo superior",
            contentScale = ContentScale.Fit,
        )

        // Imagen de transacción centrada
//        Image(
//            modifier = Modifier
//                .width(300.dp)
//                .align(Alignment.CenterHorizontally),
//            painter = painterResource(id = R.drawable.check),
//            contentDescription = "Transacción aprobada",
//            contentScale = ContentScale.Fit,
//        )
        Text(
            text = "El pago fue exitoso y la nota de cobro está totalmente pagada.",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold)

        // Botón "Finalizar"
        Button(
            onClick = { navController.navigate(Routes.ContractScreen.route) },
            modifier = Modifier
                .width(400.dp)
                .height(50.dp),
        ) {
            Text(
                text = "Finalizar",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
