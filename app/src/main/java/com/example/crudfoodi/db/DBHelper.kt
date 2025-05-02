package com.example.crudfoodi.db
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.crudfoodi.models.Produto
import com.example.crudfoodi.models.Restaurante
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
                imagem TEXT,
                email text,
                senha text,
                cnpj text
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
    fun inserirCliente(nome: String, sobrenome: String, celular: String, email: String, senha: String): Boolean {
        val db = this.writableDatabase
        val contentValues = android.content.ContentValues()
        contentValues.put("nome", nome)
        contentValues.put("sobrenome", sobrenome)
        contentValues.put("celular", celular)
        contentValues.put("email", email)
        contentValues.put("senha", senha)

        val resultado = db.insert("cliente", null, contentValues)
        db.close()
        return resultado != -1L

    }
    fun insertRestaurante(nome: String, celular: String, endereco: String, imagem: String, email: String, senha: String, cnpj: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("nome", nome)
            put("celular", celular)
            put("endereco", endereco)
            put("imagem", imagem)
            put("email", email)
            put("senha", senha)
            put("cnpj", cnpj)
        }
        val result = db.insert("restaurante", null, values)
        db.close()
        return result != -1L
    }

    fun verificarTiposDeConta(email: String): Pair<Boolean, Boolean> {
        val db = this.readableDatabase

        val cursorCliente = db.rawQuery("SELECT * FROM cliente WHERE email = ?", arrayOf(email))
        val clienteExiste = cursorCliente.moveToFirst()
        cursorCliente.close()

        val cursorRestaurante = db.rawQuery("SELECT * FROM restaurante WHERE email = ?", arrayOf(email))
        val restauranteExiste = cursorRestaurante.moveToFirst()
        cursorRestaurante.close()

        db.close()
        return Pair(clienteExiste, restauranteExiste)
    }

    fun listarRestaurantes(): List<Restaurante> {
        val lista = mutableListOf<Restaurante>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM restaurante", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"))
                val celular = cursor.getString(cursor.getColumnIndexOrThrow("celular"))
                val endereco = cursor.getString(cursor.getColumnIndexOrThrow("endereco"))

                val imagem = cursor.getString(cursor.getColumnIndexOrThrow("imagem"))

                // e depois adiciona normalmente na lista:

                val email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
                val senha = cursor.getString(cursor.getColumnIndexOrThrow("senha"))
                val cnpj = cursor.getString(cursor.getColumnIndexOrThrow("cnpj"))

                lista.add(Restaurante(id, nome, celular, endereco, imagem, email, senha, cnpj))
            } while (cursor.moveToNext())
        }

        cursor.close()
        return lista
    }
}

fun getProdutosByRestauranteId(restauranteId: Int): List<Produto> {
    val produtos = mutableListOf<Produto>()
    val db = readableDatabase
    val cursor = db.rawQuery(
        "SELECT * FROM produto WHERE id_restaurante = ?",
        arrayOf(restauranteId.toString())
    )

    if (cursor.moveToFirst()) {
        do {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"))
            val descricao = cursor.getString(cursor.getColumnIndexOrThrow("descricao"))
            val valor = cursor.getDouble(cursor.getColumnIndexOrThrow("valor"))
            produtos.add(Produto(id, restauranteId, nome, descricao, valor))
        } while (cursor.moveToNext())
    }

    cursor.close()
    return produtos
}

fun inserirProduto(idRestaurante: Int, nome: String, descricao: String, valor: Double): Boolean {
    val db = writableDatabase
    val contentValues = ContentValues().apply {
        put("id_restaurante", idRestaurante)
        put("nome", nome)
        put("descricao", descricao)
        put("valor", valor)
    }
    val result = db.insert("produto", null, contentValues)
    db.close()
    return result != -1L
}

fun removerProduto(idProduto: Int): Boolean {
    val db = writableDatabase
    val result = db.delete("produto", "id = ?", arrayOf(idProduto.toString()))
    db.close()
    return result > 0
}
