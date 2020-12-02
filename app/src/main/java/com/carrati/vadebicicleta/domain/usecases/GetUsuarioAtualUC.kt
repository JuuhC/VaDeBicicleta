package com.carrati.vadebicicleta.domain.usecases

import com.carrati.vadebicicleta.data.ConfigFirebase
import com.carrati.vadebicicleta.domain.models.Usuario

class GetUsuarioAtualUC(private val firebase: ConfigFirebase) {

    fun execute(): Usuario?  {
        val auth = firebase.getFirebaseAuth()

         return if(auth.currentUser != null) {
            Usuario().apply {
                id = auth.currentUser?.uid ?: ""
                email = auth.currentUser?.email ?: ""
            }
        } else null
    }
}