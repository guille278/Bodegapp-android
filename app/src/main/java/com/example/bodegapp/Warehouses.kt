package com.example.bodegapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bodegapp.databinding.FragmentWarehousesBinding

class Warehouses : Fragment() {

    private var _binding: FragmentWarehousesBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWarehousesBinding.inflate(inflater, container, false)

        (activity as MainActivity).supportActionBar?.title = "Mis bodegas"

        binding.myWarehouse.visibility = View.VISIBLE
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}