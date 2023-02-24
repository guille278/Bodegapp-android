package com.example.bodegapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.bodegapp.databinding.FragmentLoginBinding
import com.google.android.material.textfield.TextInputLayout

class Login : Fragment() {

    private var _binding : FragmentLoginBinding ?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.signin.setOnClickListener{
            findNavController().navigate(R.id.action_login_to_signin)
        }

        binding.login.setOnClickListener{
            if (validateInput(binding.email, "Correo electronico obligatorio") and validateInput(binding.password, "Contraseña obligatoria")){
                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
                //this.activity?.finish()
            }
        }

        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun validateInput(input : TextInputLayout, errMsg : String): Boolean {
        var txt = input.editText?.text.toString()
        if (txt.isEmpty()){
            input.error = errMsg
            return false
        }
        input.error = null
        return true
    }

}