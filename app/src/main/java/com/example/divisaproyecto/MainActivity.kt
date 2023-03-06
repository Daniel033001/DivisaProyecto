package com.example.divisaproyecto

import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.divisaproyecto.Modelo.Moneda
import com.example.divisaproyecto.MonedaDB.DB
import com.example.divisaproyecto.MonedaDB.MonedaObjeto
import com.example.divisaproyecto.WorkManager.MW
import com.example.divisaproyecto.network.MonedaApi
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

var datos: Map<String, Double>? =null


class MainActivity : AppCompatActivity() {
    private lateinit var myJsonTxt : TextView
    private lateinit var button: Button
    private lateinit var uri : Uri
    lateinit var myJsonTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myJsonTxt = findViewById(R.id.jsonText)
        uri= Uri.parse("content://com.example.divisaproyecto.ContentProvider.MyCP/Moneda")

        // EJECUTA EL WORKMANAGER PERIODICAMENTE
        val workManager = WorkManager.getInstance(applicationContext)

        val constraints = Constraints.Builder()
            .setRequiresCharging(false)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<MW>(
            5, // tiempo en minutos
            TimeUnit.HOURS
        )
            .setConstraints(constraints)
            .build()

        workManager.enqueue(workRequest)

        val room = Room.databaseBuilder(this, DB::class.java,"moneda").build()
        myJsonTextView = findViewById(R.id.jsonText)
        getMoneda()
        datos?.forEach { s, d ->
        lifecycleScope.launch{
            room.daoMoneda().insert(
                MonedaObjeto("USD",
                    s,
                    d
                )
            )
            var moneda =room.daoMoneda().obtenerUno("AMD")
            println(moneda.toString())
        }
    };

    }

    private fun getMoneda(){
        lateinit var monedita: MonedaObjeto
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://v6.exchangerate-api.com/")
            .build()
        var  api: MonedaApi = retrofit.create(MonedaApi::class.java)

        var call: Call<Moneda> = api.moneda
        call.enqueue(object :Callback<Moneda>{
            override fun onResponse(call: Call<Moneda>, response: Response<Moneda>) {
                if(!response.isSuccessful){
                    myJsonTextView.text = "Codigo: "+response.code()
                    return
                }
                var post =response.body()
                var content: String ="" ;
                content+=" "+post?.result+"\n";
                content+=" "+post?.documentation+"\n";
                content+=" "+post?.terms_of_use+"\n";
                content+=" "+post?.time_last_update_unix+"\n";
                content+=" "+post?.time_last_update_utc+"\n";
                content+=" "+post?.time_next_update_unix+"\n";
                content+=" "+post?.time_next_update_utc+"\n";
                content+=" "+post?.base_code+"\n";
                post?.conversion_rates!!.forEach { s, d ->  content+=s+" "+d+"\n"
                    datos = datos?.plus(s to d)
                };
                myJsonTextView.append(content)

            }

            override fun onFailure(call: Call<Moneda>, t: Throwable) {
                myJsonTextView.text = t.message
            }

        })

    }
}