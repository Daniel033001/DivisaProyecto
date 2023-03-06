package com.example.divisaproyecto.repository

import android.app.Application
import com.example.divisaproyecto.MonedaDB.DB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MonedaApp  : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { DB.getDatabase(this, applicationScope) }
    val repositoryMoneda by lazy {  MonedaRepository (database.daoMoneda()) }

    override fun onCreate() {
        super.onCreate()

    }
}