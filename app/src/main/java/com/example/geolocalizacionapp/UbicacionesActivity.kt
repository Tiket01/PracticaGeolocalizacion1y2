package com.example.geolocalizacionapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log

class UbicacionesActivity : AppCompatActivity() {

    private lateinit var recyclerViewUbicaciones: RecyclerView
    private lateinit var ubicacionesAdapter: UbicacionesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ubicaciones)

        recyclerViewUbicaciones = findViewById(R.id.recyclerViewUbicaciones)
        recyclerViewUbicaciones.layoutManager = LinearLayoutManager(this)
        ubicacionesAdapter = UbicacionesAdapter(emptyList())
        recyclerViewUbicaciones.adapter = ubicacionesAdapter

        obtenerUbicaciones()
    }

    private fun obtenerUbicaciones() {
        val api = RetrofitClient.instance.create(ApiService::class.java)
        val call = api.obtenerUbicaciones()

        call.enqueue(object : Callback<List<Ubicacion>> {
            override fun onResponse(
                call: Call<List<Ubicacion>>,
                response: Response<List<Ubicacion>>
            ) {
                if (response.isSuccessful) {
                    val ubicaciones = response.body() ?: emptyList()
                    ubicacionesAdapter.setUbicaciones(ubicaciones)
                    Log.d("UbicacionesActivity", "Ubicaciones obtenidas: $ubicaciones")
                } else {
                    Toast.makeText(
                        this@UbicacionesActivity,
                        "Error al obtener ubicaciones: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e(
                        "UbicacionesActivity",
                        "Error al obtener ubicaciones: ${response.errorBody()?.string()}"
                    )
                }
            }

            override fun onFailure(call: Call<List<Ubicacion>>, t: Throwable) {
                Toast.makeText(
                    this@UbicacionesActivity,
                    "Error de conexión: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e("UbicacionesActivity", "Error de conexión: ${t.message}", t)
            }
        })
    }
}
