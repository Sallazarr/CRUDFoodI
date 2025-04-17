package com.example.crudfoodi
import androidx.compose.foundation.clickable

import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.foundation.background // <--- Este aqui é o que falta!
import androidx.navigation.NavHostController
import androidx.compose.ui.graphics.Brush

import androidx.compose.material3.TextField
import androidx.compose.material3.ButtonDefaults
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

//Import para SQLite

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.sp

@Composable
fun RegisterScreen(navController: NavHostController) {
    // Tela de Registro
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.White, Color(0xFF007bff))
                )
            )
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.logobgless),
                contentDescription = "Logo FoodI",
                modifier = Modifier
                    .height(200.dp)
                    .width(200.dp)
                    .padding(bottom = 6.dp)
            )
            Text(
                text = "Criar Conta",
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                color = Color.White
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Inputs de Registro
            TextField(
                value = "",
                onValueChange = {},
                label = { Text("Nome") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color(0xFF007bff),
                    unfocusedIndicatorColor = Color.LightGray,
                    focusedLabelColor = Color(0xFF007bff),
                    unfocusedLabelColor = Color.Gray,
                    cursorColor = Color(0xFF007bff)
                ),
                textStyle = TextStyle(color = Color.Black)
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = "",
                onValueChange = {},
                label = { Text("Sobrenome") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color(0xFF007bff),
                    unfocusedIndicatorColor = Color.LightGray,
                    focusedLabelColor = Color(0xFF007bff),
                    unfocusedLabelColor = Color.Gray,
                    cursorColor = Color(0xFF007bff)
                ),
                textStyle = TextStyle(color = Color.Black)
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = "",
                onValueChange = {},
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color(0xFF007bff),
                    unfocusedIndicatorColor = Color.LightGray,
                    focusedLabelColor = Color(0xFF007bff),
                    unfocusedLabelColor = Color.Gray,
                    cursorColor = Color(0xFF007bff)
                ),
                textStyle = TextStyle(color = Color.Black)
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = "",
                onValueChange = {},
                label = { Text("Celular") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color(0xFF007bff),
                    unfocusedIndicatorColor = Color.LightGray,
                    focusedLabelColor = Color(0xFF007bff),
                    unfocusedLabelColor = Color.Gray,
                    cursorColor = Color(0xFF007bff)
                ),
                textStyle = TextStyle(color = Color.Black)
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = "",
                onValueChange = {},
                label = { Text("Senha") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color(0xFF007bff),
                    unfocusedIndicatorColor = Color.LightGray,
                    focusedLabelColor = Color(0xFF007bff),
                    unfocusedLabelColor = Color.Gray,
                    cursorColor = Color(0xFF007bff)
                ),
                textStyle = TextStyle(color = Color.Black)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007bff))
            ) {
                Text("Criar Conta", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Texto de registro
            Text(
                text = "Já tem uma conta? Entrar",
                color = Color(0xFFffffff),
                modifier = Modifier.clickable { navController.navigate("MainActivity") }
            )
            Text(
                text = "Deseja cadastrar um restaurante?",
                color = Color(0xFFffffff),
                modifier = Modifier
                    .clickable { navController.navigate("registerStore") }
            )

        }
    }
}
