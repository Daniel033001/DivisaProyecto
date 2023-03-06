package com.example.divisaproyecto.repository

import com.example.divisaproyecto.MonedaDB.DAOMoneda
import com.example.divisaproyecto.MonedaDB.MonedaObjeto
import kotlinx.coroutines.flow.Flow

class MonedaRepository(private val monedaDao: DAOMoneda) {
    val allMonedas: Flow<List<MonedaObjeto>>
    get() = monedaDao.getAll()
}
