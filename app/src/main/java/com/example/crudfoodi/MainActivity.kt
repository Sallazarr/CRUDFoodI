package com.example.crudfoodi
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.crudfoodi.ui.theme.CRUDFoodITheme
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*


import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {

 override fun onCreate(savedInstanceState: Bundle?) {
  super.onCreate(savedInstanceState)
  setContent {
   LoginScreen() // Chama a função que define a tela com Compose
  }
 }
}

@Composable
fun LoginScreen() {
 // Toda a tela com cor de fundo personalizada
 Box(
  modifier = Modifier
   .fillMaxSize()
   .background(Color(0xFF121212)) // Cor de fundo da tela (Dark Mode)
   .padding(24.dp),
  contentAlignment = Alignment.Center // Centraliza tudo dentro da Box
 ) {
  Column(
   horizontalAlignment = Alignment.CenterHorizontally, // Centraliza os itens horizontalmente
  ) {
   Text(
    text = "Bem-vindo à FoodI!",
    style = TextStyle(
     fontSize = 24.sp,
     fontWeight = FontWeight.Bold
    ),
    color = Color.White // Cor do texto
   )

   Spacer(modifier = Modifier.height(40.dp))

   // Input de Email
   TextField(
    value = "",
    onValueChange = {},
    label = { Text("Email", color = Color.LightGray) },
    modifier = Modifier
     .fillMaxWidth()
     .clip(RoundedCornerShape(10.dp)), // Bordas arredondadas
    colors = TextFieldDefaults.textFieldColors(
     containerColor = Color(0xFF1F1F1F), // Fundo do input
     focusedIndicatorColor = Color(0xFF007bff),
     unfocusedIndicatorColor = Color.Gray,
     textColor = Color.White
    )
   )

   Spacer(modifier = Modifier.height(16.dp))

   // Input de Senha
   TextField(
    value = "",
    onValueChange = {},
    label = { Text("Senha", color = Color.LightGray) },
    modifier = Modifier
     .fillMaxWidth()
     .clip(RoundedCornerShape(10.dp)),
    colors = TextFieldDefaults.textFieldColors(
     containerColor = Color(0xFF1F1F1F),
     focusedIndicatorColor = Color(0xFF007bff),
     unfocusedIndicatorColor = Color.Gray,
     textColor = Color.White
    )
   )

   Spacer(modifier = Modifier.height(24.dp))

   // Botão de Entrar
   Button(
    onClick = {},
    modifier = Modifier
     .fillMaxWidth()
     .height(50.dp), // Altura do botão
    shape = RoundedCornerShape(10.dp), // Borda arredondada
    colors = ButtonDefaults.buttonColors(
     containerColor = Color(0xFF007bff) // Cor do botão
    )
   ) {
    Text("Entrar", color = Color.White)
   }

   Spacer(modifier = Modifier.height(16.dp))

   // Texto de registrar
   Text(
    text = "Registrar-se",
    color = Color(0xFF007bff)
   )

   Spacer(modifier = Modifier.height(8.dp))

   // Texto de esqueceu senha
   Text(
    text = "Esqueceu a senha?",
    color = Color(0xFF007bff)
   )
  }
 }
}
