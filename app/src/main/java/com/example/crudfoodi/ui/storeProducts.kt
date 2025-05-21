package com.example.crudfoodi

import android.content.Context
import kotlinx.coroutines.launch

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.crudfoodi.models.Pedido

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun StoreProducts(navController: NavHostController) {
    val context = LocalContext.current
    val dbHelper = DBHelper(context)

    val idRestaurante = remember {
        val sharedPrefs = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        sharedPrefs.getInt("restaurante_selecionado_id", -1)
    }

    val idCliente = remember {
        val sharedPrefs = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        sharedPrefs.getInt(
            "id_cliente",
            -1
        ) // Aqui você deve garantir que "cliente_id" existe nas preferências
    }

    var restaurante by remember { mutableStateOf<Restaurante?>(null) }
    var produtos by remember { mutableStateOf<List<Produto>>(emptyList()) }
    var carrinho by remember { mutableStateOf<List<Produto>>(emptyList()) }
    var pedidos by remember { mutableStateOf<List<Pedido>>(emptyList()) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showCarrinho by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(idRestaurante) {
        restaurante = dbHelper.buscarRestaurantePorId(idRestaurante)
        produtos = dbHelper.buscarProdutosPorRestaurante(idRestaurante)
    }

    LaunchedEffect(idCliente) {
        if (idCliente != -1) {  // Verifica se idCliente é válido
        }
    }
    Log.d("DEBUG", "idCliente: $idCliente, idRestaurante: $idRestaurante")

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
                .padding(bottom = 80.dp) // espaço para o botão flutuante
        ) {
            restaurante?.getImagemUri()?.let { uri ->
                if (restaurante?.imagem == "logobgless") {
                    Image(
                        painter = painterResource(id = R.drawable.logobgless),
                        contentDescription = "Logo padrão",
                        modifier = Modifier
                            .size(110.dp)
                            .clip(RoundedCornerShape(55))
                            .border(2.dp, Color.Gray, RoundedCornerShape(55))
                            .align(Alignment.CenterHorizontally),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    AsyncImage(
                        model = uri,
                        contentDescription = "Imagem do restaurante",
                        modifier = Modifier
                            .size(110.dp)
                            .clip(RoundedCornerShape(55))
                            .border(2.dp, Color.Gray, RoundedCornerShape(55))
                            .align(Alignment.CenterHorizontally),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 24.dp)
            ) {
                item {
                    restaurante?.let {
                        Text(
                            text = it.nome,
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }
                }

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
                                if (produto.imagem == "burguer") {
                                    Image(
                                        painter = painterResource(id = R.drawable.burguer),
                                        contentDescription = "Produto padrão",
                                        modifier = Modifier
                                            .size(64.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                } else {
                                    AsyncImage(
                                        model = uri,
                                        contentDescription = "Imagem do produto",
                                        modifier = Modifier
                                            .size(64.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    produto.nome,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = Color.DarkGray
                                )
                                Text(
                                    produto.descricao,
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                                Text(
                                    "R$ %.2f".format(produto.valor),
                                    color = Color(0xFF007bff)
                                )
                            }

                            Button(
                                onClick = {
                                    carrinho = carrinho + produto
                                },
                                shape = RoundedCornerShape(50),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(
                                        0xFF007bff
                                    )
                                ),
                                contentPadding = PaddingValues(0.dp),
                                modifier = Modifier.size(40.dp)
                            ) {
                                Text(
                                    text = "+",
                                    color = Color.White,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }
        }

        // FAB fora da Column, flutuando sobre tudo
        FloatingActionButton(
            onClick = { showCarrinho = true },
            containerColor = Color(0xFF007bff),
            contentColor = Color.White,
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Abrir Carrinho"
            )
        }

        // Bottom Sheet do Carrinho
        if (showCarrinho) {
            ModalBottomSheet(
                onDismissRequest = { showCarrinho = false },
                sheetState = sheetState,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Carrinho", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Spacer(modifier = Modifier.height(8.dp))

                    if (carrinho.isEmpty()) {
                        Text("Seu carrinho está vazio.")
                    } else {
                        LazyColumn {
                            items(carrinho) { produto ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    produto.getImagemUri()?.let { uri ->
                                        if (produto.imagem == "burguer") {
                                            Image(
                                                painter = painterResource(id = R.drawable.burguer),
                                                contentDescription = "Produto padrão",
                                                modifier = Modifier
                                                    .size(40.dp)
                                                    .clip(RoundedCornerShape(8.dp)),
                                                contentScale = ContentScale.Crop
                                            )
                                        } else {
                                            AsyncImage(
                                                model = uri,
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .size(40.dp)
                                                    .clip(RoundedCornerShape(8.dp)),
                                                contentScale = ContentScale.Crop
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Text(produto.nome)
                                        Text("R$ %.2f".format(produto.valor), fontSize = 12.sp)
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        val total = carrinho.sumOf { it.valor }
                        Text("Total: R$ %.2f".format(total), fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                // Navegar para a tela de checkout
                                navController.navigate("checkout")
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF007bff)
                            )
                        ) {
                            Text("Finalizar Pedido", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}