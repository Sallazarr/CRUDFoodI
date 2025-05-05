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

import androidx.compose.foundation.layout.height

import androidx.compose.foundation.layout.width
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

    // Carregando o restaurante do banco
    LaunchedEffect(idRestaurante) {
        val restauranteCarregado = dbHelper.buscarRestaurantePorId(idRestaurante)
        restaurante = restauranteCarregado
        if (restauranteCarregado != null) {
            Log.d("HomeRestaurante", "Restaurante carregado: ${restauranteCarregado.nome}")
        } else {
            Log.e("HomeRestaurante", "Nenhum restaurante encontrado para o ID: $idRestaurante")
        }
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
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Barra superior com ícone de usuário e logo
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                // Ícone de usuário no canto superior esquerdo
                IconButton(
                    onClick = {
                        // Ação para ir para a tela de perfil ou outro destino
                    },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Ícone de usuário",
                        tint = Color.Black
                    )
                }

                // Logo do app no centro
                Image(
                    painter = painterResource(id = R.drawable.logobgless),
                    contentDescription = "Logo FoodI",
                    modifier = Modifier
                        .height(80.dp)
                        .align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Foto de perfil do restaurante
            restaurante?.getImagemUri()?.let { uri ->
                AsyncImage(
                    model = uri,
                    contentDescription = "Imagem do restaurante",
                    modifier = Modifier
                        .size(90.dp) // Define o tamanho do ícone
                        .clip(RoundedCornerShape(50)) // Torna a imagem circular
                        .border(2.dp, Color.Gray, RoundedCornerShape(50)) // Adiciona uma borda ao redor da imagem
                        .align(Alignment.CenterHorizontally), // Alinha a imagem ao centro
                    contentScale = ContentScale.Crop // Faz com que a imagem preencha o espaço circular, cortando se necessário
                )
            }

            // Exibição do nome do restaurante
            restaurante?.let {
                Text(
                    text = it.nome,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    ),
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Ação para editar informações do restaurante (exemplo)
            Button(
                onClick = {
                    // Ação para editar as informações do restaurante
                },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Editar Restaurante")
            }
        }
    }
}
