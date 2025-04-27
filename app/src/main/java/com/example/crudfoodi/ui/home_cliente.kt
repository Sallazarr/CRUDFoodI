package com.example.crudfoodi


import androidx.compose.foundation.clickable
import com.example.crudfoodi.nav.Navigation
import com.example.crudfoodi.models.Restaurante
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.foundation.background // <--- Este aqui é o que falta!

import coil.compose.AsyncImage
import androidx.compose.material3.TextField
import androidx.compose.material3.ButtonDefaults
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import java.io.File
import androidx.compose.ui.layout.ContentScale

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Text


import com.example.crudfoodi.ui.theme.CRUDFoodITheme

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight


import androidx.compose.ui.unit.sp

import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height

import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

import androidx.navigation.NavHostController
import com.example.crudfoodi.R
import com.example.crudfoodi.db.DBHelper

import androidx.compose.runtime.remember

import androidx.compose.ui.graphics.asImageBitmap

import android.graphics.BitmapFactory


@Composable
fun HomeCliente(navController: NavHostController) {
    val context = LocalContext.current
    val dbHelper = DBHelper(context)
    val listaRestaurantes by remember { mutableStateOf(dbHelper.listarRestaurantes()) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.White, Color(0xFF007bff))
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
                color = Color.White,
                modifier = Modifier.padding(start = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                CategoriaCard("Hambúrguer", R.drawable.burguer)
                CategoriaCard("Pizza", R.drawable.pizza)
                CategoriaCard("Sushi", R.drawable.sushi)
                CategoriaCard("Prato Feito", R.drawable.prato_feito)
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Restaurantes disponíveis",
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold),
                color = Color.White,
                modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
            )

            LazyColumn {
                items(listaRestaurantes) { restaurante ->
                    RestaurantCard(
                        nome = restaurante.nome,
                        imagemPath = restaurante.imagem
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}


@Composable
fun CategoriaCard(titulo: String, imagemResId: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(80.dp)
            .clickable { /* ação ao clicar */ }
    ) {
        Image(
            painter = painterResource(id = imagemResId),
            contentDescription = titulo,
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(12.dp))
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = titulo,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )
    }
}

@Composable
fun RestaurantCard(
    nome: String,
    imagemPath: String
) {
    // Debug: ver o caminho da imagem no Logcat
    println("Imagem path: $imagemPath")

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            // Corrigido: adiciona "file://" para carregar imagem local
            AsyncImage(
                model = "file://$imagemPath",
                contentDescription = nome,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(text = nome, style = MaterialTheme.typography.titleMedium)
 
            }
        }
    }
}