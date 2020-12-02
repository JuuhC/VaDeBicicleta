package com.carrati.vadebicicleta.presentation.registrar

import androidx.lifecycle.ViewModel
import com.carrati.vadebicicleta.domain.models.Usuario
import com.carrati.vadebicicleta.domain.usecases.GetUsuarioAtualUC
import com.carrati.vadebicicleta.domain.usecases.RegistrarUsuarioUC
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

class RegistrarViewModel(
    private val registrarUsuarioUC: RegistrarUsuarioUC): ViewModel() {

    fun registarUsuario(user: Usuario): Task<AuthResult> {
        return registrarUsuarioUC.execute(user)
    }
}