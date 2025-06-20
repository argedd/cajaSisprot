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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adminpay.caja.R
import com.adminpay.caja.domain.model.auth.User
import com.adminpay.caja.utils.ScreenDimensions
import com.adminpay.caja.utils.rememberScreenDimensions


@Composable
fun DrawerContent(
    selectedRoute: String,
    onDestinationClicked: (String) -> Unit,
    user: User?
) {
    val screen = rememberScreenDimensions()

    val drawerWidth = screen.widthPercentage(0.22f)
    val logoSize = screen.widthPercentage(0.12f)
    val iconSize = screen.widthPercentage(0.02f)
    val textSize = (screen.width.value * 0.014f).sp // Escalado de texto
    val paddingVertical = screen.heightPercentage(0.015f)
    val spacing = screen.widthPercentage(0.01f)

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(drawerWidth)
            .background(Color.White),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Spacer(modifier = Modifier.height(paddingVertical))
            Image(
                painter = painterResource(id = R.drawable.logocolor),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(logoSize)
                    .align(Alignment.CenterHorizontally)
            )
            HorizontalDivider(modifier = Modifier.padding(vertical = paddingVertical))

            DrawerItem(
                title = "Facturación",
                icon = Icons.Default.RequestPage,
                route = "contract_screen",
                selectedRoute = selectedRoute,
                onDestinationClicked = onDestinationClicked,
                iconSize = iconSize,
                textSize = textSize
            )
            DrawerItem(
                title = "Caja",
                icon = Icons.Default.PointOfSale,
                route = "box_screen",
                selectedRoute = selectedRoute,
                onDestinationClicked = onDestinationClicked,
                iconSize = iconSize,
                textSize = textSize
            )
        }

        Column(modifier = Modifier.padding(spacing)) {
            if (user != null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = spacing)
                ) {
                    Icon(
                        imageVector = Icons.Default.Apartment,
                        contentDescription = "Sucursal",
                        tint = Color.Gray,
                        modifier = Modifier.size(iconSize)
                    )
                    Spacer(modifier = Modifier.width(spacing))
                    Text(
                        text = user.officeName,
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = textSize)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = spacing)
                ) {
                    Icon(
                        imageVector = Icons.Default.SupervisedUserCircle,
                        contentDescription = "Rol",
                        tint = Color.Gray,
                        modifier = Modifier.size(iconSize)
                    )
                    Spacer(modifier = Modifier.width(spacing))
                    Text(
                        text = user.role,
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = textSize)
                    )
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = paddingVertical))
            }

            DrawerItem(
                title = "Cerrar sesión",
                icon = Icons.AutoMirrored.Filled.ExitToApp,
                route = "logout",
                selectedRoute = selectedRoute,
                onDestinationClicked = onDestinationClicked,
                iconSize = iconSize,
                textSize = textSize
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
    onDestinationClicked: (String) -> Unit,
    iconSize: Dp,
    textSize: TextUnit,
    screen: ScreenDimensions = rememberScreenDimensions()
) {
    val isSelected = route == selectedRoute

    NavigationDrawerItem(
        label = {
            Text(
                text = title,
                fontSize = textSize,
                color = if (isSelected) Color.White else Color.Black,
                modifier = Modifier.padding(vertical = screen.heightPercentage(0.015f))
            )
        },
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isSelected) Color(0xFFFCA311) else Color.Black,
                modifier = Modifier.size(iconSize)
            )
        },
        selected = isSelected,
        onClick = { onDestinationClicked(route) },
        modifier = Modifier
            .padding(horizontal = iconSize, vertical = iconSize / 4)
            .clip(RoundedCornerShape(iconSize / 2)),
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = Color(0xFF004C72),
            unselectedContainerColor = Color.Transparent
        )
    )
}

