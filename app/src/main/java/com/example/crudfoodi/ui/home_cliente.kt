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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color


import androidx.navigation.NavHostController

import com.example.crudfoodi.db.DBHelper

import androidx.compose.runtime.remember
import androidx.compose.ui.text.style.TextAlign
import coil.request.ImageRequest
import java.io.File



@Composable
fun HomeCliente(navController: NavHostController, requestPermissionLauncher: ActivityResultLauncher<String>) {
    requestStoragePermission(requestPermissionLauncher)
    val context = LocalContext.current
    val dbHelper = DBHelper(context)
    val listaRestaurantes = remember { mutableStateListOf<Restaurante>() }

    LaunchedEffect(Unit) {
        val restaurantes = dbHelper.listarRestaurantes()
        listaRestaurantes.clear()
        listaRestaurantes.addAll(restaurantes)
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
        Column {
            Image(
                painter = painterResource(id = R.drawable.logobgless),
                contentDescription = "Logo FoodI",
                modifier = Modifier
                    .height(80.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Sugestões para você",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                color = Color.DarkGray,
                modifier = Modifier.padding(start = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    CategoriaCard("Hambúrguer", R.drawable.burguer, Modifier.weight(1f))
                    CategoriaCard("Pizza", R.drawable.pizza, Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CategoriaCard("Sushi", R.drawable.sushi, Modifier.weight(1f))
                    CategoriaCard("Prato Feito", R.drawable.prato_feito, Modifier.weight(1f))
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Restaurantes disponíveis",
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold),
                color = Color.DarkGray,
                modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
            )

            LazyColumn {
                items(listaRestaurantes) { restaurante ->
                    RestaurantCard(
                        nome = restaurante.nome,
                        imagemPath = restaurante.imagem,
                        endereco = restaurante.endereco
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}


@Composable
fun CategoriaCard(titulo: String, imagemResId: Int, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F8F8)),
        modifier = modifier
            .height(95.dp)
            .padding(4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp)
        ) {
            Image(
                painter = painterResource(id = imagemResId),
                contentDescription = titulo,
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = titulo,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333),
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
    }
}


@Composable
fun RestaurantCard(
    nome: String,
    imagemPath: String,
    endereco: String
) {
    val context = LocalContext.current
    val file = File(imagemPath)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(8.dp)
        .border(2.dp, Color.DarkGray, shape = RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize()
        ) {
            if (file.exists()) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(file) // ← caminho do arquivo salvo internamente
                        .crossfade(true)
                        .build(),
                    contentDescription = nome,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop,
                    error = painterResource(id = R.drawable.error),
                    placeholder = painterResource(id = R.drawable.loading)
                )
                Log.d("IMAGE_LOADING", "Imagem carregada de: ${file.absolutePath}")
            } else {
                ImageFallback()
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxHeight()
            ) {
                Text(
                    text = nome,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = endereco,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}


@Composable
private fun ImageFallback() {
    Box(
        modifier = Modifier
            .size(80.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.error),
            contentDescription = "Imagem não disponível",
            modifier = Modifier.size(40.dp))
    }
}
