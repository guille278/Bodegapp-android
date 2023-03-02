package com.example.bodegapp

import android.content.DialogInterface
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.bodegapp.databinding.FragmentExplorerBinding
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso
import org.json.JSONArray
import java.util.*


class Explorer : Fragment() {
    private var _binding: FragmentExplorerBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentExplorerBinding.inflate(inflater, container, false)

        val queque = Volley.newRequestQueue(context)
        val requestCategories =
            JsonArrayRequest("${resources.getString(R.string.api_url)}/categories", { categories ->
                (0 until categories.length()).forEach {
                    binding.myTabLayout.addTab(
                        binding.myTabLayout.newTab()
                            .setText(categories.getJSONObject(it).getString("name").replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })
                            .setId(categories.getJSONObject(it).getInt("id"))
                            .setContentDescription(categories.getJSONObject(it).getString("name"))
                            /*.setIcon(
                                resources.getDrawable(
                                    categories.getJSONObject(it).getInt("src"), null
                                )
                            )*/

                    )
                    binding.myTabLayout.visibility = View.VISIBLE
                }
            }) {
                val alertDialog = context?.let { it1 -> AlertDialog.Builder(it1) }
                alertDialog?.setTitle("Error en el servidor")
                alertDialog?.setMessage("Error: ${it}")
                alertDialog?.setPositiveButton("Aceptar",null)
                alertDialog?.create()?.show()
            }

        queque.add(requestCategories)

        binding.myTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val requestWarehouses =
                    JsonArrayRequest(
                        "${resources.getString(R.string.api_url)}/storages/category/${tab?.id}",
                        {
                            binding.rvCards.adapter = context?.let { it1 ->
                                WarehouseAdapter(it,
                                    it1
                                )
                            }
                            binding.rvCards.layoutManager = LinearLayoutManager(context)
                        },
                        {
                            Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
                        })
                queque.add(requestWarehouses)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}