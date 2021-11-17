package com.jhondevs.grupocincomarketplace

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapterProducto(private val productoLista : ArrayList<Producto>) : RecyclerView.Adapter<MyAdapterProducto.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.producto_item,parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = productoLista[position]

        holder.cantidad.text = currentitem.cantidad
        holder.descripcion.text = currentitem.descripcion
        holder.precio.text = currentitem.precio.toString()
    }

    override fun getItemCount(): Int {

        return productoLista.size
    }

    class  MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val cantidad : TextView = itemView.findViewById(R.id.tvcantidad)
        val descripcion : TextView = itemView.findViewById(R.id.tvdescripcion)
        val precio : TextView = itemView.findViewById(R.id.tvprecio)


    }
}