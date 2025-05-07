package com.example.crudfoodi

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
fun StoreProducts(navController: NavHostController) {
    val context = LocalContext.current
    val dbHelper = DBHelper(context)

    // Pegando o ID do restaurante logado salvo no SharedPreferences
    val idRestaurante = remember {
        val sharedPrefs = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val id = sharedPrefs.getInt("id_restaurante", -1)

        Log.d("storeP", "ID do restaurante recuperado: ${id} Diretório de imagem: ${produto.imagem}" )
        id
    }

    var restaurante by remember { mutableStateOf<Restaurante?>(null) }
    var produtos by remember { mutableStateOf<List<Produto>>(emptyList()) }

    LaunchedEffect(idRestaurante) {
        restaurante = dbHelper.buscarRestaurantePorId(idRestaurante)
        produtos = dbHelper.buscarProdutosPorRestaurante(idRestaurante)
        Log.d(
            "StoreProducts",
            "Carregados ${produtos.size} produtos do restaurante ID $idRestaurante"
        )
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            restaurante?.let {
                Text(
                    text = it.nome,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(produtos) { produto ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
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
                            produto.getImagemUri()?.let { uri ->
                                AsyncImage(
                                    model = produto.getImagemUri(),
                                    contentDescription = "Imagem do produto",
                                    modifier = Modifier
                                        .size(64.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
                                    contentScale = ContentScale.Crop
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column {
                                Text(produto.nome, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Text(produto.descricao, fontSize = 14.sp, color = Color.Gray)
                                Text("R$ %.2f".format(produto.valor), color = Color(0xFF007bff))
                            }
                        }
                    }
                }
            }
        }
    }
}