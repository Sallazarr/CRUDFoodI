package com.example.crudfoodi.ui

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

import androidx.compose.material3.ButtonDefaults
import androidx.navigation.NavHostController
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import com.example.crudfoodi.db.DBHelper
import com.example.crudfoodi.models.Produto
import androidx.compose.foundation.border
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.graphics.Brush
import com.example.crudfoodi.R

import androidx.compose.foundation.layout.padding






import androidx.compose.foundation.layout.fillMaxSize


import androidx.compose.material3.Text



import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.height

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions

import androidx.compose.runtime.Composable


import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction


import androidx.compose.foundation.layout.Spacer
import com.example.crudfoodi.moveImage.salvarImagemProdutoNoApp

import java.io.File


@Composable
fun AddProdutoScreen(navController: NavHostController) {
    var nome by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var valor by remember { mutableStateOf("") }
    var imagemUri by remember { mutableStateOf<Uri?>(null) } // Variável para armazenar a imagem
    val context = LocalContext.current
    val dbHelper = DBHelper(context)

    val focusManager = LocalFocusManager.current

    val focusRequesterNome = remember { FocusRequester() }
    val focusRequesterDesc = remember { FocusRequester() }
    val focusRequesterValor = remember { FocusRequester() }


    val idRestaurante = remember {
        val prefs = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val id = prefs.getInt("id_restaurante", -1)
        Log.d("AddProdutoScreen", "ID do restaurante: $id")
        id
    }

    // Se o idRestaurante não for encontrado, você pode exibir uma mensagem de erro ou navegar para a tela de login.
    if (idRestaurante == -1) {
        Toast.makeText(context, "Erro: Restaurante não encontrado!", Toast.LENGTH_SHORT).show()
        navController.popBackStack()
    }

    // Função para lançar a atividade de seleção de imagem
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imagemUri = uri
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colorStops = arrayOf(
                        0.0f to Color.White,
                        0.7f to Color.White,
                        1.0f to Color(0xFF60adff)
                    )
                )
            )
            .padding(16.dp),
                contentAlignment = Alignment.Center
    ) {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier.verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logobgless),
                contentDescription = "Logo FoodI",
                modifier = Modifier
                    .height(200.dp)
                    .width(200.dp)
                    .padding(3.dp)
            )
        Column(modifier = Modifier.padding(16.dp)) {
            TextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome do Produto") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .focusRequester(focusRequesterNome),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusRequesterDesc.requestFocus() }
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


            TextField(
                value = descricao,
                onValueChange = { descricao = it },
                label = { Text("Descrição") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .focusRequester(focusRequesterDesc),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusRequesterValor.requestFocus() }
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


            TextField(
                value = valor,
                onValueChange = { valor = it },
                label = { Text("Valor") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .focusRequester(focusRequesterValor),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus(force = true) }
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

            // Botão para selecionar imagem
            Button(
                onClick = { launcher.launch("image/*") },
                modifier = Modifier
                .padding(top = 12.dp)
                .align(Alignment.CenterHorizontally)
                .height(50.dp), // Altura do botão
            shape = RoundedCornerShape(14.dp), // Borda arredondada
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF007bff) // Cor do botão
            )
            )   {
                Text("Selecionar Imagem")
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Exibir a imagem selecionada (se houver)
            imagemUri?.let {
                AsyncImage(
                    model = it,
                    contentDescription = "Imagem do produto",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.5.dp, Color.DarkGray, RoundedCornerShape(8.dp))
                        .align(Alignment.CenterHorizontally),
                            contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botão para salvar o produto
            Button(
                onClick = {
                    // Salva a imagem do produto e obtém o caminho
                    val imagemSalva = imagemUri?.let { uri ->
                        salvarImagemProdutoNoApp(context, uri)  // Passando "produto" como tipo
                    } ?: ""

                    val produto = Produto(
                        id = 0, // ID será gerado automaticamente
                        idRestaurante = idRestaurante,
                        imagem = imagemSalva,
                        nome = nome,
                        descricao = descricao,
                        valor = valor.toDouble()
                    )
                    val sucesso = dbHelper.adicionarProduto(produto)
                    if (sucesso) {
                        Toast.makeText(context, "Produto adicionado com sucesso!", Toast.LENGTH_SHORT).show()
                        Log.d("ImgProduto", "Imagem do Produto de Id ${produto.id} Diretório: ${produto.imagem}")
                        navController.popBackStack()
                    } else {
                        Toast.makeText(context, "Erro ao adicionar produto!", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .padding(top = 12.dp)
                    .align(Alignment.CenterHorizontally)
                    .height(50.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF007bff)
                )
            ) {
                Text("Salvar Produto")
            }

        }
    }
}}