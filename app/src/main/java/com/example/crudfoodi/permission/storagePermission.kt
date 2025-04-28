package com.example.crudfoodi.permission

import android.Manifest
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager

// Função para verificar e solicitar permissão
@Composable
fun requestStoragePermission(requestPermissionLauncher: ActivityResultLauncher<String>) {
    val context = LocalContext.current
    val permission = Manifest.permission.READ_EXTERNAL_STORAGE

    // Verifica se a permissão já foi concedida
    when {
        ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED -> {
            // Permissão já concedida
            Toast.makeText(context, "Permissão já concedida", Toast.LENGTH_SHORT).show()
        }
        else -> {
            // Solicita a permissão
            requestPermissionLauncher.launch(permission)
        }
    }
}