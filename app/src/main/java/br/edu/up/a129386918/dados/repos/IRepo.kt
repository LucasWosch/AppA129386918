package br.edu.up.a129386918.dados.repos

import br.edu.up.a129386918.dados.models.User
import kotlinx.coroutines.flow.Flow

interface IRepo {

    fun listar(): Flow<List<User>>
    suspend fun buscarPorId(idx: Int): User?
    suspend fun gravar(user: User)
    suspend fun excluir(user: User)
}