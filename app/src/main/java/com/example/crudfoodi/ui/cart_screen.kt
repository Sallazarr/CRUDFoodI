package com.example.crudfoodi.ui

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.crudfoodi.db.DBHelper
import com.example.crudfoodi.models.Produto
import com.example.crudfoodi.models.Restaurante


@Composable
fun CartScreen(navController: NavHostController) {
    val context = LocalContext.current
    val dbHelper = DBHelper(context)

    var carrinho by remember { mutableStateOf<List<Produto>>(emptyList()) }

    // Carregando o carrinho
    LaunchedEffect(Unit) {
        val savedCarrinho = // Carregar do banco de dados ou SharedPreferences
            carrinho = savedCarrinho
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Carrinho",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color(0xFF007bff)
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (carrinho.isEmpty()) {
            Text("Seu carrinho está vazio.")
        } else {
            LazyColumn {
                items(carrinho) { produto ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        produto.getImagemUri()?.let { uri ->
                            AsyncImage(
                                model = uri,
                                contentDescription = "Imagem do produto",
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(produto.nome, fontWeight = FontWeight.Bold)
                            Text("R$ %.2f".format(produto.valor), fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        // Adicionar um botão de finalizar a compra
        Button(
            onClick = {
                // Ação de finalizar a compra
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007bff))
        ) {
            Text(text = "Finalizar Compra", color = Color.White)
        }
    }
}