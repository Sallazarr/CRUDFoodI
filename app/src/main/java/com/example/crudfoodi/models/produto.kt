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
    // Função para garantir a URI válida
    fun getImagemUri(): Uri? {
        return try {
            Uri.parse(imagem).normalizeScheme()
        } catch (e: Exception) {
            null
        }
    }
}