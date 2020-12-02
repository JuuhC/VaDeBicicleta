package com.carrati.vadebicicleta.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ConfigFirebase {
    companion object {
        private var auth: FirebaseAuth? = null
        private var crashlytics: FirebaseCrashlytics = FirebaseCrashlytics.getInstance()
        private var database: DatabaseReference? = null
    }

    fun getFirebaseAuth(): FirebaseAuth {
        if (auth == null) auth = FirebaseAuth.getInstance()
        return auth!!
    }

    fun getFirebaseDb(): DatabaseReference {
        if (database == null) database = FirebaseDatabase.getInstance().reference
        return database!!
    }

    fun sendThrowableToFirebase(t: Throwable) {
        crashlytics.recordException(t)
        crashlytics.sendUnsentReports()
    }
}