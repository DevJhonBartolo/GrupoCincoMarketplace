package com.jhondevs.grupocincomarketplace

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter: RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    val nombres = arrayOf("Sansumg a72",
    "Nokia 1100","Huawei")

    val precios = arrayOf("$799.000",
        "$1299.000","$999.000")

    val imagenes = intArrayOf(R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground)

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.tarjeta_productos,viewGroup,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.nombre.text=nombres[i]
        viewHolder.precio.text=precios[i]
        viewHolder.imagen.setImageResource(imagenes[i])
    }

    override fun getItemCount(): Int {
        return nombres.size
    }

    inner class  ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var imagen: ImageView
        var nombre: TextView
        var precio: TextView

        init {
            imagen =itemView.findViewById(R.id.imagen)
            nombre =itemView.findViewById(R.id.nombre)
            precio =itemView.findViewById(R.id.precio)
        }
    }
}