package com.example.crudfoodi

import android.widget.Toast
import androidx.compose.foundation.clickable

import androidx.compose.ui.unit.dp

import androidx.compose.ui.draw.clip

import androidx.compose.foundation.background

import androidx.compose.material3.TextField

import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.unit.sp
import com.example.crudfoodi.db.DBHelper




import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color

import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.TextFieldDefaults

import androidx.navigation.NavHostController
import androidx.compose.ui.graphics.Brush


import androidx.compose.material3.ButtonDefaults



import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight


@Composable
fun RegisterScreen(navController: NavHostController) {
    // Tela de Registro

    // Variáveis de estado para os campos
    var nome by remember { mutableStateOf("") }
    var sobrenome by remember { mutableStateOf("") }
    var celular by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

    val context = LocalContext.current
    val db = DBHelper(context)

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
                value = nome,
                onValueChange = {nome = it},
                label = { Text("Nome") },

                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color(0xFF007bff),
                    unfocusedIndicatorColor = Color.LightGray,
                    focusedLabelColor = Color.Gray,
                    unfocusedLabelColor = Color.Gray,
                    cursorColor = Color(0xFF007bff)
                ),
                textStyle = TextStyle(color = Color(0xFF007bff))
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = sobrenome,
                onValueChange = {sobrenome = it},
                label = { Text("Sobrenome") },

                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color(0xFF007bff),
                    unfocusedIndicatorColor = Color.LightGray,
                    focusedLabelColor = Color.Gray,
                    unfocusedLabelColor = Color.Gray,
                    cursorColor = Color(0xFF007bff)
                ),
                textStyle = TextStyle(color = Color(0xFF007bff))
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = email,
                onValueChange = {email = it},
                label = { Text("Email") },

                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color(0xFF007bff),
                    unfocusedIndicatorColor = Color.LightGray,
                    focusedLabelColor = Color.Gray,
                    unfocusedLabelColor = Color.Gray,
                    cursorColor = Color(0xFF007bff)
                ),
                textStyle = TextStyle(color = Color(0xFF007bff))
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = celular,
                onValueChange = {celular = it},
                label = { Text("Celular") },

                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color(0xFF007bff),
                    unfocusedIndicatorColor = Color.LightGray,
                    focusedLabelColor = Color.Gray,
                    unfocusedLabelColor = Color.Gray,
                    cursorColor = Color(0xFF007bff)
                ),
                textStyle = TextStyle(color = Color(0xFF007bff))
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = senha,
                onValueChange = {senha = it},
                label = { Text("Senha") },

                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color(0xFF007bff),
                    unfocusedIndicatorColor = Color.LightGray,
                    focusedLabelColor = Color.Gray,
                    unfocusedLabelColor = Color.Gray,
                    cursorColor = Color(0xFF007bff)
                ),
                textStyle = TextStyle(color = Color(0xFF007bff))
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val resultado = db.inserirCliente(nome, sobrenome, celular, email, senha)

                    if (resultado) {
                        Toast.makeText(context, "Cadastro realizado com sucesso!", Toast.LENGTH_LONG).show()
                        navController.navigate("login")
                    } else {
                        Toast.makeText(context, "Erro ao cadastrar!", Toast.LENGTH_LONG).show()
                    }
                },
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
                modifier = Modifier.clickable { navController.navigate("login") }
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
