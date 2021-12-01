package com.jhondevs.grupocincomarketplace

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import androidx.fragment.app.setFragmentResultListener


class ProDetailsFragment : Fragment() {

    private var db = FirebaseFirestore.getInstance()

    private var textproduct: TextView? = null
    private var imageproduct: ImageView? = null
    private var idProduct: TextView? = null
    private var desProduct: TextView? = null
    private var listViewComments: ListView? = null
    private var listComments = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //param1 = it.getString(ARG_PARAM1)
            //param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        var root = inflater.inflate(R.layout.fragment_pro_details, container, false)

        textproduct = root.findViewById<TextView>(R.id.textProduct)
        imageproduct = root.findViewById<ImageView>(R.id.imageProduct)
        desProduct = root.findViewById<TextView>(R.id.desProduct)
        idProduct = root.findViewById<TextView>(R.id.idProduct)
        listViewComments = root.findViewById<ListView>(R.id.listComments)

        setFragmentResultListener("key") { requestKey, bundle ->
            //Load Product
            bundle.getString("productos")?.let { loadProduct(it) };
        }

        return root

        //return inflater.inflate(R.layout.fragment_pro_details, container, false)
    }

    private fun loadProduct(product: String) {

        db.collection("productos").document(product).get()
            .addOnSuccessListener {
                textproduct!!.setText(it.get("nombre") as String)
                Picasso.get().load(it.get("imagen").toString()).into(imageproduct!!)
                idProduct!!.setText(it.id)
                desProduct!!.setText(it.get("descripcion") as String)

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
    }


}