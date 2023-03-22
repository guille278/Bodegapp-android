package com.example.bodegapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.bodegapp.databinding.FragmentLoginBinding
import com.example.bodegapp.databinding.FragmentSigninBinding
import com.google.android.material.textfield.TextInputLayout
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class Signin : Fragment() {
    private var _binding : FragmentSigninBinding ?= null
    private val binding get() = _binding!!
    private var datos: JSONObject = JSONObject()
    //private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSigninBinding.inflate(inflater, container, false)



        binding.registerAction.setOnClickListener{

            if (validateInput(binding.emailR, "Correo electronico obligatorio")
                and validateInput(binding.passwordR, "Contraseña obligatoria")
                and validateInput(binding.apellidoR,"Apellido obligatorio")
                and validateInput(binding.nameR,"Nombre obligatorio")
                and validateInput(binding.phoneR,"Teléfono obligatorio")
                and validateInput(binding.passwordR2,"Confirmación de contraseña obligatoria")
            ) {
                if((binding.passwordR2.editText?.text.toString() ==
                binding.passwordR.editText?.text.toString())
                and isValidString(binding.passwordR2.editText?.text.toString())){

                    if(isValidEmail(binding.emailR.editText?.text.toString())){

                        //////////////////////////////comienza api para registro

                        val calendar = Calendar.getInstance()
                        val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
                        val dateTime = dateFormat.format(calendar.time)

                        val queue = Volley.newRequestQueue(context)
                        datos.put("name", binding.nameR.editText?.text.toString())
                        datos.put("last_names", binding.apellidoR.editText?.text.toString())
                        datos.put("email", binding.emailR.editText?.text.toString())
                        datos.put("password", binding.passwordR.editText?.text.toString())
                        datos.put("verified", "verified".toString())
                        datos.put("address", "address".toString())
                        datos.put("identification", "identification".toString())
                        datos.put("rfc", "rfc".toString())
                        datos.put("phone", binding.phoneR.editText?.text.toString())
                        datos.put("updated_at",dateTime)
                        datos.put("created_at",dateTime)
                        //println(" es menor o igual que 10 $datos")



                        val request = JsonObjectRequest(
                            Request.Method.POST,
                            "${resources.getString(R.string.api_url)}/auth/register",
                            datos,
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
                                    showDialog("Error de registro", it.getString("errors").toString())
                                }
                            },
                            {
                                showDialog("Error en el servidor", it.toString())
                            })
                        queue.add(request)


                        /////////////////////////////finaliza api para registro
                    }else{
                        binding.emailR.error = "Correo con formato incorrecto"
                    }

                }else{

                    binding.passwordR.error = "Tamaño mínimo 8 caracteres"
                    binding.passwordR2.error = "La contraseña debe coincidir"

                }

            }
        }

        binding.cancelarRegister.setOnClickListener(){
            val fragmentManager = requireActivity().supportFragmentManager

            fragmentManager.popBackStack()
        }
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun cancelar(vista:View){

        val intent = Intent(context, Login::class.java)
        startActivity(intent)
        this.activity?.finish()
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

    fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")

        return emailRegex.matches(email)
    }

    fun isValidString(string: String): Boolean {
        return string.length >= 8
    }
    private fun showDialog(title: String, message: String) {
        val dialog = context?.let { AlertDialog.Builder(it) }
        dialog?.setTitle(title)
        dialog?.setMessage(message)
        dialog?.setPositiveButton("Aceptar") { _, _ -> binding.passwordR.editText?.setText("")  }
        dialog?.create()?.show()

    }

}