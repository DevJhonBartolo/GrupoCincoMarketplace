package com.jhondevs.grupocincomarketplace

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

public class OrdenAdapter(private val dataSet: MutableList<OrdenEntity>):
    RecyclerView.Adapter<OrdenAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_orden, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var ordenEntity = dataSet[position]

        holder.titleItems.text = ordenEntity.nombre
        holder.desItem.text = ordenEntity.descripcion

    }

    override fun getItemCount():Int{
        return dataSet.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleItems : TextView = itemView.findViewById<TextView>(R.id.tvnombreo)
        val desItem : TextView = itemView.findViewById<TextView>(R.id.tvdescripciono)
    }

}