package com.carrati.vadebicicleta.domain.usecases

import com.carrati.vadebicicleta.data.ConfigFirebase
import com.carrati.vadebicicleta.domain.models.Aluguel
import com.google.firebase.database.DatabaseReference

class GetHistoricoAluguelUC(private val firebase: ConfigFirebase) {
    fun execute(): DatabaseReference {
        try {
            return firebase.getFirebaseDb()
                .child("historico_aluguel_conf")
                .child(firebase.getFirebaseAuth().uid!!)
        } catch (e: Exception) {
            throw Throwable("Erro ao carregar histórico.", e)
        }
    }
}