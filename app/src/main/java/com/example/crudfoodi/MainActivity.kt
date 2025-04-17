package com.example.crudfoodi
import androidx.compose.foundation.clickable
import com.example.crudfoodi.nav.Navigation
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.foundation.background // <--- Este aqui é o que falta!
import androidx.navigation.NavHostController
import androidx.compose.ui.text.input.PasswordVisualTransformation

import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.graphics.Brush

import androidx.compose.material3.TextField
import androidx.compose.material3.ButtonDefaults
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import com.example.crudfoodi.ui.theme.CRUDFoodITheme

//Import para SQLite
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {

 override fun onCreate(savedInstanceState: Bundle?) {
  super.onCreate(savedInstanceState)
  setContent {

   CRUDFoodITheme {
    Navigation()
   }
  }
 }
}

@Composable
fun LoginScreen(navController: NavHostController){

 // Toda a tela com cor de fundo personalizada
 Box(
  modifier = Modifier
   .fillMaxSize()
   .background(
    brush = Brush.verticalGradient(
     colors = listOf(Color.White, Color(0xFF007bff))
    )
   )
   .padding(24.dp),
  contentAlignment = Alignment.Center
 ) {
  Column(
   horizontalAlignment = Alignment.CenterHorizontally, // Centraliza os itens horizontalmente
  ) {
   Image(
    painter = painterResource(id = R.drawable.logobgless),
    contentDescription = "Logo FoodI",
    modifier = Modifier
     .height(200.dp) // Ajuste o tamanho como quiser
     .width(200.dp)
     .padding(bottom = 6.dp)

   )
   Text(
    text = "Bem-vindo à FoodI!",
    style = TextStyle(
     fontSize = 24.sp,
     fontWeight = FontWeight.Bold
    ),
    color = Color.White
   )


   Spacer(modifier = Modifier.height(40.dp))

   // Input de Email
   TextField(
    value = "",
    onValueChange = {},
    label = { Text("Email") },
    modifier = Modifier
     .fillMaxWidth()
     .clip(RoundedCornerShape(10.dp)),
    colors = TextFieldDefaults.colors(
     focusedContainerColor = Color.White,
     unfocusedContainerColor = Color.White,
     focusedIndicatorColor = Color(0xFF007bff),
     unfocusedIndicatorColor = Color.LightGray,
     focusedLabelColor = Color(0xFF007bff),
     unfocusedLabelColor = Color.Gray,
     cursorColor = Color(0xFF007bff)
    ),
    textStyle = TextStyle(color = Color.Black)
   )



   Spacer(modifier = Modifier.height(16.dp))

   // Input de Senha
   TextField(
    visualTransformation = PasswordVisualTransformation(),

            value = "",
    onValueChange = {},
    label = { Text("Senha") },
    modifier = Modifier
     .fillMaxWidth()
     .clip(RoundedCornerShape(10.dp)),
    colors = TextFieldDefaults.colors(
     focusedContainerColor = Color.White,
     unfocusedContainerColor = Color.White,
     focusedIndicatorColor = Color(0xFF007bff),
     unfocusedIndicatorColor = Color.LightGray,
     focusedLabelColor = Color(0xFF007bff),
     unfocusedLabelColor = Color.Gray,
     cursorColor = Color(0xFF007bff)
    ),
    textStyle = TextStyle(color = Color.Black)
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
    color = Color(0xFFffffff),
            modifier = Modifier
             .clickable { navController.navigate("register") }
   )

   Spacer(modifier = Modifier.height(8.dp))

   // Texto de esqueceu senha
   Text(
    text = "Esqueceu a senha?",
    color = Color(0xFFffffff)
   )
  }
 }
}


