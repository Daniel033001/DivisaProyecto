package com.example.divisaproyecto.MonedaDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope

@Database(
    entities = [MonedaObjeto::class],
    version = 1
)
abstract class DB: RoomDatabase(){

    abstract fun daoMoneda(): DAOMoneda
    companion object {
        private var INSTANCE: DB? = null
        fun getDatabase(context: Context, scope: CoroutineScope): DB {

            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE =
                        Room.databaseBuilder(context,DB::class.java, "moneda")
                            .allowMainThreadQueries()
                            .build()
                }
            }
            return INSTANCE!!
        }
    }
}