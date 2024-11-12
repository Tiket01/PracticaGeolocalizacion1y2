package com.example.geolocalizacionapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UbicacionesAdapter(private var ubicaciones: List<Ubicacion>) :
    RecyclerView.Adapter<UbicacionesAdapter.UbicacionViewHolder>() {

    fun setUbicaciones(nuevasUbicaciones: List<Ubicacion>) {
        ubicaciones = nuevasUbicaciones
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UbicacionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ubicacion, parent, false)
        return UbicacionViewHolder(view)
    }

    override fun onBindViewHolder(holder: UbicacionViewHolder, position: Int) {
        holder.bind(ubicaciones[position])
    }

    override fun getItemCount() = ubicaciones.size

    class UbicacionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombreTextView: TextView = itemView.findViewById(R.id.textViewNombre)
        private val coordenadasTextView: TextView = itemView.findViewById(R.id.textViewCoordenadas)

        fun bind(ubicacion: Ubicacion) {
            nombreTextView.text = ubicacion.nombre_ubicacion
            coordenadasTextView.text = "Lat: ${ubicacion.latitud}, Lng: ${ubicacion.longitud}"
        }
    }
}
