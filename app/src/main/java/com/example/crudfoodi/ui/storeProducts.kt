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
import androidx.compose.material3.ExperimentalMaterial3Api

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun StoreProducts(navController: NavHostController) {
    val context = LocalContext.current
    val dbHelper = DBHelper(context)

    val idRestaurante = remember {
        val sharedPrefs = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        sharedPrefs.getInt("restaurante_selecionado_id", -1)
    }

    var restaurante by remember { mutableStateOf<Restaurante?>(null) }
    var produtos by remember { mutableStateOf<List<Produto>>(emptyList()) }
    var carrinho by remember { mutableStateOf<List<Produto>>(emptyList()) }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showCarrinho by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(idRestaurante) {
        restaurante = dbHelper.buscarRestaurantePorId(idRestaurante)
        produtos = dbHelper.buscarProdutosPorRestaurante(idRestaurante)
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
    ) {
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
                        .padding(vertical = 8.dp)
                        .clickable {
                            carrinho = carrinho + produto
                        },
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Imagem do Produto
                        AsyncImage(
                            model = produto.getImagemUri(),
                            contentDescription = null,
                            modifier = Modifier
                                .size(80.dp) // Imagem maior
                                .clip(RoundedCornerShape(10.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(16.dp)) // Mais espaço entre imagem e texto

                        // Detalhes do Produto
                        Column(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            Text(
                                text = produto.nome,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                            Text(
                                text = "R$ %.2f".format(produto.valor),
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }

                        // Botão para adicionar ao carrinho com símbolo "+"
                        Button(
                            onClick = { carrinho = carrinho + produto },
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF007bff)),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                text = "+",
                                fontSize = 24.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

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

        // Modal Bottom Sheet para o Carrinho
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
                                    AsyncImage(
                                        model = produto.getImagemUri(),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(RoundedCornerShape(8.dp)),
                                        contentScale = ContentScale.Crop
                                    )
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
                                // Lógica de finalizar pedido
                                showCarrinho = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Finalizar Pedido")
                        }
                    }
                }
            }
        }
    }
}