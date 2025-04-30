package com.example.crudfoodi.moveImage

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

fun salvarImagemNoApp(context: Context, uri: Uri): String? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        val nomeArquivo = "restaurante_${System.currentTimeMillis()}.jpg"
        val arquivo = File(context.filesDir, nomeArquivo)

        val outputStream = FileOutputStream(arquivo)
        inputStream?.copyTo(outputStream)

        inputStream?.close()
        outputStream.close()

        arquivo.absolutePath  // caminho que você salva no banco
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}