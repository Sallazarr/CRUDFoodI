package com.example.crudfoodi.nav

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.crudfoodi.LoginScreen
import com.example.crudfoodi.RegisterScreen
import com.example.crudfoodi.RegisterStoreScreen
import com.example.crudfoodi.ui.HomeCliente
import androidx.compose.runtime.Composable


@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("registerStore") { RegisterStoreScreen(navController) }
        composable("home_cliente"){HomeCliente(navController)}
    }
}
