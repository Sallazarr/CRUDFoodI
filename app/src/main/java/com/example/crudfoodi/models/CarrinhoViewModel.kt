package com.example.crudfoodi.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.crudfoodi.models.Produto

class CarrinhoViewModel : ViewModel() {

    val carrinho = mutableStateListOf<Produto>()

    fun adicionarProduto(produto: Produto) {
        carrinho.add(produto)
    }

    fun removerProduto(produto: Produto) {
        carrinho.remove(produto)
    }

    fun limparCarrinho() {
        carrinho.clear()
    }

    fun valorTotal(): Double {
        return carrinho.sumOf { it.valor }
    }
}
