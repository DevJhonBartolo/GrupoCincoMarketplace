package com.jhondevs.grupocincomarketplace.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jhondevs.grupocincomarketplace.databinding.FragmentHomeBinding
import com.jhondevs.grupocincomarketplace.databinding.FragmentProductBinding

class ProductsFragment : Fragment() {

    private lateinit var productsViewModel:  ProductsViewModel
    private var _binding: FragmentProductBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        productsViewModel =
            ViewModelProvider(this).get(ProductsViewModel::class.java)

        _binding = FragmentProductBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        productsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}