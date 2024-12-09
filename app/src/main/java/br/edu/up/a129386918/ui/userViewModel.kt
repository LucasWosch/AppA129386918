package br.edu.up.a129386918.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.up.a129386918.dados.models.User
import br.edu.up.a129386918.dados.repos.IRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class userViewModel(
    private val repository: IRepo
): ViewModel() {

        private val _users = MutableStateFlow<List<User>>(emptyList())
        val users: StateFlow<List<User>> get() = _users

        init {
            viewModelScope.launch {
                repository.listar().collectLatest { lista ->
                    _users.value = lista
                }
            }
        }

        suspend fun buscarUserPorId(userId: Int): User?{
            return repository.buscarPorId(userId)
        }

        fun gravarUser(user: User){
            viewModelScope.launch {
                repository.gravar(user)
            }
        }

        fun excluirUser(user: User){
            viewModelScope.launch {
                repository.excluir(user)
            }
        }
    }