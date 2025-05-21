package com.example.crudfoodi.nav

import androidx.activity.result.ActivityResultLauncher
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.crudfoodi.LoginScreen
import com.example.crudfoodi.RegisterScreen
import com.example.crudfoodi.RegisterStoreScreen
import com.example.crudfoodi.HomeCliente
import androidx.compose.runtime.Composable
import com.example.crudfoodi.HomeRestaurante
import com.example.crudfoodi.ui.AddProdutoScreen
import com.example.crudfoodi.StoreProducts
import com.example.crudfoodi.CheckoutScreen
import com.example.crudfoodi.ConfirmacaoScreen



@Composable
fun Navigation(requestPermissionLauncher: ActivityResultLauncher<String>) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {LoginScreen(navController,
            requestPermissionLauncher = requestPermissionLauncher) }
        composable("register") { RegisterScreen(navController) }
        composable("registerStore") { RegisterStoreScreen(navController) }
        composable("home_cliente"){HomeCliente(navController, requestPermissionLauncher)}
        composable("home_restaurante"){ HomeRestaurante(navController) }
        composable("addProduto"){ AddProdutoScreen(navController) }
        composable("storeProducts"){ StoreProducts(navController) }
        composable("checkout"){ CheckoutScreen(navController) }
        composable("confirmation"){ ConfirmacaoScreen(navController) }
    }
}
