package com.example.crudfoodi.moveImage

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

fun salvarImagemRestauranteNoApp(context: Context, uri: Uri): String? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        val nomeArquivo = "restaurante_${System.currentTimeMillis()}.jpg"
        val arquivo = File(context.filesDir, nomeArquivo)

        val outputStream = FileOutputStream(arquivo)
        inputStream?.copyTo(outputStream)

        inputStream?.close()
        outputStream.close()

        arquivo.absolutePath  // Retorna o caminho do arquivo salvo
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun salvarImagemProdutoNoApp(context: Context, uri: Uri): String? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        val nomeArquivo = "produto_${System.currentTimeMillis()}.jpg"
        val arquivo = File(context.filesDir, nomeArquivo)

        val outputStream = FileOutputStream(arquivo)
        inputStream?.copyTo(outputStream)

        inputStream?.close()
        outputStream.close()

        arquivo.absolutePath  // Retorna o caminho do arquivo salvo
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
