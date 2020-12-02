package com.carrati.vadebicicleta

import com.carrati.vadebicicleta.data.ConfigFirebase
import com.carrati.vadebicicleta.domain.usecases.*
import com.carrati.vadebicicleta.presentation.login.LoginViewModel
import com.carrati.vadebicicleta.presentation.main.HistoricoViewModel
import com.carrati.vadebicicleta.presentation.registrar.RegistrarViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {
    single { ConfigFirebase() }
}

val domainModule = module {
    factory { AlugarBicicletaUC( get() ) }
    factory { DeslogarUC( get() ) }
    factory { EfetuarLoginUC( get() ) }
    factory { GetHistoricoAluguelUC( get() ) }
    factory { GetUsuarioAtualUC( get() ) }
    factory { RegistrarUsuarioUC( get() ) }
}

val presentationModule = module {
    viewModel { HistoricoViewModel( get(), get(), get(), get() ) }
    viewModel { LoginViewModel( get() ) }
    viewModel { RegistrarViewModel( get() ) }
}

val appModules = listOf(dataModule, domainModule, presentationModule)