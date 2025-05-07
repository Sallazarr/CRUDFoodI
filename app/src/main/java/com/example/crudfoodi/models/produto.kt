package com.example.crudfoodi.models

import android.net.Uri

data class Produto(
    val id: Int,
    val idRestaurante: Int,
    val imagem: String,
    val nome: String,
    val descricao: String,
    val valor: Double
) {
    fun getImagemUri(): Uri? {
        return imagem?.let { Uri.parse(it) }
    }
}