package com.carrati.vadebicicleta.domain.usecases

import com.carrati.vadebicicleta.data.ConfigFirebase
import com.carrati.vadebicicleta.domain.models.Usuario
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

class EfetuarLoginUC(private val firebase: ConfigFirebase) {

    fun execute(user: Usuario): Task<AuthResult> {
        val auth = firebase.getFirebaseAuth()

        return auth.signInWithEmailAndPassword(
            user.email, user.pass
        )
    }
}