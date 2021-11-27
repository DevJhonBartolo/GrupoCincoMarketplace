package com.jhondevs.grupocincomarketplace.ui.products

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.jhondevs.grupocincomarketplace.ProAdapter
import com.jhondevs.grupocincomarketplace.ProEntity
import com.jhondevs.grupocincomarketplace.databinding.FragmentProductBinding
import com.jhondevs.grupocincomarketplace.*
import com.jhondevs.grupocincomarketplace.R

class ProductsFragment : Fragment(), SearchView.OnQueryTextListener,
    AdapterView.OnItemSelectedListener, ProAdapter.OnItemClickListener  {

    private lateinit var productsViewModel:  ProductsViewModel
    private var _binding: FragmentProductBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var recycleView: RecyclerView
    private var db = FirebaseFirestore.getInstance()
    private var listProduct = mutableListOf<ProEntity>()
    private lateinit var productAdapter: ProAdapter

    private lateinit var svSearch: SearchView

    private lateinit var spinnerCategory: Spinner
    private lateinit var spinnerSeller: Spinner
    private var listCategory = arrayListOf<String>()
    private var listSeller = arrayListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        productsViewModel =
            ViewModelProvider(this).get(ProductsViewModel::class.java)
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //Recycler
        recycleView = root.findViewById<RecyclerView>(R.id.recycler1)
        recycleView.layoutManager = LinearLayoutManager(activity)
        recycleView.setHasFixedSize(true)

        getAllProduct()
        productAdapter = ProAdapter(listProduct, this);
        recycleView.adapter = productAdapter;

        val textView: TextView = binding.textHome
        productsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
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

                var productExist = listProduct.find { it.id == document.id }

                if (productExist == null) {
                    listProduct.add(
                        ProEntity(
                            document.data["cantidad"].toString(),
                            document.data["nombre"].toString(),
                            document.data["categoria"].toString(),
                            document.data["vendedor"].toString(),
                            document.data["precio"].toString().toInt(),
                            document.id
                            // averageScore(document.id)
                        )
                    )
                    // }

                }
                productAdapter.notifyDataSetChanged();
            }

        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        TODO("Not yet implemented")
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