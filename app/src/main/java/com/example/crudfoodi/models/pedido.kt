package com.example.crudfoodi.models

data class Pedido(
    val id: Int,
    val nome: String,
    val idRestaurante: Int,
    val idProduto: Int,
    val idCliente: Int,
    val valor: Double
)
