package com.adminpay.caja.ui.presentation.main.components


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adminpay.caja.domain.model.auth.User
import com.adminpay.caja.ui.presentation.main.components.tasa.TasaBcvWidgetCard
import com.adminpay.caja.utils.TimeVenezuelaWidgetCard
import com.adminpay.caja.utils.rememberScreenDimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeaderContent(
    onMenuClick: () -> Unit,
    user: User?
) {
    val screen = rememberScreenDimensions()

    Column {
        TopAppBar(
            title = {
                Text(
                    text = "Bienvenido, ${
                        user?.let { "${it.name.replaceFirstChar { c -> c.uppercase() }} ${it.lastName.replaceFirstChar { c -> c.uppercase() }}" }
                            ?: "Usuario"
                    }",
                    fontSize = screen.heightPercentage(0.020f).value.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = onMenuClick) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menú",
                        modifier = Modifier.size(screen.widthPercentage(0.03f))
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White,
                titleContentColor = Color.Black
            )
        )

        // Aquí van los widgets tipo tarjeta
        Box(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TimeVenezuelaWidgetCard(modifier = Modifier.weight(0.5f))
                Spacer(modifier = Modifier.width(8.dp))
                TasaBcvWidgetCard(modifier = Modifier.weight(0.5f))
            }
        }
    }
}



