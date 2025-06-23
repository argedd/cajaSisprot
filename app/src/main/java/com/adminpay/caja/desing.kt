package com.adminpay.caja

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex


@Composable
fun DesingScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val gradientColors = listOf(Color(0xFF6A11CB), Color(0xFF2575FC))
    val secondaryColor = MaterialTheme.colorScheme.secondary

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Círculo esquina superior derecha
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(-1f)
        ) {
            drawCircle(
                color = secondaryColor.copy(alpha = 0.4f),
                radius = size.minDimension / 3,
                center = Offset(size.width, 0f)
            )
            drawCircle(
                color = secondaryColor.copy(alpha = 0.25f),
                radius = size.minDimension / 5,
                center = Offset(size.width * 0.75f, size.minDimension * 0.15f)
            )
        }

        // Círculo esquina inferior izquierda
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(-1f)
        ) {
            drawCircle(
                color = secondaryColor.copy(alpha = 0.35f),
                radius = size.minDimension / 3.5f,
                center = Offset(0f, size.height)
            )
            drawCircle(
                color = secondaryColor.copy(alpha = 0.2f),
                radius = size.minDimension / 6,
                center = Offset(size.minDimension * 0.2f, size.height * 0.85f)
            )
        }

        // Contenido central (formulario)
        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .align(Alignment.Center)
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 32.dp)
            )



            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            )

            Button(
                onClick = { /* Lógica de login */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.horizontalGradient(gradientColors),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Login",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            ClickableText(
                text = AnnotatedString("Need to redeem?"),
                onClick = { /* Acción */ },
                style = TextStyle(
                    color = Color(0xFF6A11CB),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}



@Composable
fun SgfLogo(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.secondary,
    alpha: Float = 0.7f
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val strokeWidth = 8f

        val strokeStyle = Stroke(
            width = strokeWidth,
            cap = StrokeCap.Round,
            join = StrokeJoin.Round
        )

        // Letra S
        val pathS = Path().apply {
            moveTo(w * 0.1f, h * 0.2f)
            cubicTo(
                w * 0.5f, h * 0.1f,
                w * 0.5f, h * 0.5f,
                w * 0.1f, h * 0.5f
            )
            cubicTo(
                w * 0.1f, h * 0.5f,
                w * 0.9f, h * 0.5f,
                w * 0.9f, h * 0.8f
            )
            cubicTo(
                w * 0.9f, h * 0.9f,
                w * 0.3f, h * 0.9f,
                w * 0.3f, h * 0.7f
            )
        }
        drawPath(pathS, color.copy(alpha = alpha), style = strokeStyle)

        // Letra G
        val pathG = Path().apply {
            val centerX = w * 0.6f
            val centerY = h * 0.5f
            val radius = h * 0.3f
            arcTo(
                rect = Rect(
                    centerX - radius,
                    centerY - radius,
                    centerX + radius,
                    centerY + radius
                ),
                startAngleDegrees = 0f,
                sweepAngleDegrees = 300f,
                forceMoveTo = false
            )
            moveTo(centerX + radius * 0.5f, centerY)
            lineTo(centerX, centerY)
        }
        drawPath(pathG, color.copy(alpha = alpha), style = strokeStyle)

        // Letra F
        val pathF = Path().apply {
            val startX = w * 0.85f
            val startY = h * 0.2f
            val endY = h * 0.8f
            moveTo(startX, startY)
            lineTo(startX, endY)  // barra vertical
            moveTo(startX, startY)
            lineTo(w * 1.05f, startY)  // barra superior horizontal
            moveTo(startX, h * 0.5f)
            lineTo(w * 1.0f, h * 0.5f)  // barra media horizontal
        }
        drawPath(pathF, color.copy(alpha = alpha), style = strokeStyle)
    }
}

