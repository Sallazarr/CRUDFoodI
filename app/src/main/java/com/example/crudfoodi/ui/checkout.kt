package com.example.crudfoodi

import android.content.Context
import kotlinx.coroutines.launch
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.crudfoodi.db.DBHelper
import com.example.crudfoodi.models.Produto
import com.example.crudfoodi.models.Restaurante
import com.example.crudfoodi.viewmodel.CarrinhoViewModel

@Composable
fun CheckoutScreen(navController: NavController) {
    var rua by remember { mutableStateOf("") }
    var bairro by remember { mutableStateOf("") }
    var numero by remember { mutableStateOf("") }
    var cep by remember { mutableStateOf("") }
    var complemento by remember { mutableStateOf("") }
    var tipoPagamento by remember { mutableStateOf("Credito") }

    val opcoesPagamento = listOf("Credito", "Debito", "Pix")

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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    "Endereço e Pagamento",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color(0xFF007bff)
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                TextField(
                    value = rua,
                    onValueChange = { rua = it },
                    label = { Text("Rua") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp)),
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
            }

            item {
                TextField(
                    value = bairro,
                    onValueChange = { bairro = it },
                    label = { Text("Bairro") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp)),
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
            }

            item {
                TextField(
                    value = numero,
                    onValueChange = { numero = it },
                    label = { Text("Número") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp)),
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
            }

            item {
                TextField(
                    value = cep,
                    onValueChange = { cep = it },
                    label = { Text("CEP") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp)),
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
            }

            item {
                TextField(
                    value = complemento,
                    onValueChange = { complemento = it },
                    label = { Text("Complemento") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp)),
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
            }

            item {
                Text(
                    "Forma de pagamento:",
                    style = TextStyle(fontWeight = FontWeight.SemiBold, color = Color(0xFF007bff))
                )
                Row {
                    opcoesPagamento.forEach { opcao ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(end = 16.dp)
                        ) {
                            RadioButton(
                                selected = tipoPagamento == opcao,
                                onClick = { tipoPagamento = opcao },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Color(0xFF007bff)
                                )
                            )
                            Text(opcao, color = Color.DarkGray)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Button(
                    onClick = {
                        // Ação ao confirmar pedido
                        navController.navigate("confirmation")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF007bff)
                    )
                ) {
                    Text("Confirmar Pedido", color = Color.White)
                }
            }
        }
    }
}
