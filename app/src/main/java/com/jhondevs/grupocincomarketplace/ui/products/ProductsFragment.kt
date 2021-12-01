package com.jhondevs.grupocincomarketplace.ui.products

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
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

        //Search
        svSearch = root.findViewById<SearchView>(R.id.svSearch)
        svSearch.setOnQueryTextListener(this)

        //Spinner Category
        spinnerCategory = root.findViewById(R.id.category_spinner)
        spinnerCategory.onItemSelectedListener = this
        listCategory.add(resources.getString(R.string.test_All))

        activity?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item,
                listCategory
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerCategory.adapter = adapter
            }
        }

        //Spinner Seller
        spinnerSeller = root.findViewById(R.id.seller_spinner)
        spinnerSeller.onItemSelectedListener = this
        listSeller.add(resources.getString(R.string.test_All))

        activity?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item,
                listSeller
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerSeller.adapter = adapter
            }
        }

       // val textView: TextView = binding.textHome
       // productsViewModel.text.observe(viewLifecycleOwner, Observer {
       //     textView.text = it
       // })
        return root
    }

    private fun searchForTitle(newText: String) {
        listProduct.clear()
        db.collection("productos")
            .whereGreaterThanOrEqualTo("nombre", newText)
            .whereLessThanOrEqualTo("nombre", (newText + "\uF7FF"))
            .get().addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")

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

                if (!listCategory.contains(document.data["categoria"].toString())) {
                    listCategory.add(document.data["categoria"].toString())
                }

                if (!listSeller.contains(document.data["vendedor"].toString())) {
                    listSeller.add(document.data["vendedor"].toString())
                }

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
                            document.id,
                            averageScore(document.id)
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
        if (parent != null) {

            var categoria: String = spinnerCategory.getSelectedItem().toString()
            var vendedor: String = spinnerSeller.getSelectedItem().toString()

            var all = "All"

            if (categoria == all && vendedor == all) {
                getAllProduct()
            } else {
                if (categoria == all) {
                    filterForSeller(vendedor)
                } else {
                    if (vendedor == all) {
                        filterForCategory(categoria)
                    } else {
                        filterForCategoryAndSeller(categoria, vendedor)
                    }
                }
            }

        }
    }

    private fun filterForCategory(categoria: String) {
        listProduct.clear()

        db.collection("productos")
            .whereEqualTo("categoria", categoria)
            .get().addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")

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
                                document.id,
                                averageScore(document.id)
                            )
                        )
                    }
                }
                productAdapter.notifyDataSetChanged();
            }
    }

    private fun filterForSeller(vendedor: String) {
        listProduct.clear()

        db.collection("productos")
            .whereEqualTo("vendedor", vendedor)
            .get().addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")

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
                                document.id,
                                averageScore(document.id)
                            )
                        )
                    }
                }
                productAdapter.notifyDataSetChanged();
            }
    }

    private fun filterForCategoryAndSeller(categoria: String, vendedor: String) {
        listProduct.clear()

        db.collection("productos")
            .whereEqualTo("vendedor", vendedor).whereEqualTo("categoria", categoria)
            .get().addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")

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
                                document.id,
                                averageScore(document.id)
                            )
                        )
                    }
                }
                productAdapter.notifyDataSetChanged();
            }
    }

    private fun averageScore(product: String):Double{

        var average : Double = 0.0

        db.collection("productos").document(product).collection("score").get()
            .addOnSuccessListener {

                if (it.any()) {
                    for (score in it) {
                        average += score.get("score").toString().toDouble()
                    }
                    average /= it.count()
                    productAdapter.notifyDataSetChanged();
                }
            }

        return average
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemClick(position: Int) {
       val productItem: ProEntity = listProduct[position]

        //Go  ProDetailsActivity
        val prefs = requireActivity().getSharedPreferences(resources.getString(R.string.prefs_file),Context.MODE_PRIVATE)
        val email = prefs.getString("email", null)

        var bundle =Bundle()
        bundle.putString("email",email)
        bundle.putString("productos",productItem.id)
        parentFragmentManager.setFragmentResult("key",bundle)

        var nav = Navigation.createNavigateOnClickListener(R.id.nav_pro_details)
        nav.onClick(view);
    }
}