package com.example.crudfoodi.db
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.crudfoodi.models.Produto
import com.example.crudfoodi.models.Restaurante
import com.example.crudfoodi.models.Pedido
class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "foodI.db"
        const val DATABASE_VERSION = 2
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
                imagem TEXT,
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

        // Inserindo dados iniciais
        db.execSQL(
            """
        INSERT INTO cliente (nome, sobrenome, celular, email, senha) VALUES 
        ('Henrique', 'Salazar da Silva', '11999999999', 'a', 'a')
    """
        )

        db.execSQL(
            """
        INSERT INTO restaurante (nome, celular, endereco, imagem, email, senha, cnpj) VALUES 
        ('FoodI Restaurante Teste', '11988887777', 'Av. Central, 123', 'logobgless', 'b', 'b', '12345678000199')
    """
        )

        db.execSQL(
            """
        INSERT INTO produto (id_restaurante, nome, descricao, imagem, valor) VALUES 
        (1, 'Hambúrguer', 'Hambúrguer com carne grelhada no fogo', 'burguer', 25.90)
    """
        )
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS pedido")
        db.execSQL("DROP TABLE IF EXISTS produto")
        db.execSQL("DROP TABLE IF EXISTS restaurante")
        db.execSQL("DROP TABLE IF EXISTS cliente")
        onCreate(db)
    }

    fun inserirCliente(
        nome: String,
        sobrenome: String,
        celular: String,
        email: String,
        senha: String
    ): Boolean {
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

    fun insertRestaurante(
        nome: String,
        celular: String,
        endereco: String,
        imagem: String,
        email: String,
        senha: String,
        cnpj: String
    ): Boolean {
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

    fun adicionarProduto(produto: Produto): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("id_restaurante", produto.idRestaurante)
            put("nome", produto.nome)
            put("descricao", produto.descricao)
            put("imagem", produto.imagem)
            put("valor", produto.valor.toDouble())
        }

        val result = db.insert("produto", null, values)
        db.close()
        return result != -1L
    }


    fun verificarTiposDeConta(email: String): Pair<Boolean, Boolean> {
        val db = this.readableDatabase

        val cursorCliente = db.rawQuery("SELECT * FROM cliente WHERE email = ?", arrayOf(email))
        val clienteExiste = cursorCliente.moveToFirst()
        cursorCliente.close()

        val cursorRestaurante =
            db.rawQuery("SELECT * FROM restaurante WHERE email = ?", arrayOf(email))
        val restauranteExiste = cursorRestaurante.moveToFirst()
        cursorRestaurante.close()

        db.close()
        return Pair(clienteExiste, restauranteExiste)
    }

    fun buscarIdClientePorEmail(email: String): Int {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT id FROM cliente WHERE email = ?", arrayOf(email))
        var id = -1
        if (cursor.moveToFirst()) {
            id = cursor.getInt(0)
        }
        cursor.close()
        return id
    }

    fun buscarIdRestaurantePorEmail(email: String): Int {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT id FROM restaurante WHERE email = ?", arrayOf(email))
        var id = -1
        if (cursor.moveToFirst()) {
            id = cursor.getInt(0)
        }
        cursor.close()
        return id
    }

    fun buscarRestaurantePorId(id: Int): Restaurante? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM restaurante WHERE id = ?", arrayOf(id.toString()))
        var restaurante: Restaurante? = null

        if (cursor.moveToFirst()) {
            val nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"))
            val celular = cursor.getString(cursor.getColumnIndexOrThrow("celular"))
            val endereco = cursor.getString(cursor.getColumnIndexOrThrow("endereco"))
            val imagem = cursor.getString(cursor.getColumnIndexOrThrow("imagem"))
            val email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
            val senha = cursor.getString(cursor.getColumnIndexOrThrow("senha"))
            val cnpj = cursor.getString(cursor.getColumnIndexOrThrow("cnpj"))

            restaurante = Restaurante(id, nome, celular, endereco, imagem, email, senha, cnpj)
        }

        cursor.close()
        return restaurante
    }


    fun listarRestaurantes(): List<Restaurante> {
        val lista = mutableListOf<Restaurante>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM restaurante", null)

        Log.d("DBHelper", "Restaurantes encontrados: ${cursor.count}")

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

    fun buscarProdutosPorRestaurante(idRestaurante: Int): List<Produto> {
        val produtos = mutableListOf<Produto>()
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM produto WHERE id_restaurante = ?",
            arrayOf(idRestaurante.toString())
        )

        if (cursor.moveToFirst()) {
            do {
                val produto = Produto(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    idRestaurante = cursor.getInt(cursor.getColumnIndexOrThrow("id_restaurante")),
                    nome = cursor.getString(cursor.getColumnIndexOrThrow("nome")),
                    descricao = cursor.getString(cursor.getColumnIndexOrThrow("descricao")),
                    valor = cursor.getDouble(cursor.getColumnIndexOrThrow("valor")),
                    imagem = cursor.getString(cursor.getColumnIndexOrThrow("imagem")) // <-- Aqui
                )
                produtos.add(produto)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return produtos
    }
    fun criarPedidoComProdutos(
        nomeCliente: String,
        idCliente: Int,
        idRestaurante: Int,
        produtos: List<Produto>
    ): Long {
        val db = writableDatabase

        // Inicia uma transação para garantir que todos os dados sejam inseridos corretamente
        db.beginTransaction()

        try {
            // Cria o pedido na tabela de pedidos
            val contentValues = ContentValues().apply {
                put("nome", nomeCliente)
                put("id_cliente", idCliente)
                put("id_restaurante", idRestaurante)
                put("valor", produtos.sumOf { it.valor })
            }

            // Insere o pedido e obtém o ID do pedido inserido
            val idPedido = db.insert("pedido", null, contentValues)

            // Verifica se o pedido foi inserido com sucesso
            if (idPedido != -1L) {
                // Agora, inserimos os produtos associados ao pedido
                for (produto in produtos) {
                    val contentValuesProduto = ContentValues().apply {
                        put("id_pedido", idPedido)  // Associa o produto ao pedido
                        put("id_produto", produto.id)
                        put("valor", produto.valor)
                    }
                    // Insere o produto na tabela de pedido_produto (tabela intermediária)
                    db.insert("pedido_produto", null, contentValuesProduto)
                }

                // Marca a transação como bem-sucedida
                db.setTransactionSuccessful()
            }

            return idPedido
        } catch (e: Exception) {
            e.printStackTrace()
            return -1L  // Se algo deu errado, retorna um ID inválido
        } finally {
            // Fim da transação
            db.endTransaction()
        }
    }
    fun buscarProdutosDoPedido(idPedido: Long): List<Produto> {
        val db = readableDatabase
        val produtos = mutableListOf<Produto>()
        val cursor = db.rawQuery(
            "SELECT * FROM produtos WHERE id IN (SELECT id_produto FROM pedido_produto WHERE id_pedido = ?)",
            arrayOf(idPedido.toString())
        )

        if (cursor.moveToFirst()) {
            do {
                // Verifica se a coluna existe antes de acessar
                val idColumnIndex = cursor.getColumnIndex("id")
                val nomeColumnIndex = cursor.getColumnIndex("nome")
                val valorColumnIndex = cursor.getColumnIndex("valor")
                val descricaoColumnIndex = cursor.getColumnIndex("descricao")
                val idRestauranteColumnIndex = cursor.getColumnIndex("id_restaurante")
                val imagemColumnIndex = cursor.getColumnIndex("imagem")

                // Verifica se todas as colunas existem
                if (idColumnIndex >= 0 && nomeColumnIndex >= 0 && valorColumnIndex >= 0) {
                    val produto = Produto(
                        id = cursor.getInt(idColumnIndex),
                        nome = cursor.getString(nomeColumnIndex),
                        valor = cursor.getDouble(valorColumnIndex), // Converter para Float
                        descricao = if (descricaoColumnIndex >= 0) cursor.getString(descricaoColumnIndex) else "",
                        idRestaurante = if (idRestauranteColumnIndex >= 0) cursor.getInt(idRestauranteColumnIndex) else 0,
                        imagem = if (imagemColumnIndex >= 0) cursor.getString(imagemColumnIndex) else ""
                    )
                    produtos.add(produto)
                }
            } while (cursor.moveToNext())
        }
        cursor.close()
        return produtos
    }
    fun limparCarrinho(idCliente: Int) {
        // Supondo que o banco de dados esteja acessível sem contexto
        val db = writableDatabase // Aqui, o banco de dados é acessado diretamente
        db.execSQL("DELETE FROM carrinho WHERE id_cliente = ?", arrayOf(idCliente.toString()))
        db.close()
    }

}

