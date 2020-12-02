package com.carrati.vadebicicleta.domain.usecases

import com.carrati.vadebicicleta.data.ConfigFirebase
import com.carrati.vadebicicleta.domain.models.Usuario
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

class RegistrarUsuarioUC(private val firebase: ConfigFirebase) {

    fun execute(user: Usuario): Task<AuthResult> {
        val auth = firebase.getFirebaseAuth()

        return auth.createUserWithEmailAndPassword(
            user.email, user.pass
        )
    }
}