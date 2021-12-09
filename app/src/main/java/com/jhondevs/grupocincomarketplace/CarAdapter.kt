package com.jhondevs.grupocincomarketplace

import android.location.GnssAntennaInfo
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.jhondevs.grupocincomarketplace.ui.car.CarFragment
import com.squareup.picasso.Picasso

import android.content.Context
import android.net.Uri
import android.graphics.drawable.Drawable
import android.widget.Button
import android.widget.Toast
import java.io.InputStream
import java.net.URL

public class CarAdapter(private val dataSet: MutableList<CarEntity>, private val listener: CarAdapter.OnItemClickListener) :
    RecyclerView.Adapter<CarAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.car_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val CarEn = dataSet[position]

        holder.nombrec.text= CarEn.nombre
        //holder.cantidad.text = CarEn.cantidad
        //holder.precioc.text = CarEn.precio.toString()

        var total = CarEn.precio!!.toInt() * CarEn.cantidad!!.toInt()

        holder.precioc.text = CarEn.cantidad +" x "+ CarEn.precio + " = " + total

        Picasso.get().load(CarEn.imagen).into(holder.imagenc)

    }

    override fun getItemCount():Int{
        return dataSet.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val nombrec :TextView = itemView.findViewById(R.id.tvnombrec)
        //val cantidad :TextView = itemView.findViewById(R.id.tvcantidadc)
        val precioc :TextView = itemView.findViewById(R.id.tvprecioc)
        val imagenc: ImageView =itemView.findViewById(R.id.tvimagenc)
        //val delete :Button = itemView.findViewById<Button>(R.id.delete)

        override fun onClick(v: View){
            if(adapterPosition != RecyclerView.NO_POSITION)
            {
                listener.onItemClick(adapterPosition)
            }

        }
    }
    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
}