package com.example.crudfoodi.models
import android.net.Uri

data class Restaurante(
    val id: Int,
    val nome: String,
    val celular: String,
    val endereco: String,
    val imagem: String,
    val email: String,
    val senha: String,
    val cnpj: String
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