package com.jhondevs.grupocincomarketplace.ui.slideshow

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.jhondevs.grupocincomarketplace.OrdenAdapter
import com.jhondevs.grupocincomarketplace.OrdenEntity
import com.jhondevs.grupocincomarketplace.R
import com.jhondevs.grupocincomarketplace.databinding.FragmentSlideshowBinding

class SlideshowFragment : Fragment() {

    private lateinit var slideshowViewModel: SlideshowViewModel
    private var _binding: FragmentSlideshowBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var recycleView: RecyclerView
    private var db = FirebaseFirestore.getInstance()
    private var listaOrdenes = mutableListOf<OrdenEntity>()
    private lateinit var ordenAdapter: OrdenAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        slideshowViewModel =
            ViewModelProvider(this).get(SlideshowViewModel::class.java)

        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //Recycler
        recycleView = root.findViewById<RecyclerView>(R.id.recyclerOrden)
        recycleView.layoutManager = LinearLayoutManager(activity)
        recycleView.setHasFixedSize(true)

        getAllOrderNew()
        ordenAdapter = OrdenAdapter(listaOrdenes);
        recycleView.adapter = ordenAdapter;

        val textView: TextView = binding.textSlideshow
        slideshowViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    //funcion traer ordenes
    fun getAllOrderNew() {
        listaOrdenes.clear()

        val prefs = requireActivity().getSharedPreferences(resources.getString(R.string.prefs_file), Context.MODE_PRIVATE)
        var email = prefs.getString("email", null).toString()

        db.collection("carrito").whereEqualTo("estado", true).whereEqualTo("email",email)
            .get().addOnSuccessListener {
                if (it.any()) {

                    var idOrder = 1;
                    for (item in it) {

                        var des = ""

                        db.collection("carrito").document(item.id).collection("productoscarrito")
                            .get().addOnSuccessListener { productoscarrito ->
                                if (productoscarrito.any()) {
                                    for (product in productoscarrito) {
                                        des = des + product.data["nombre"] + " Cantidad: " + product.data["cantidad"] + "\n"
                                    }

                                    listaOrdenes.add(
                                        OrdenEntity(
                                            "Orden" + " " + idOrder,
                                            des
                                        )
                                    )
                                    idOrder++

                                    ordenAdapter.notifyDataSetChanged();
                                }
                            }

                    }
                    ordenAdapter.notifyDataSetChanged();
                }
            }
    }
}