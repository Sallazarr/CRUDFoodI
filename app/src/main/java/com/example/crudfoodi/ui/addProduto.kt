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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.ButtonDefaults
import androidx.navigation.NavHostController
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import com.example.crudfoodi.db.DBHelper
import com.example.crudfoodi.models.Produto
import androidx.compose.foundation.border


@Composable
fun AddProdutoScreen(navController: NavHostController) {
    var nome by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var valor by remember { mutableStateOf("") }
    var imagemUri by remember { mutableStateOf<Uri?>(null) } // Variável para armazenar a imagem
    val context = LocalContext.current
    val dbHelper = DBHelper(context)

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
        contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        imagemUri = uri
    }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome do Produto") }
        )
        TextField(
            value = descricao,
            onValueChange = { descricao = it },
            label = { Text("Descrição") }
        )
        TextField(
            value = valor,
            onValueChange = { valor = it },
            label = { Text("Valor") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botão para selecionar imagem
        Button(onClick = { launcher.launch("image/*") }) {
            Text("Selecionar Imagem")
        }

        // Exibir a imagem selecionada (se houver)
        imagemUri?.let {
            AsyncImage(
                model = it,
                contentDescription = "Imagem do produto",
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                    .padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botão para salvar o produto
        Button(onClick = {
            val produto = Produto(
                id = 0, // ID será gerado automaticamente
                idRestaurante = idRestaurante,
                imagem = imagemUri.toString(), // Salva a URI da imagem
                nome = nome,
                descricao = descricao,
                valor = valor
            )
            val sucesso = dbHelper.adicionarProduto(produto)
            if (sucesso) {
                Toast.makeText(context, "Produto adicionado com sucesso!", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            } else {
                Toast.makeText(context, "Erro ao adicionar produto!", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Salvar Produto")
        }
    }
}
