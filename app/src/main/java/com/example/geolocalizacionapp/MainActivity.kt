// MainActivity.kt
package com.example.geolocalizacionapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var editTextLatitude: EditText
    private lateinit var editTextLongitude: EditText
    private lateinit var buttonSearch: Button
    private lateinit var buttonClear: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextLatitude = findViewById(R.id.editTextLatitude)
        editTextLongitude = findViewById(R.id.editTextLongitude)
        buttonSearch = findViewById(R.id.buttonSearch)
        buttonClear = findViewById(R.id.buttonClear)

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
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }

    // Función para buscar la ubicación en el mapa
    private fun searchLocation() {
        val latitude = editTextLatitude.text.toString().toDoubleOrNull()
        val longitude = editTextLongitude.text.toString().toDoubleOrNull()

        if (latitude != null && longitude != null) {
            val location = LatLng(latitude, longitude)

            // Limpiar el mapa y agregar un nuevo marcador
            mMap.clear()
            mMap.addMarker(MarkerOptions().position(location).title("Ubicación seleccionada"))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
        } else {
            // Mensajes de error si los valores no son válidos
            editTextLatitude.error = "Ingrese una latitud válida"
            editTextLongitude.error = "Ingrese una longitud válida"
        }
    }

    // Función para limpiar los datos de los campos y el mapa
    private fun clearData() {
        // Limpiar los campos de texto
        editTextLatitude.text.clear()
        editTextLongitude.text.clear()

        // Limpiar el mapa
        mMap.clear()
    }
}
