package com.jhondevs.grupocincomarketplace

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*


class ListaProductosActivity : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var productoReciclerview : RecyclerView
    private lateinit var productoArraylist : ArrayList<Producto>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_productos)

        productoReciclerview = findViewById(R.id.productoLista)
        productoReciclerview.layoutManager = LinearLayoutManager(this)
        productoReciclerview.setHasFixedSize(true)

        productoArraylist = arrayListOf<Producto>()

        getProductoData()

    }

    private fun getProductoData(){

        dbref = FirebaseDatabase.getInstance().getReference("ProductosB")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for(productoSnapshot in snapshot.children){

                        val producto = productoSnapshot.getValue(Producto::class.java)
                        productoArraylist.add(producto!!)

                    }

                    productoReciclerview.adapter =MyAdapterProducto(productoArraylist)

                }
            }

            override fun onCancelled(error: DatabaseError) {

                Log.w(TAG, "Failed to read value.", error.toException());
            }

        })
    }
}