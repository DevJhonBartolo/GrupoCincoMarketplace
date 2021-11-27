package com.jhondevs.grupocincomarketplace

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView


class ProAdapter(private val dataSet: MutableList<ProEntity>,
                        private val listener: OnItemClickListener) : RecyclerView.Adapter<ProAdapter.ViewHolder>() {

    val imagenes = intArrayOf(R.drawable.desengrasante,
        R.drawable.gelantibacterial,
        R.drawable.lavaloza)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.producto_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val proEn = dataSet[position]

        holder.cantidad.text = proEn.cantidad
        holder.nombre.text= proEn.nombre
        holder.precio.text = proEn.precio.toString()

        //Picasso.get().load(proEntity.imagen).into(holder.imagenItem);

        holder.categoria.text = proEn.categoria;

        holder.vendedor.text = proEn.vendedor;

        //holder.scoreItem.text = proEntity.score.toString();


        //holder.imagen.setImageResource(imagenes[position])

        //holder.imagen.text = currentitem.imagen
    }

    override fun getItemCount():Int{
        return dataSet.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val cantidad :TextView = itemView.findViewById(R.id.tvcantidad)
        val nombre :TextView = itemView.findViewById(R.id.tvdescripcion)
        val precio :TextView = itemView.findViewById(R.id.tvprecio)
        val categoria :TextView = itemView.findViewById(R.id.tvcategoria)
        val vendedor :TextView = itemView.findViewById(R.id.tvvendedor)
        //val imagen: ImageView =itemView.findViewById(R.id.tvimagen)

        //val categoryItem:TextView = itemView.findViewById<TextView>(R.id.categoryItem);
        //val sellerItem :TextView= itemView.findViewById<TextView>(R.id.sellerItem);
        //val scoreItem :TextView= itemView.findViewById<TextView>(R.id.scoreItem);

        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            if(adapterPosition != RecyclerView.NO_POSITION){
                listener.onItemClick(adapterPosition)
            }
        }

    }
    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }



}