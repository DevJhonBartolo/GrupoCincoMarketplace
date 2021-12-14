package com.jhondevs.grupocincomarketplace.ui.home

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.jhondevs.grupocincomarketplace.CatAdapter
import com.jhondevs.grupocincomarketplace.CatEntity
import com.jhondevs.grupocincomarketplace.R
import com.jhondevs.grupocincomarketplace.databinding.FragmentHomeBinding

class HomeFragment : Fragment(), SearchView.OnQueryTextListener,
    AdapterView.OnItemSelectedListener, CatAdapter.OnItemClickListener  {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!
    private lateinit var recycleView: RecyclerView
    private var db = FirebaseFirestore.getInstance()
    private var listaCategorias = mutableListOf<CatEntity>()
    private lateinit var CategoriaAdapter: CatAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //Recycler
        recycleView = root.findViewById<RecyclerView>(R.id.recyclerCategoria)
        recycleView.layoutManager = LinearLayoutManager(activity)
        recycleView.setHasFixedSize(true)

        traerCategoria()
        CategoriaAdapter = CatAdapter(listaCategorias, this)
        recycleView.adapter = CategoriaAdapter


        val textView: TextView = binding.textCategoria
        homeViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })
        return root
    }

    private fun traerCategoria() {
        listaCategorias.clear()

        db.collection("categoria").get().addOnSuccessListener { result ->
            for (document in result) {
                Log.d(ContentValues.TAG, "${document.id} => ${document.data}")

                val CategoriaExiste = listaCategorias.find { it.id == document.id }

                if (CategoriaExiste == null) {
                    listaCategorias.add(
                        CatEntity(
                            document.data["nombre"].toString(),
                            document.data["imagen"].toString(),
                            document.id
                        )
                    )
                }
                CategoriaAdapter.notifyDataSetChanged()
            }

        }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error obteniendo datos.", exception)
            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true;
    }

    override fun onQueryTextChange(newText: String?): Boolean {

        if (newText!!.isEmpty()) {
            traerCategoria()
        }
        return true;
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        TODO("Not yet implemented")
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }
}