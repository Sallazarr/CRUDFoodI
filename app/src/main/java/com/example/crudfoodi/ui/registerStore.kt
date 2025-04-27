package com.example.crudfoodi
import com.example.crudfoodi.db.DBHelper
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

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.sp

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

import android.net.Uri
import android.widget.Toast

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


@Composable
fun RegisterStoreScreen(navController: NavHostController) {
    // Tela de Registro

    val context = LocalContext.current
    val dbHelper = DBHelper(context)



    var endereco by remember { mutableStateOf("") }
    var celular by remember { mutableStateOf("") }
    var cnpj by remember { mutableStateOf("") }
    var nomeLoja by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

    val imageUri = remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        imageUri.value = uri
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.White, Color(0xFF007bff))
                )
            )
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier.verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
        Image(
                painter = painterResource(id = R.drawable.logobgless),
                contentDescription = "Logo FoodI",
                modifier = Modifier
                    .height(200.dp)
                    .width(200.dp)
                    .padding( 3.dp)
            )
            Text(
                text = "Criar Conta",
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                color = Color.White
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Inputs de Registro

            TextField(
                value = cnpj,
                onValueChange = { cnpj = it },
                label = { Text("CNPJ") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color(0xFF007bff),
                    unfocusedIndicatorColor = Color.LightGray,
                    focusedLabelColor = Color.Gray,
                    unfocusedLabelColor = Color(0xFF007bff),
                    cursorColor = Color(0xFF007bff)
                ),
                textStyle = TextStyle(color = Color(0xFF007bff))
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = nomeLoja,
                onValueChange = {nomeLoja = it},
                label = { Text("Nome Estabelecimento") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color(0xFF007bff),
                    unfocusedIndicatorColor = Color.LightGray,
                    focusedLabelColor = Color.Gray,
                    unfocusedLabelColor = Color(0xFF007bff),
                    cursorColor = Color(0xFF007bff)
                ),
                textStyle = TextStyle(color = Color(0xFF007bff))
            )

            Spacer(modifier = Modifier.height(8.dp))

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
                    unfocusedLabelColor = Color(0xFF007bff),
                    cursorColor = Color(0xFF007bff)
                ),
                textStyle = TextStyle(color = Color(0xFF007bff))
            )

            Spacer(modifier = Modifier.height(8.dp))

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
                    unfocusedLabelColor = Color(0xFF007bff),
                    cursorColor = Color(0xFF007bff)
                ),
                textStyle = TextStyle(color = Color(0xFF007bff))
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = endereco,
                onValueChange = {endereco = it},
                label = { Text("Endereço") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color(0xFF007bff),
                    unfocusedIndicatorColor = Color.LightGray,
                    focusedLabelColor = Color.Gray,
                    unfocusedLabelColor = Color(0xFF007bff),
                    cursorColor = Color(0xFF007bff)
                ),
                textStyle = TextStyle(color = Color(0xFF007bff))
            )



            Spacer(modifier = Modifier.height(8.dp))

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
                    unfocusedLabelColor = Color(0xFF007bff),
                    cursorColor = Color(0xFF007bff)
                ),
                textStyle = TextStyle(color = Color(0xFF007bff))
            )



            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { launcher.launch("image/*") },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text("Selecionar Imagem do Restaurante", color = Color(0xFF007bff))
            }

            imageUri.value?.let {
                Text("Imagem selecionada: ${it.lastPathSegment}", color = Color.White, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    val imagemPath = imageUri.value?.toString() ?: ""



                    val resultado = dbHelper.insertRestaurante(
                        nome = nomeLoja,
                        celular = celular, // você pode adicionar um campo se quiser
                        endereco = endereco, // idem
                        imagem = imagemPath,
                        email = email,
                        senha = senha,
                        cnpj = cnpj);

                        if (resultado) {
                            // Sucesso no cadastro
                            Toast.makeText(context, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                            // Navega para a tela de login após o sucesso
                            navController.navigate("login")
                        } else {
                            // Falha no cadastro
                            Toast.makeText(
                                context,
                                "Falha no cadastro. Tente novamente.",
                                Toast.LENGTH_SHORT
                            ).show()
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

            Spacer(modifier = Modifier.height(8.dp))

            // Texto de registro
            Text(
                text = "Já tem uma conta? Entrar",
                color = Color(0xFFffffff),
                modifier = Modifier.clickable { navController.navigate("login") }
            )
        }
    }
}
