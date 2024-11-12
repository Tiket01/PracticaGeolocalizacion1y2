package com.example.geolocalizacionapp

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

data class Ubicacion(
    val latitud: Double,
    val longitud: Double,
    val nombre_ubicacion: String
)

interface ApiService {
    @POST("ubicaciones")
    fun agregarUbicacion(@Body ubicacion: Ubicacion): Call<Ubicacion>

    @GET("ubicaciones")
    fun obtenerUbicaciones(): Call<List<Ubicacion>>
}
