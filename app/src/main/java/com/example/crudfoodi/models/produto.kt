package com.example.crudfoodi.models

data class Produto(
    val id: Int,
    val idRestaurante: Int,
    val imagem: String,
    val nome: String,
    val descricao: String,
    val valor: String
)