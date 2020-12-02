package com.carrati.vadebicicleta.domain.usecases

import com.carrati.vadebicicleta.data.ConfigFirebase

class DeslogarUC(private val firebase: ConfigFirebase) {
    fun execute(){
        firebase.getFirebaseAuth().signOut()
    }
}