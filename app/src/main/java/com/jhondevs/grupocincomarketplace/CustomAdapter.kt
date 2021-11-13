package com.jhondevs.grupocincomarketplace

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter: RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    val nombres = arrayOf("Iphone 1 64 GB",
        "Iphone 2 32 GB","Iphone 3 128 GB",
        "Iphone 4 128 GB",
        "Iphone 5 64 GB",
        "Iphone 6 64 GB",
        "Iphone 7 64 GB",
        "Iphone 8 64 GB",
        "Iphone 9 64 GB",
        "Iphone 10 64 GB",)

    val precios = arrayOf("$799.000",
        "$1299.000","$999.000"
        ,"$999.000"
        ,"$999.000"
        ,"$999.000"
        ,"$999.000"
        ,"$999.000"
        ,"$999.000"
        ,"$999.000")

    val imagenes = intArrayOf(R.drawable.iphone1,
        R.drawable.iphone2,
        R.drawable.iphone3,
        R.drawable.iphone4,
        R.drawable.iphone5,
        R.drawable.iphone6,
        R.drawable.iphone7,
        R.drawable.iphone8,
        R.drawable.iphone9,
        R.drawable.iphone10,)

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