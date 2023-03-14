package com.example.bodegapp

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.bodegapp.databinding.ActivityMainBinding
import com.example.bodegapp.databinding.ActivityProfileBinding
import com.example.bodegapp.databinding.ActivityRegisterBinding
import com.example.bodegapp.databinding.FragmentLoginBinding
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.HashMap
import com.google.android.material.textfield.TextInputLayout

class Register : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    //private var _binding: ActivityRegisterBinding? = null
    //private val binding get() = _binding!!
    private lateinit var sharedPreferences: SharedPreferences
    private var datos: JSONObject = JSONObject()

    private lateinit var name: TextInputLayout
    private lateinit var email: TextInputLayout
    private lateinit var last_names: TextInputLayout
    private lateinit var password: TextInputLayout
    private lateinit var password2: TextInputLayout

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        sharedPreferences = getSharedPreferences("auth", MODE_PRIVATE)

        name = findViewById<TextInputLayout>(R.id.nameR)
        email = findViewById<TextInputLayout>(R.id.emailR)
        last_names = findViewById<TextInputLayout>(R.id.apellidoR)
        password = findViewById<TextInputLayout>(R.id.passwordR)
        password2 = findViewById<TextInputLayout>(R.id.passwordR)


      /*  binding.registerAction.setOnClickListener(){

            Toast.makeText(this, "El resultado de la suma ", Toast.LENGTH_SHORT).show()
            //val prueba = binding.nameR.editText?.text.toString()
            println(" es menor o igual que 10")
        }*/
    }
     fun cancelar(vista:View){

         sharedPreferences.edit().clear().apply()
         startActivity(Intent(applicationContext, Auth::class.java))
         finish()
         //println(" Ahuevo "+ email.editText?.text.toString())
    }
    fun guardar(vista:View){
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
        val dateTime = dateFormat.format(calendar.time)

        val queue = Volley.newRequestQueue(this)
        datos.put("name", name.editText?.text.toString())
        datos.put("last_names", last_names.editText?.text.toString())
        datos.put("email", email.editText?.text.toString())
        datos.put("password", password.editText?.text.toString())
        datos.put("updated_at",dateTime)
        datos.put("created_at",dateTime)
        println(" es menor o igual que 10 $datos")



        val request = JsonObjectRequest(
            Request.Method.POST,
            "${resources.getString(R.string.api_url)}/auth/register",
            datos,
            {
                if (it.getBoolean("success")) {
                    //val sharedPreferences = activity?.getSharedPreferences("auth", Context.MODE_PRIVATE)
                    val editor = sharedPreferences?.edit()
                    editor?.putString("auth", it.getString("token"))
                    editor?.putString("user", it.getJSONObject("user").toString().replace("null", "\"\""))
                    editor?.apply()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    //this.activity?.finish()
                } else {
                    showDialog("Error de autenticación", it.getString("errors").toString())
                }
            },
            {
                showDialog("Error en el servidor", it.toString())
            })
        queue.add(request)


    }
    private fun showDialog(title: String, message: String) {
        val dialog = this?.let { AlertDialog.Builder(it) }
        dialog?.setTitle(title)
        dialog?.setMessage(message)
        dialog?.setPositiveButton("Aceptar") { _, _ -> password.editText?.setText("") }
        dialog?.create()?.show()

    }

}