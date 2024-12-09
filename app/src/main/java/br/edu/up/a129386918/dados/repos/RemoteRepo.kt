package br.edu.up.a129386918.dados.repos

import br.edu.up.a129386918.dados.models.User
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class RemoteRepo: IRepo {

    private val firestore = FirebaseFirestore.getInstance()
    private val afazerCollection = firestore.collection("afazeres")



    override fun listar(): Flow<List<User>> = callbackFlow {
        val listener = afazerCollection.addSnapshotListener {
                dados, erros ->
            if (erros != null) {
                close(erros)
                return@addSnapshotListener
            }
            if (dados != null) {
                val users = dados.documents.mapNotNull {
                    it.toObject(User::class.java)
                }
                trySend(users).isSuccess
            }
        }
        awaitClose { listener.remove() }
    }


    override suspend fun buscarPorId(idx: Int): User? {
        val dados = afazerCollection.document(idx.toString()).get().await()
        val afazer = dados.toObject(User::class.java)
        return afazer
    }

    suspend fun getId(): Int {
        val dados = afazerCollection.get().await()
        val maxId = dados.documents.mapNotNull {
            it.getLong("id")?.toInt()
        }.maxOrNull() ?: 0
        return maxId + 1
    }

    override suspend fun gravar(afazer: User) {
        val document: DocumentReference
        if(afazer.id == null) {
            afazer.id = getId()
            document = afazerCollection.document(afazer.id.toString())
        } else {
            document = afazerCollection.document(afazer.id.toString())
        }
        document.set(afazer).await()
    }

    override suspend fun excluir(afazer: User) {
        afazerCollection.document(afazer.id.toString()).delete().await()
    }





}