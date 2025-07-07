package com.adminpay.caja.ui.presentation.box.components

import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.adminpay.caja.domain.model.paymentMethods.PaymentMethodCard
import ir.ehsannarmani.compose_charts.ColumnChart
import ir.ehsannarmani.compose_charts.models.BarProperties
import ir.ehsannarmani.compose_charts.models.Bars

@Composable
fun PaymentMethodsBarChart(paymentMethods: List<PaymentMethodCard>) {
    if (paymentMethods.isEmpty()) return // 👈 No renderizar si no hay datos

    val barsData = paymentMethods.map { method ->
        Bars.Data(
            label = "",
            value = method.totalToday.toDouble(),
            color = SolidColor(method.barColor)
        )
    }

    ColumnChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        data = listOf(Bars(label = "", values = barsData)),
        barProperties = BarProperties(
            thickness = 24.dp,
            spacing = 8.dp,
            cornerRadius = Bars.Data.Radius.Rectangle(8.dp, 8.dp)
        ),
        animationSpec = spring()
    )
}
