package com.example.crudfoodi.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "foodI.db"
        const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Tabela Cliente
        val createClienteTable = """
            CREATE TABLE cliente (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT,
                sobrenome TEXT,
                celular TEXT,
                email TEXT,
                senha TEXT
            )
        """.trimIndent()

        // Tabela Restaurante
        val createRestauranteTable = """
            CREATE TABLE restaurante (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT,
                celular TEXT,
                endereco TEXT,
                imagem TEXT
            )
        """.trimIndent()

        // Tabela Produto
        val createProdutoTable = """
            CREATE TABLE produto (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                id_restaurante INTEGER,
                nome TEXT,
                descricao TEXT,
                valor REAL,
                FOREIGN KEY(id_restaurante) REFERENCES restaurante(id)
            )
        """.trimIndent()

        // Tabela Pedido
        val createPedidoTable = """
            CREATE TABLE pedido (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT,
                id_restaurante INTEGER,
                id_produto INTEGER,
                id_cliente INTEGER,
                valor REAL,
                FOREIGN KEY(id_restaurante) REFERENCES restaurante(id),
                FOREIGN KEY(id_produto) REFERENCES produto(id),
                FOREIGN KEY(id_cliente) REFERENCES cliente(id)
            )
        """.trimIndent()

        db.execSQL(createClienteTable)
        db.execSQL(createRestauranteTable)
        db.execSQL(createProdutoTable)
        db.execSQL(createPedidoTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS pedido")
        db.execSQL("DROP TABLE IF EXISTS produto")
        db.execSQL("DROP TABLE IF EXISTS restaurante")
        db.execSQL("DROP TABLE IF EXISTS cliente")
        onCreate(db)
    }
}
