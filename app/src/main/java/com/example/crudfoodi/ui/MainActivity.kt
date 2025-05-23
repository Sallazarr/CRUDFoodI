package com.example.crudfoodi
import android.content.Context
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
import com.example.crudfoodi.db.DBHelper

import androidx.compose.ui.graphics.Brush

import androidx.compose.ui.platform.LocalFocusManager

import androidx.compose.ui.focus.FocusDirection
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.platform.LocalFocusManager

import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.focusRequester
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

import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher

import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button

import com.example.crudfoodi.permission.requestStoragePermission

import androidx.compose.material3.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight


import androidx.compose.ui.unit.sp


import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {

 // Registrar o ActivityResultLauncher para solicitar permissão
 private val requestPermissionLauncher =
  registerForActivityResult(RequestPermission()) { isGranted: Boolean ->
   if (isGranted) {
    Toast.makeText(this, "Permissão concedida", Toast.LENGTH_SHORT).show()
   } else {
    Toast.makeText(this, "Permissão negada", Toast.LENGTH_SHORT).show()
   }
  }

 override fun onCreate(savedInstanceState: Bundle?) {
  super.onCreate(savedInstanceState)
  setContent {
   CRUDFoodITheme {
    // Chama o composable para solicitar permissão
    Navigation(requestPermissionLauncher)
   }

  }
 }
}

fun salvarIdEmSharedPreferences(context: Context, id: Int, tipo: String) {
 val sharedPrefs = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
 val editor = sharedPrefs.edit()
 if (tipo == "cliente") {
  editor.putInt("id_cliente", id)
 } else if (tipo == "restaurante") {
  editor.putInt("id_restaurante", id)
 }
 editor.apply()
}

 @Composable

 fun LoginScreen(navController: NavHostController, requestPermissionLauncher: ActivityResultLauncher<String>) {
  requestStoragePermission(requestPermissionLauncher)
  val context = LocalContext.current
  val dbHelper = DBHelper(context)
  val focusRequesterEmail = remember { FocusRequester() }
  val focusRequesterSenha = remember { FocusRequester() } // Próximo campo
  val focusManager = LocalFocusManager.current

  var email by remember { mutableStateOf("") }
  var senha by remember { mutableStateOf("") }

  var mostrarDialogo by remember { mutableStateOf(false) }
  var tipoDialogo by remember { mutableStateOf(Pair(false, false)) }

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
     value = email,
     onValueChange = { email = it },
     label = { Text("Email") },
     singleLine = true,
     modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(10.dp))
      .focusRequester(focusRequesterEmail),
     keyboardOptions = KeyboardOptions(
      imeAction = ImeAction.Next
     ),
     keyboardActions = KeyboardActions(
      onNext = { focusRequesterSenha.requestFocus() }
     ),
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

    // Input de Senha
    TextField(
     visualTransformation = PasswordVisualTransformation(),
     value = senha,
     onValueChange = { senha = it },
     label = { Text("Senha") },
     singleLine = true,
     modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(10.dp))
      .focusRequester(focusRequesterSenha),
     keyboardOptions = KeyboardOptions(
      imeAction = ImeAction.Done
     ),
     keyboardActions = KeyboardActions(
      onDone = { focusManager.clearFocus() }
     ),
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


    Spacer(modifier = Modifier.height(24.dp))

    // Botão de Entrar
    Button(
     onClick = {
      val (clienteExiste, restauranteExiste) = dbHelper.verificarTiposDeConta(email)

      when {
       clienteExiste && restauranteExiste -> {
        tipoDialogo = Pair(true, true)
        mostrarDialogo = true
       }

       clienteExiste -> {
        val clienteId = dbHelper.buscarIdClientePorEmail(email)
        if (clienteId != -1) {
         salvarIdEmSharedPreferences(context, clienteId, "cliente")
         navController.navigate("home_cliente")
        } else {
         Toast.makeText(context, "Cliente não encontrado", Toast.LENGTH_SHORT).show()
        }
       }

       restauranteExiste -> {
        // Verificar se o restaurante existe antes de tentar buscar o ID
        val restauranteId = dbHelper.buscarIdRestaurantePorEmail(email)
        if (restauranteId != -1) { // Verifica se o ID é válido
         salvarIdEmSharedPreferences(context, restauranteId, "restaurante")
         navController.navigate("home_restaurante")
        } else {
         Toast.makeText(context, "Restaurante não encontrado", Toast.LENGTH_SHORT).show()
        }
       }

       else -> {
        Toast.makeText(context, "Email ou senha inválidos", Toast.LENGTH_SHORT).show()
       }
      }
     },

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

   // 🔽 Dialogo de escolha se for os dois
   if (mostrarDialogo && tipoDialogo.first && tipoDialogo.second) {
    AlertDialog(
     onDismissRequest = { mostrarDialogo = false },
     confirmButton = {
      TextButton(onClick = {
       mostrarDialogo = false
       navController.navigate("home_cliente")
      }) {
       Text("Entrar como Cliente")
      }
     },
     dismissButton = {
      TextButton(onClick = {
       mostrarDialogo = false
       navController.navigate("home_restaurante")
      }) {
       Text("Entrar como Restaurante")
      }
     },
     title = { Text("Tipo de Conta") },
     text = { Text("Email cadastrado como Cliente e Restaurante. Como deseja entrar?") }
    )
   }

  }
 }


