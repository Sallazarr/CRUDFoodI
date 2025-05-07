package com.example.crudfoodi
import com.example.crudfoodi.permission.requestStoragePermission


import com.example.crudfoodi.models.Restaurante
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*

import androidx.compose.material3.*
import androidx.compose.runtime.*

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.activity.result.ActivityResultLauncher

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

import coil.compose.AsyncImage



import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding



import androidx.compose.foundation.clickable

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip

import androidx.compose.foundation.background // <--- Este aqui é o que falta!

import androidx.compose.ui.layout.ContentScale



import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Text


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight


import androidx.compose.ui.unit.sp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.height

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color


import androidx.navigation.NavHostController

import com.example.crudfoodi.db.DBHelper

import androidx.compose.runtime.remember
import androidx.compose.ui.text.style.TextAlign

import com.example.crudfoodi.models.Produto
import java.io.File




@Composable
fun HomeRestaurante(navController: NavHostController) {
    val context = LocalContext.current
    val dbHelper = DBHelper(context)

    // Pegando o ID do restaurante logado salvo no SharedPreferences
    val idRestaurante = remember {
        val sharedPrefs = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val id = sharedPrefs.getInt("id_restaurante", -1)

        Log.d("HomeRestaurante", "ID do restaurante recuperado: $id")
        id
    }

    var restaurante by remember { mutableStateOf<Restaurante?>(null) }
    var produtos by remember { mutableStateOf<List<Produto>>(emptyList()) }

    // Carregando o restaurante do banco
    LaunchedEffect(idRestaurante) {
        val restauranteCarregado = dbHelper.buscarRestaurantePorId(idRestaurante)

        restaurante = restauranteCarregado
        if (restauranteCarregado != null) {
            Log.d("HomeRestaurante", "Restaurante carregado: ${restauranteCarregado.nome}")
        } else {
            Log.e("HomeRestaurante", "Nenhum restaurante encontrado para o ID: $idRestaurante")
        }
        val produtosCarregados = dbHelper.buscarProdutosPorRestaurante(idRestaurante)


        produtos = produtosCarregados
        Log.d("HomeRestaurante", "Produtos carregados: ${produtos.size}")
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
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Conteúdo fixo acima (logo, imagem, botões)
            // ...
            // Barra superior com ícone de usuário e logo
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logobgless),
                    contentDescription = "Logo FoodI",
                    modifier = Modifier
                        .height(60.dp)
                        .align(Alignment.TopStart)
                )
            }

            Spacer(modifier = Modifier.height(8.dp)) // Pequeno espaçamento após a logo

// Imagem do restaurante (subida visualmente na tela)
            restaurante?.getImagemUri()?.let { uri ->
                AsyncImage(
                    model = uri,
                    contentDescription = "Imagem do restaurante",
                    modifier = Modifier
                        .size(110.dp) // imagem um pouco maior
                        .clip(RoundedCornerShape(55)) // borda circular proporcional
                        .border(2.dp, Color.Gray, RoundedCornerShape(55))
                        .align(Alignment.CenterHorizontally),
                    contentScale = ContentScale.Crop
                )
            }

            // Exibição do nome do restaurante
            restaurante?.let {
                Text(
                    text = it.nome,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color(0xFF007bff)
                    ),
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Editar Restaurante", color = Color.White)

            // Botão Adicionar Produto (agora clicável!)
            Button(
                onClick = {
                    navController.navigate("addProduto")
                },
                modifier = Modifier
                    .padding(top = 12.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF007bff)
                )
            ) {
                Text(text = "Adicionar Produto", color = Color.White)
            }


            // Título dos produtos
            Text(
                text = "Seus Produtos",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 8.dp),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
            )

            // Lista de produtos
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(produtos) { produto ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(12.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Imagem do produto
                            produto.getImagemUri()?.let { uri ->
                                AsyncImage(
                                    model = uri,
                                    contentDescription = "Imagem do produto",
                                    modifier = Modifier
                                        .size(64.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                            }

                            // Informações do produto
                            Column {
                                Text(produto.nome, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Text(produto.descricao, fontSize = 14.sp, color = Color.Gray)
                                Text(
                                    "R$ %.2f".format(produto.valor),
                                    fontSize = 14.sp,
                                    color = Color(0xFF007bff)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
    }