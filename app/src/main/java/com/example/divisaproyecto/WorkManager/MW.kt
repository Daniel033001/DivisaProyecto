package com.example.divisaproyecto.WorkManager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.divisaproyecto.Modelo.Moneda
import com.example.divisaproyecto.MonedaDB.DB
import com.example.divisaproyecto.MonedaDB.MonedaObjeto
import com.example.divisaproyecto.network.MonedaApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MW(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams){
    val applicationScope = CoroutineScope(SupervisorJob())

    override fun doWork(): Result {
        DB.getDatabase(applicationContext, applicationScope).daoMoneda().deleteAll()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://v6.exchangerate-api.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var api : MonedaApi = retrofit.create(MonedaApi::class.java)

        var call: Call<Moneda> = api.moneda

        call.enqueue(object : Callback<Moneda> {
            override fun onResponse(call: Call<Moneda>, response: Response<Moneda>) {
                if(!response.isSuccessful) {
                    return
                }

                var post = response.body()

                val applicationScope = CoroutineScope(SupervisorJob())
                var moneda = MonedaObjeto(
                    base_code = "",
                    tipo_moneda = "",
                    valor = 0.0
                )
                for(codes in post!!.conversion_rates){
                    moneda.base_code = codes.key
                    moneda.tipo_moneda = codes.key
                    moneda.valor = codes.value
                    DB.getDatabase(applicationContext, applicationScope).daoMoneda().insert(moneda)
                }
            }

            override fun onFailure(call: Call<Moneda>, t: Throwable) {
            }
        })
        Log.d("this_app", "Si que, jala")

        return Result.success()
    }
}