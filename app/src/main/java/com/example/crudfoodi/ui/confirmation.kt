package com.example.crudfoodi

import androidx.compose.foundation.Image
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

import androidx.compose.ui.graphics.Color

import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.navigation.NavController

import androidx.compose.material3.ButtonDefaults


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.material3.Button
import androidx.compose.ui.graphics.Brush


@Composable

fun ConfirmacaoScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colorStops = arrayOf(
                        0.0f to Color.White,
                        0.7f to Color.White,
                        1.0f to Color(0xFF60adff) // Cor de fundo do gradiente
                    )
                )
            )
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Substitua por sua logo real se necessário
            Image(
                painter = painterResource(id = R.drawable.logobgless), // sua logo aqui
                contentDescription = "Logo FoodI",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Restaurante notificado,\nem breve seu pedido será entregue",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF007bff),
                lineHeight = 28.sp,
                modifier = Modifier.padding(horizontal = 8.dp),
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { navController.navigate("home_cliente") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007bff)),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Entendido", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}