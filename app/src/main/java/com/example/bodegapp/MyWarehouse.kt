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
import com.android.volley.toolbox.Volley
import com.example.bodegapp.databinding.FragmentExplorerBinding
import com.example.bodegapp.databinding.FragmentMyWarehouseBinding
import com.example.bodegapp.databinding.FragmentWarehousesBinding
import com.google.android.material.tabs.TabLayout
import org.json.JSONObject
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyWarehouse.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyWarehouse : Fragment() {
    // TODO: Rename and change types of parameters
   /* private var param1: String? = null
    private var param2: String? = null*/
    private var _binding: FragmentWarehousesBinding? = null
    private val binding get() = _binding!!
  /*  override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWarehousesBinding.inflate(inflater, container, false)

        val sharedPreferences =
            requireActivity().baseContext.getSharedPreferences("auth", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("auth", "")

        (activity as MainActivity).supportActionBar?.title = "Recibos"

        val user  = JSONObject(sharedPreferences.getString("user", "").toString())

        val queque = Volley.newRequestQueue(context)
        println(user.getInt("id"))
        val request = object : JsonArrayRequest(
            "${getString(R.string.api_url)}/storageByUser/${user.getInt("id")}",
            {
                println("${getString(R.string.api_url)}/storageByUser/${user.getInt("id")}")
                binding.rvBode.adapter = AdapterWar(it)
                binding.rvBode.layoutManager = LinearLayoutManager(context)
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



        //}
        return inflater.inflate(R.layout.fragment_my_warehouse, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MyWarehouse.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyWarehouse().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}