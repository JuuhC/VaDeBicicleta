package com.carrati.vadebicicleta.domain.usecases

import com.carrati.vadebicicleta.data.ConfigFirebase
import com.carrati.vadebicicleta.domain.models.Aluguel

class AlugarBicicletaUC(private val firebase: ConfigFirebase) {

    fun execute(aluguel: Aluguel) {
        try {
            firebase.getFirebaseDb().child("historico_aluguel_conf")
                .child(firebase.getFirebaseAuth().uid!!)
                .push()
                .setValue( aluguel )
        } catch (e: Exception) {
            throw Throwable("Erro ao efetuar aluguel.", e)
        }
    }
}