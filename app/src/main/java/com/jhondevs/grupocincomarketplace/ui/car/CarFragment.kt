package com.jhondevs.grupocincomarketplace.ui.car

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.jhondevs.grupocincomarketplace.CarAdapter
import com.jhondevs.grupocincomarketplace.CarEntity
import com.jhondevs.grupocincomarketplace.R
import com.jhondevs.grupocincomarketplace.databinding.FragmentCarBinding

class CarFragment : Fragment(), CarAdapter.OnItemClickListener {

    private lateinit var carViewModel: CarViewModel
    private var _binding: FragmentCarBinding? = null
    private val binding get() = _binding!!

    private lateinit var recycleView: RecyclerView
    private var db = FirebaseFirestore.getInstance()
    private var listaCarrito = mutableListOf<CarEntity>()
    private lateinit var carAdapter: CarAdapter
    private var total = 0
    private var totalText :TextView?=null
    private var idCarrito = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        carViewModel =
            ViewModelProvider(this).get(CarViewModel::class.java)

        _binding = FragmentCarBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //captura el Id de los productos
        val prefs = requireActivity().getSharedPreferences(resources.getString(R.string.prefs_file), Context.MODE_PRIVATE)
        idCarrito = prefs.getString("carrito", null).toString()

        //Recycler
        recycleView = root.findViewById<RecyclerView>(R.id.reclyclerCar)
        recycleView.layoutManager = LinearLayoutManager(activity)
        recycleView.setHasFixedSize(true)

        getAllCarNew()
        carAdapter = CarAdapter(listaCarrito,this);
        recycleView.adapter = carAdapter

        totalText = root.findViewById<TextView>(R.id.total)

        //Boton Comprar
        var buttonBuy = root.findViewById<Button>(R.id.BtnComprar)
        buttonBuy.setOnClickListener {

            db.collection("carrito").document(idCarrito).update("estado",true)

            val prefsDeleteCar= requireActivity().getSharedPreferences(resources.getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefsDeleteCar.remove("carrito")
            prefsDeleteCar.apply()

        //Me lleva ala orden de la compra
            var nav = Navigation.createNavigateOnClickListener(R.id.nav_slideshow)
            nav.onClick(view);
        }

        val textView: TextView = binding.textCar
        carViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getAllCarNew() {
        listaCarrito.clear()
        if(idCarrito != null && idCarrito.isNotEmpty() && idCarrito != "null"){
            db.collection("carrito").document(idCarrito).collection("productoscarrito").get().addOnSuccessListener {
                if (it.any()) {
                    for (item in it) {
                        listaCarrito.add(
                            CarEntity(
                                item.data["nombre"].toString(),
                                item.data["cantidad"].toString(),
                                item.data["precio"].toString().toInt(),
                                item.data["imagen"].toString(),
                                item.id.toString()
                            )
                        )
                        total += (item.data["precio"].toString()
                            .toInt() * item.data["cantidad"].toString().toInt())

                        totalText!!.text = total.toString()
                    }
                    carAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    override fun onItemClick(position: Int) {
        val carItem: CarEntity = listaCarrito[position]

        if(idCarrito != null && idCarrito.isNotEmpty() && idCarrito != "null"){
            db.collection("carrito").document(idCarrito).collection("productoscarrito").document(carItem.id!!).delete()
        }
        getAllCarNew();
    }
}