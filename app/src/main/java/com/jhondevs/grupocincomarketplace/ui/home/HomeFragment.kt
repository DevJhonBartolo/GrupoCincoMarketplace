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
import com.jhondevs.grupocincomarketplace.ProAdapter
import com.jhondevs.grupocincomarketplace.ProEntity
import com.jhondevs.grupocincomarketplace.R
import com.jhondevs.grupocincomarketplace.databinding.FragmentHomeBinding

class HomeFragment : Fragment(), SearchView.OnQueryTextListener,
    AdapterView.OnItemSelectedListener, ProAdapter.OnItemClickListener  {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var recycleView: RecyclerView
    private var db = FirebaseFirestore.getInstance()
    private var listProduct = mutableListOf<ProEntity>()
    private lateinit var productAdapter: ProAdapter
    private lateinit var svSearch: SearchView




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
        recycleView = root.findViewById<RecyclerView>(R.id.recycler1)
        recycleView.layoutManager = LinearLayoutManager(activity)
        recycleView.setHasFixedSize(true)

        getAllProduct()
        productAdapter = ProAdapter(listProduct, this)
        recycleView.adapter = productAdapter

        //Search
        svSearch = root.findViewById<SearchView>(R.id.svSearch)
        svSearch.setOnQueryTextListener(this)

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })
        return root
    }

    private fun searchForTitle(newText: String) {
        listProduct.clear()
        db.collection("productos")
            .whereGreaterThanOrEqualTo("nombre", newText)
            .whereLessThanOrEqualTo("nombre", (newText + "\uF7FF"))
            .get().addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")

                    var productExist = listProduct.find { it.id == document.id }

                    if (productExist == null) {
                        listProduct.add(
                            ProEntity(
                                document.data["nombre"].toString(),
                                document.data["cantidad"].toString(),
                                document.data["categoria"].toString(),
                                document.data["vendedor"].toString(),
                                document.data["precio"].toString().toInt(),
                                document.data["imagen"].toString(),
                                document.id
                               // averageScore(document.id)
                            )
                        )
                    }
                }
                productAdapter.notifyDataSetChanged();
            }
    }


    private fun getAllProduct() {
        listProduct.clear()

        db.collection("productos").get().addOnSuccessListener { result ->
            for (document in result) {
                Log.d(ContentValues.TAG, "${document.id} => ${document.data}")

                //if (!listCategory.contains(document.data["category"].toString())) {
                //    listCategory.add(document.data["category"].toString())
                //}

                //if (!listSeller.contains(document.data["seller"].toString())) {
                //    listSeller.add(document.data["seller"].toString())
                //}

                val productExist = listProduct.find { it.id == document.id }

                if (productExist == null) {
                    listProduct.add(
                        ProEntity(
                            document.data["nombre"].toString(),
                            document.data["cantidad"].toString(),
                            document.data["categoria"].toString(),
                            document.data["vendedor"].toString(),
                            document.data["precio"].toString().toInt(),
                            document.data["imagen"].toString(),
                            document.id
                            // averageScore(document.id)
                        )
                    )
                    // }

                }
                productAdapter.notifyDataSetChanged()
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
            getAllProduct()
        } else {
            searchForTitle(newText)
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