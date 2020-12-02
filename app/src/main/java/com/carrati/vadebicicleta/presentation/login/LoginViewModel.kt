package com.carrati.vadebicicleta.presentation.login

import androidx.lifecycle.ViewModel
import com.carrati.vadebicicleta.domain.models.Usuario
import com.carrati.vadebicicleta.domain.usecases.EfetuarLoginUC
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

class LoginViewModel(private val efetuarLoginUC: EfetuarLoginUC): ViewModel() {

    fun efetuarLogin(user: Usuario): Task<AuthResult> {
        return efetuarLoginUC.execute(user)
    }
}