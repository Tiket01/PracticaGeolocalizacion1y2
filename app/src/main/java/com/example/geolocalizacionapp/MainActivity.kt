package com.example.geolocalizacionapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var editTextLatitude: EditText
    private lateinit var editTextLongitude: EditText
    private lateinit var editTextNombre: EditText
    private lateinit var buttonSearch: Button
    private lateinit var buttonClear: Button
    private lateinit var buttonGuardar: Button
    private lateinit var buttonConsultarUbicaciones: Button  // Declaración del botón

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextLatitude = findViewById(R.id.editTextLatitude)
        editTextLongitude = findViewById(R.id.editTextLongitude)
        editTextNombre = findViewById(R.id.editTextNombre)
        buttonSearch = findViewById(R.id.buttonSearch)
        buttonClear = findViewById(R.id.buttonClear)
        buttonGuardar = findViewById(R.id.buttonGuardar)
        buttonConsultarUbicaciones = findViewById(R.id.buttonConsultarUbicaciones)  // Inicialización del botón

        // Configurar el fragmento de mapa
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Configura el botón para buscar la ubicación
        buttonSearch.setOnClickListener {
            searchLocation()
        }

        // Configura el botón para limpiar los datos
        buttonClear.setOnClickListener {
            clearData()
        }

        // Configura el botón para guardar la ubicación
        buttonGuardar.setOnClickListener {
            guardarUbicacion()
        }

        // Configura el botón para consultar ubicaciones guardadas
        buttonConsultarUbicaciones.setOnClickListener {
            val intent = Intent(this, UbicacionesActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }

    private fun searchLocation() {
        val latitude = editTextLatitude.text.toString().toDoubleOrNull()
        val longitude = editTextLongitude.text.toString().toDoubleOrNull()

        if (latitude != null && longitude != null) {
            val location = LatLng(latitude, longitude)
            mMap.clear()
            mMap.addMarker(MarkerOptions().position(location).title("Ubicación seleccionada"))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
        } else {
            editTextLatitude.error = "Ingrese una latitud válida"
            editTextLongitude.error = "Ingrese una longitud válida"
        }
    }

    private fun clearData() {
        editTextLatitude.text.clear()
        editTextLongitude.text.clear()
        editTextNombre.text.clear()
        mMap.clear()
    }

    private fun guardarUbicacion() {
        val latitud = editTextLatitude.text.toString().toDoubleOrNull()
        val longitud = editTextLongitude.text.toString().toDoubleOrNull()
        val nombre = editTextNombre.text.toString()

        if (latitud == null || longitud == null || nombre.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val ubicacion = Ubicacion(latitud, longitud, nombre)

        val api = RetrofitClient.instance.create(ApiService::class.java)
        val call = api.agregarUbicacion(ubicacion)

        call.enqueue(object : Callback<Ubicacion> {
            override fun onResponse(call: Call<Ubicacion>, response: Response<Ubicacion>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@MainActivity, "Ubicación guardada con éxito", Toast.LENGTH_SHORT).show()
                    clearData()
                } else {
                    Toast.makeText(this@MainActivity, "Error al guardar la ubicación", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Ubicacion>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
