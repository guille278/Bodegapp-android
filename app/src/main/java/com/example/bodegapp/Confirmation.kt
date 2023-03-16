package com.example.bodegapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.bodegapp.databinding.FragmentConfirmationBinding
import com.example.bodegapp.databinding.FragmentPlanBinding

class Confirmation : Fragment() {
    private var _binding: FragmentConfirmationBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentConfirmationBinding.inflate(inflater, container, false)

        binding.finish.setOnClickListener{
            startActivity(Intent(context, MainActivity::class.java))
            this.activity?.finish()
        }

        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}