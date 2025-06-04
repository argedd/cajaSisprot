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
import com.adminpay.caja.ui.presentation.main.components.tasa.TasaBcv
import com.adminpay.caja.utils.rememberScreenDimensions
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeaderContent(
    onMenuClick: () -> Unit,
    user: User?
) {
    val fecha = remember {
        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
    }

    val userName = if (user != null) {
        "${user.name.replaceFirstChar { it.uppercase() }} ${user.lastName.replaceFirstChar { it.uppercase() }}"
    } else {
        "Usuario"
    }

    val screen = rememberScreenDimensions()

    Log.d("HeaderContent", "User: $userName")

    TopAppBar(
        title = {
            Box(modifier = Modifier.fillMaxWidth().padding(top = 32.dp)) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menú",
                    tint = Color.Black,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 8.dp)
                        .clickable { onMenuClick() }
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .background(
                            color = Color(0xFF004C72),
                            shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp)
                        )
                        .padding(horizontal = 200.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(top = 12.dp)
                    ) {
                        Text(
                            text = "Fecha: $fecha",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = screen.heightPercentage(0.020f).value.sp
                            ),
                            modifier = Modifier.padding(end = 12.dp)
                        )
                        TasaBcv()
                    }
                }

                Text(
                    text = "Bienvenido, $userName",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.Black,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 12.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
            titleContentColor = Color.Black
        )
    )
}


