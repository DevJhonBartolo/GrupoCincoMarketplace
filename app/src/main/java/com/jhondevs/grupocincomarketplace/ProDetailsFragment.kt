package com.jhondevs.grupocincomarketplace

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.Navigation
import java.util.*


class ProDetailsFragment : Fragment() {

    private var db = FirebaseFirestore.getInstance()

    private var textproduct: TextView? = null
    private var amountText: TextView? = null
    private var costProduct: TextView? = null
    private var imageproduct: ImageView? = null
    private var idProduct: TextView? = null
    private var desProduct: TextView? = null
    private var listViewComments: ListView? = null
    private var listComments = arrayListOf<String>()
    private var costUnit = 0
    private var image = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //param1 = it.getString(ARG_PARAM1)
            //param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("LongLogTag")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        var root = inflater.inflate(R.layout.fragment_pro_details, container, false)

        textproduct = root.findViewById<TextView>(R.id.textProduct)
        amountText = root.findViewById<TextView>(R.id.amountText)
        costProduct = root.findViewById<TextView>(R.id.costProduct)
        imageproduct = root.findViewById<ImageView>(R.id.imageProduct)
        desProduct = root.findViewById<TextView>(R.id.desProduct)
        idProduct = root.findViewById<TextView>(R.id.idProduct)
        listViewComments = root.findViewById<ListView>(R.id.listComments)


        //Adicionar Productos al Carrito
        setFragmentResultListener("key") { requestKey, bundle ->
            //Load Product
            bundle.getString("productos")?.let { loadProduct(it) };
        }
        //adicionar Producto al Carrito
        var btnAdicionarCarrito = root.findViewById<Button>(R.id.adicionarProducto)

        btnAdicionarCarrito.setOnClickListener {
            Log.d(ContentValues.TAG,"carrito")


        var idCarrito=""

        val getPrefs = requireActivity().getSharedPreferences(resources.getString(R.string.prefs_file), Context.MODE_PRIVATE)
        idCarrito = getPrefs.getString("carrito", null).toString()
            var email = getPrefs.getString("email", null).toString()

            if(idCarrito == null || idCarrito.isEmpty() || idCarrito == "null"){
                idCarrito = UUID.randomUUID().toString()
                val setPrefs = requireActivity().getSharedPreferences(resources.getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
                setPrefs.putString("carrito",idCarrito)
                setPrefs.apply()
            }

            db.collection("carrito")
                .document(idCarrito)
                .set(hashMapOf("estado" to false,"email" to email,"carritoid" to idCarrito))

           db.collection("carrito").document(idCarrito)
                .collection("productoscarrito").document(idProduct!!.text.toString()).set(
                    hashMapOf("cantidad" to amountText!!.text.toString(),
                       "precio" to costUnit!!.toString(),
                       "imagen" to image,
                        "nombre" to textproduct!!.text.toString(),

                    ))
               .addOnFailureListener { e ->
                   Log.w(TAG, "Error adding documentossss", e)



               }
            Log.e("insercion carro pruductos",amountText!!.text.toString())
            Log.e("insercion carro pruductos",costUnit!!.toString())
            Log.e("insercion carro pruductos",image)
            Log.e("insercion carro pruductos",textproduct!!.text.toString())
            Log.e("insercion carro pruductos",idProduct!!.text.toString())


            var nav = Navigation.createNavigateOnClickListener(R.id.nav_car)
            nav.onClick(view);

        }

        return root

        //return inflater.inflate(R.layout.fragment_pro_details, container, false)
    }

    //Retorna el detalle del producto
    private fun loadProduct(product: String) {

        db.collection("productos").document(product).get()
            .addOnSuccessListener {
                textproduct!!.setText(it.get("nombre") as String)
                Picasso.get().load(it.get("imagen").toString()).into(imageproduct!!)
                image = it.get("imagen").toString()

                idProduct!!.setText(it.id)


                desProduct!!.setText(it.get("descripcion") as String)

                costProduct!!.text =  it.get("precio").toString()
                costUnit = it.get("precio").toString().toInt()


            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

        db.collection("productos").document(product).collection("comentarios").get()
            .addOnSuccessListener {

                if (it.any()) {

                    for (comment in it) {
                        listComments.add(
                            comment.get("user").toString() + " : " + comment.get("comentario").toString()
                        )
                    }

                    listViewComments!!.adapter = activity?.let { it1 ->
                        ArrayAdapter(
                            it1,
                            android.R.layout.simple_dropdown_item_1line,
                            listComments
                        )
                    }

                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }


}