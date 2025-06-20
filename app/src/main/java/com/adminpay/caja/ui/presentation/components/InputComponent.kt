package com.adminpay.caja.ui.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adminpay.caja.utils.rememberScreenDimensions
@Composable
fun InputComponent(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    isError: Boolean = false,
    error: String? = null,
    isPassword: Boolean = false,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    singleLine: Boolean = true,
    height: Int = 48, // Ignorado, lo adaptamos con screen
    cornerRadius: Int = 1,
    textStyle: TextStyle = TextStyle.Default,
    placeholderStyle: TextStyle = TextStyle.Default,
    onTrailingIconClick: () -> Unit = {},
    readOnly: Boolean = false
) {
    val focusRequester = remember { FocusRequester() }
    val screen = rememberScreenDimensions()

    val dynamicHeight = screen.heightPercentage(0.06f) // ~48.dp en tablets
    val iconSize = screen.widthPercentage(0.02f) // ~20.dp
    val spacing = screen.widthPercentage(0.02f)   // ~8.dp
    val paddingHorizontal = screen.widthPercentage(0.02f) // ~16.dp

    Column(modifier = modifier) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(dynamicHeight),
            shape = RoundedCornerShape(screen.widthPercentage(cornerRadius / 100f)),
            color = Color.White,
            border = BorderStroke(
                width = screen.widthPercentage(0.002f), // ~1.dp
                color = if (isError) MaterialTheme.colorScheme.error
                else MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
            ),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = paddingHorizontal),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Leading Icon
                    leadingIcon?.let {
                        Icon(
                            imageVector = it,
                            contentDescription = null,
                            tint = if (isError) MaterialTheme.colorScheme.error
                            else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            modifier = Modifier.size(iconSize)
                        )
                        Spacer(modifier = Modifier.width(spacing))
                    }

                    // Text Field
                    BasicTextField(
                        value = value,
                        onValueChange = onValueChange,
                        modifier = Modifier
                            .weight(1f)
                            .focusRequester(focusRequester),
                        singleLine = singleLine,
                        readOnly = readOnly,
                        textStyle = textStyle.copy(
                            color = if (isError) MaterialTheme.colorScheme.error
                            else MaterialTheme.colorScheme.onSurface,
                            fontSize = (screen.width.value * 0.014).sp, // ~16.sp
                            textAlign = TextAlign.Start
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                        visualTransformation = if (isPassword) androidx.compose.ui.text.input.PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None,
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                if (value.isEmpty()) {
                                    Text(
                                        text = placeholder,
                                        style = placeholderStyle.copy(
                                            fontSize = (screen.width.value * 0.014).sp,
                                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                        )
                                    )
                                }
                                innerTextField()
                            }
                        }
                    )

                    // Trailing Icon
                    trailingIcon?.let {
                        Spacer(modifier = Modifier.width(spacing))
                        Icon(
                            imageVector = it,
                            contentDescription = null,
                            tint = if (isError) MaterialTheme.colorScheme.error
                            else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            modifier = Modifier
                                .size(iconSize)
                                .clickable { onTrailingIconClick() }
                        )
                    }
                }
            }
        }

        // Error message
        if (error != null) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = (screen.width.value * 0.013f).sp // ~14.sp
                ),
                modifier = Modifier
                    .padding(start = screen.widthPercentage(0.02f), top = screen.heightPercentage(0.005f))
            )
        }
    }
}

