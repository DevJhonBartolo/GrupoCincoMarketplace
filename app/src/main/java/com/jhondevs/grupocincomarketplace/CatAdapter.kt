package com.jhondevs.grupocincomarketplace

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class CatAdapter(private val dataSet: MutableList<CatEntity>, private val listener: CatAdapter.OnItemClickListener) :
    RecyclerView.Adapter<CatAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_categoria, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val catEn = dataSet[position]

        holder.nombrecategoria.text= catEn.nombre

        Picasso.get().load(catEn.imagen).into(holder.imagencategoria)

    }

    override fun getItemCount():Int{
        return dataSet.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val nombrecategoria :TextView = itemView.findViewById(R.id.tvnombrecategoria)
        val imagencategoria: ImageView =itemView.findViewById(R.id.tvimagencategoria)

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