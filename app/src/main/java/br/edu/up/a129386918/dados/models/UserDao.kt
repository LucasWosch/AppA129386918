package br.edu.up.a129386918.dados.models

import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

interface UserDao {
    //CRUD
    @Query("select * from tab_user")
    fun listar(): Flow<List<User>>

    @Query("select * from tab_user where id = :idx")
    suspend fun buscarPorId(idx: Int): User

    //@Update @Insert
    @Upsert
    suspend fun gravar(afazer: User)

    @Delete
    suspend fun excluir(afazer: User)
}