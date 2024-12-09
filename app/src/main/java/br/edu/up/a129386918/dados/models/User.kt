package br.edu.up.a129386918.dados.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tab_user")
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val nome: String,
    val email: String
) {
    constructor(): this(null, "", "")
}