package com.example.bodegapp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.bodegapp.databinding.FragmentBillingBinding
import java.util.HashMap

class Billing : Fragment() {

    private var _binding: FragmentBillingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBillingBinding.inflate(inflater, container, false)

        val sharedPreferences =
            requireActivity().baseContext.getSharedPreferences("auth", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("auth", "")

        (activity as MainActivity).supportActionBar?.title = "Recibos"

        val queque = Volley.newRequestQueue(context)

        val request = object : JsonArrayRequest(
            "${getString(R.string.api_url)}/billing",
            {
                binding.rvBilling.adapter = BillingAdapter(it)
                binding.rvBilling.layoutManager = LinearLayoutManager(context)
            },
            {
                val alertDialog = context?.let { it1 -> AlertDialog.Builder(it1) }
                alertDialog?.setTitle("Error en el servidor")
                alertDialog?.setMessage("Error: ${it}")
                alertDialog?.setPositiveButton("Aceptar", null)
                alertDialog?.create()?.show()
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                headers["Authorization"] = "Bearer $token"
                return headers
            }
        }

        queque.add(request)


        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}