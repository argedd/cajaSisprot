package com.adminpay.caja.ui.presentation.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.PointOfSale
import androidx.compose.material.icons.filled.RequestPage
import androidx.compose.material.icons.filled.SupervisedUserCircle
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.adminpay.caja.R
import com.adminpay.caja.domain.model.auth.User


@Composable
fun DrawerContent(
    selectedRoute: String,
    onDestinationClicked: (String) -> Unit,
    user: User?
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(260.dp)
            .background(Color.White),
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

            DrawerItem("Facturación", Icons.Default.RequestPage, "contract_screen", selectedRoute, onDestinationClicked)
            DrawerItem("Caja", Icons.Default.PointOfSale, "box_screen", selectedRoute, onDestinationClicked)
        }

        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            if (user != null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Apartment, // Puedes usar un ícono personalizado si deseas
                        contentDescription = "Sucursal",
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = user.officeName, // ejemplo: "Sucursal Caracas"
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.SupervisedUserCircle, // Puedes cambiar este ícono también
                        contentDescription = "Rol",
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = user.role, // ejemplo: "Administrador"
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
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
            .clip(RoundedCornerShape(12.dp)),
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = Color(0xFF004C72),
            unselectedContainerColor = Color.Transparent
        )
    )
}
