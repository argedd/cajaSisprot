package com.adminpay.caja.ui.presentation.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PointOfSale
import androidx.compose.material.icons.filled.RequestPage
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.adminpay.caja.R
import com.adminpay.caja.ui.presentation.checkout.CheckoutScreen
import com.adminpay.caja.utils.rememberScreenDimensions
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun MainScreen() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedScreen by remember { mutableStateOf("facturacion") }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                selectedRoute = selectedScreen,
                onDestinationClicked = { route ->
                    selectedScreen = route
                    scope.launch { drawerState.close() }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                HeaderContent {
                    scope.launch {
                        drawerState.open()
                    }
                }
            },
            content = { padding ->
                Box(modifier = Modifier.padding(padding).background(Color.White)) {
                    when (selectedScreen) {
                        "facturacion" -> CheckoutScreen(
                            screen = rememberScreenDimensions(),
                            serviceId = "1234"
                        )
                        "caja" -> CajaScreen()
                    }
                }
            }
        )
    }
}

@Composable
fun DrawerContent(
    selectedRoute: String,
    onDestinationClicked: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(260.dp)
            .background(MaterialTheme.colorScheme.surface),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = R.drawable.logocolor),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally)
            )
            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

            DrawerItem("Facturación", Icons.Default.RequestPage, "facturacion", selectedRoute, onDestinationClicked)
            DrawerItem("Caja", Icons.Default.PointOfSale, "caja", selectedRoute, onDestinationClicked)
        }

        DrawerItem(
            title = "Cerrar sesión",
            icon = Icons.AutoMirrored.Filled.ExitToApp,
            route = "logout",
            selectedRoute = selectedRoute,
            onDestinationClicked = onDestinationClicked
        )
    }
}

@Composable
fun DrawerItem(
    title: String,
    icon: ImageVector,
    route: String,
    selectedRoute: String,
    onDestinationClicked: (String) -> Unit
) {
    val isSelected = route == selectedRoute

    NavigationDrawerItem(
        label = {
            Text(
                text = title,
                color = if (isSelected) Color.White else Color.Black
            )
        },
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isSelected) Color(0xFFFCA311) else Color.Black
            )
        },
        selected = isSelected,
        onClick = { onDestinationClicked(route) },
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(12.dp)), // Bordes redondeados solo si está seleccionado
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = Color(0xFF004C72),
            unselectedContainerColor = Color.Transparent
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeaderContent(onMenuClick: () -> Unit) {
    val fecha = remember {
        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
    }
    val tasaBCV = "0.00"
    val userName = "Argenis Diaz"

    TopAppBar(
        title = {
            Box(modifier = Modifier.fillMaxWidth()) {
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
                        .padding(horizontal = 200.dp, vertical = 16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Fecha: $fecha",
                            color = Color.White,
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier.padding(end = 12.dp)
                        )
                        Text(
                            text = "Tasa BCV: $tasaBCV",
                            color = Color.White,
                            style = MaterialTheme.typography.titleSmall
                        )
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



@Composable
fun CajaScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Vista de Caja")
    }
}
