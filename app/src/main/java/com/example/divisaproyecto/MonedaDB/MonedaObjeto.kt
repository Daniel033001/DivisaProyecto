package com.example.divisaproyecto.MonedaDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class MonedaObjeto {
    @PrimaryKey
    var base_code: String
    var tipo_moneda: String? = null
    var valor: Double? = null
    constructor(base_code: String, tipo_moneda: String?, valor: Double?) {
        this.base_code = base_code
        this.tipo_moneda = tipo_moneda
        this.valor = valor
    }

}