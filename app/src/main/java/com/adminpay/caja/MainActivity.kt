package com.adminpay.caja

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.adminpay.caja.domain.model.socket.TcpSocketModel
import com.adminpay.caja.ui.navigation.AppNavHost
import com.adminpay.caja.ui.presentation.components.HideNavigationBar
import com.adminpay.caja.ui.presentation.components.LoadingComponent
import com.adminpay.caja.ui.theme.CajaTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var tcpServerManager: TcpSocketModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Iniciar el servidor TCP al arrancar la app
        tcpServerManager.start()
        enableEdgeToEdge()
        setContent {
            CajaTheme {
                HideNavigationBar()
                AppNavHost()
//                DesingScreen()
                LoadingComponent()

            }
        }
    }
}



