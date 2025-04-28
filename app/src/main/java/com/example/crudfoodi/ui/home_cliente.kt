package com.example.crudfoodi



import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*

import androidx.compose.material3.*
import androidx.compose.runtime.*



import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

import coil.compose.AsyncImage






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

// Adicione estas linhas junto com os outros imports
import java.io.FileNotFoundException
import java.lang.Exception // Para o 'e.message'

import androidx.navigation.NavHostController

import com.example.crudfoodi.db.DBHelper

import androidx.compose.runtime.remember
import coil.request.ImageRequest


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
    imagemPath: String,
    endereco: String
) {
    val context = LocalContext.current
    val contentResolver = context.contentResolver

    // 1. Verificação de permissões
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            Log.w("PERMISSIONS", "Permissão de leitura negada")
        }
    }

    // 2. Processamento seguro da URI
    val uri = remember(imagemPath) {
        try {
            Uri.parse(imagemPath)
                .normalizeScheme()
                .buildUpon()
                .build()
                .also {
                    Log.d("URI_DEBUG", "URI processada: $it")
                }
        } catch (e: Exception) {
            Log.e("URI_ERROR", "Falha ao processar URI: $imagemPath", e)
            null
        }
    }

    // 3. Verificação de acesso ao arquivo
    var hasFileAccess by remember { mutableStateOf(false) }

    LaunchedEffect(uri) {
        uri?.let {
            try {
                contentResolver.openInputStream(it)?.use {
                    hasFileAccess = true
                    Log.d("FILE_ACCESS", "Acesso ao arquivo concedido")
                }
            } catch (e: SecurityException) {
                Log.e("SECURITY", "Erro de permissão: ${e.message}")
                hasFileAccess = false
                permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            } catch (e: FileNotFoundException) {
                Log.e("FILE_ERROR", "Arquivo não encontrado: ${e.message}")
                hasFileAccess = false
            }
        }
    }

    // 4. Componente principal
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(8.dp),
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
            // 5. Exibição da imagem
            if (hasFileAccess && uri != null) {
                // Modifique a chamada do AsyncImage para:
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(uri)
                        .apply {
                            if (uri?.scheme == "content") {
                                allowConversionToBitmap(false)
                            }
                        }
                        .build(),
            } else {
                ImageFallback()
            }

            Spacer(modifier = Modifier.width(16.dp))

            // 6. Informações do restaurante
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
