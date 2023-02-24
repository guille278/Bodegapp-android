package com.example.bodegapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bodegapp.databinding.FragmentExplorerBinding
import com.google.android.material.tabs.TabLayout


class Explorer : Fragment() {
    private var _binding : FragmentExplorerBinding?= null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        _binding = FragmentExplorerBinding.inflate(inflater, container, false)

        binding.rvCards.adapter = WarehouseAdapter()
        binding.rvCards.layoutManager = LinearLayoutManager(context)

        binding.myTabLayout.addOnTabSelectedListener(object  :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                Toast.makeText(context, "${tab?.text}", Toast.LENGTH_SHORT).show()
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