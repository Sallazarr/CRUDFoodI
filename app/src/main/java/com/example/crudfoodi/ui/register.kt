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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight



import androidx.compose.ui.focus.FocusDirection
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.platform.LocalFocusManager

import androidx.compose.ui.focus.focusRequester
import androidx.compose.runtime.remember


@Composable
fun RegisterScreen(navController: NavHostController) {
    // Tela de Registro


    val focusRequesterNome = remember { FocusRequester() }
    val focusRequesterEmail = remember { FocusRequester() }
    val focusRequesterSenha = remember { FocusRequester() } // Próximo campo
    val focusRequesterSobrenome = remember { FocusRequester() }
    val focusRequesterCelular = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current


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

                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .focusRequester(focusRequesterNome),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusRequesterSobrenome.requestFocus() }
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color(0xFF007bff),
                    unfocusedIndicatorColor = Color.Gray,
                    focusedLabelColor = Color.DarkGray,
                    unfocusedLabelColor = Color.DarkGray,
                    cursorColor = Color(0xFF007bff)
                ),
                textStyle = TextStyle(color = Color(0xFF007bff))
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = sobrenome,
                onValueChange = {sobrenome = it},
                label = { Text("Sobrenome") },

                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .focusRequester(focusRequesterSobrenome),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusRequesterEmail.requestFocus() }
                ),
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

                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .focusRequester(focusRequesterEmail),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusRequesterCelular.requestFocus() }
                ),
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

                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .focusRequester(focusRequesterCelular),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusRequesterSenha.requestFocus() }
                ),
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

                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .focusRequester(focusRequesterSenha),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                ),
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
