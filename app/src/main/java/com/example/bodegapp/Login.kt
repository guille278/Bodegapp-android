package com.example.bodegapp

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.android.volley.Request.Method
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.bodegapp.databinding.FragmentLoginBinding
import com.google.android.material.textfield.TextInputLayout
import org.json.JSONObject

class Login : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private var credentials: JSONObject = JSONObject()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        val queue = Volley.newRequestQueue(context)

        binding.signin.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_signin)
        }
        binding.registerL.setOnClickListener(){
            val intent = Intent(context, Register::class.java)
            startActivity(intent)
            this.activity?.finish()
        }


        binding.login.setOnClickListener {
            if (validateInput(binding.email, "Correo electronico obligatorio") and validateInput(
                    binding.password,
                    "Contraseña obligatoria"
                )
            ) {
                credentials.put("email", binding.email.editText?.text.toString())
                credentials.put("password", binding.password.editText?.text.toString())
                val request = JsonObjectRequest(
                    Method.POST,
                    "${resources.getString(R.string.api_url)}/auth/login",
                    credentials,
                    {
                        if (it.getBoolean("success")) {
                            val sharedPreferences = activity?.getSharedPreferences("auth", Context.MODE_PRIVATE)
                            val editor = sharedPreferences?.edit()
                            editor?.putString("auth", it.getString("token"))
                            editor?.putString("user", it.getJSONObject("user").toString().replace("null", "\"\""))
                            editor?.apply()
                            val intent = Intent(context, MainActivity::class.java)
                            startActivity(intent)
                            this.activity?.finish()
                        } else {
                            showDialog("Error de autenticación", it.getString("errors").toString())
                        }
                    },
                    {
                        showDialog("Error en el servidor", it.toString())
                    })
                queue.add(request)
            }
        }

        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun validateInput(input: TextInputLayout, errMsg: String): Boolean {
        var txt = input.editText?.text.toString()
        if (txt.isEmpty()) {
            input.error = errMsg
            return false
        }
        input.error = null
        return true
    }


    private fun showDialog(title: String, message: String) {
        val dialog = context?.let { AlertDialog.Builder(it) }
        dialog?.setTitle(title)
        dialog?.setMessage(message)
        dialog?.setPositiveButton("Aceptar") { _, _ -> binding.password.editText?.setText("") }
        dialog?.create()?.show()

    }

}