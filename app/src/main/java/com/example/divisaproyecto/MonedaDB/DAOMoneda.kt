package com.example.divisaproyecto.MonedaDB

import android.database.Cursor
import androidx.room.*

@Dao
interface DAOMoneda {
    @Query("Select * from MonedaObjeto")
    fun getAllCursor(): Cursor

    @Query("SELECT * FROM MonedaObjeto WHERE tipo_moneda= :tm")
    suspend fun obtenerUno(tm: String): MonedaObjeto

    @Insert()
    fun insert(moneda: MonedaObjeto)


    @Query("select * from MonedaObjeto")
    fun getAll(): kotlinx.coroutines.flow.Flow<List<MonedaObjeto>>

    @Query ("delete from MonedaObjeto")
    fun deleteAll()

    @Update
    fun update(moneda : MonedaObjeto)
}