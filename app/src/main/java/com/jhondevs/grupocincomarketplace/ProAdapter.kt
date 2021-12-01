package com.jhondevs.grupocincomarketplace

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso


class ProAdapter(private val dataSet: MutableList<ProEntity>,
                        private val listener: OnItemClickListener) : RecyclerView.Adapter<ProAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.producto_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val proEn = dataSet[position]

        holder.nombre.text= proEn.nombre
        holder.cantidad.text = proEn.cantidad
        holder.categoria.text = proEn.categoria
        holder.vendedor.text = proEn.vendedor
        holder.precio.text = proEn.precio.toString()
        Picasso.get().load(proEn.imagen).into(holder.imagen)

        var average = proEn.average;

        if (average != null) {
            if(average > 0){
                holder.score.text = average.toString()
            }else{
                holder.score.visibility = View.INVISIBLE;
            }
        }

        //holder.scoreItem.text = proEntity.score.toString();

        //holder.imagen.setImageResource(imagenes[position])
        //holder.imagen.text = currentitem.imagen
    }

    override fun getItemCount():Int{
        return dataSet.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val nombre :TextView = itemView.findViewById(R.id.tvdescripcion)
        val cantidad :TextView = itemView.findViewById(R.id.tvcantidad)
        val categoria :TextView = itemView.findViewById(R.id.tvcategoria)
        val vendedor :TextView = itemView.findViewById(R.id.tvvendedor)
        val precio :TextView = itemView.findViewById(R.id.tvprecio)
        val imagen: ImageView =itemView.findViewById(R.id.tvimagen)
        val score :TextView= itemView.findViewById<TextView>(R.id.tvscore);


        //val categoryItem:TextView = itemView.findViewById<TextView>(R.id.categoryItem);
        //val sellerItem :TextView= itemView.findViewById<TextView>(R.id.sellerItem);


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