package com.carrati.vadebicicleta.presentation.main

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.carrati.vadebicicleta.domain.models.Aluguel
import com.carrati.vadebicicleta.domain.models.Usuario
import com.carrati.vadebicicleta.domain.usecases.*
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.database.DatabaseReference

class HistoricoViewModel(
    private val getUsuarioAtualUC: GetUsuarioAtualUC,
    private val alugarBicicletaUC: AlugarBicicletaUC,
    private val getHistoricoAluguelUC: GetHistoricoAluguelUC,
    private val deslogarUC: DeslogarUC
): ViewModel() {

    fun usuarioLogado(): Usuario? {
        return getUsuarioAtualUC.execute()
    }

    fun alugarBicicleta(aluguel: Aluguel) {
        alugarBicicletaUC.execute(aluguel)
    }

    fun getHistorico(): DatabaseReference {
        return getHistoricoAluguelUC.execute()
    }

    fun deslogar() {
        return deslogarUC.execute()
    }
}