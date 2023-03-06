package com.example.bodegapp

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.bodegapp.databinding.ActivityMainBinding
import com.example.bodegapp.databinding.ActivityProfileBinding
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.HashMap

class Profile : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var sharedPreferences: SharedPreferences

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        sharedPreferences = getSharedPreferences("auth", MODE_PRIVATE)
        val token = intent.getStringExtra("token")
        setSupportActionBar(binding.toolbarProfile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val web = Intent(Intent.ACTION_VIEW)

        val user  = JSONObject(sharedPreferences.getString("user", "").toString())

        val queque = Volley.newRequestQueue(applicationContext)

        binding.etName.editText?.setText("${user.getString("name")} ${user.getString("last_names")}")
        binding.etEmail.editText?.setText(user.getString("email"))
        binding.etPhone.editText?.setText(user.getString("phone"))
        binding.etRfc.editText?.setText(user.getString("rfc"))
        binding.etAddress.editText?.setText(user.getString("address"))

        if (!user.getString("verified").isNullOrEmpty()){
            binding.etVerified.setStartIconDrawable(R.drawable.baseline_check_circle_24)
            binding.etVerified.hint = "Verificado - ${DateTimeFormatter.ofPattern("dd/MM/yyyy").format(OffsetDateTime.ofInstant(Instant.parse(user.getString("verified")), ZoneId.systemDefault()))}"

        }


        binding.callSupport.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:${getString(R.string.support_phone)}")
            }
            startActivity(intent)
        }

        binding.faqs.setOnClickListener {
            web.setData(Uri.parse(getString(R.string.faqs_link)))
            startActivity(web)
        }

        binding.termsConditions.setOnClickListener {
            web.setData(Uri.parse(getString(R.string.terms_link)))
            startActivity(web)
        }

        binding.privacy.setOnClickListener {
            web.setData(Uri.parse(getString(R.string.privacy_link)))
            startActivity(web)
        }

        binding.signout.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Confirmación")
            alertDialog.setMessage("¿Desea terminar la sesión actual?")
            alertDialog.setNegativeButton("Cancelar", null)
            alertDialog.setPositiveButton(
                "Aceptar"
            ) { dialogInterface, i ->
                val request =
                    object : JsonObjectRequest(
                        "${resources.getString(R.string.api_url)}/auth/logout",
                        {
                            if (it.getBoolean("success")) {
                                clearPreferences()
                            }
                        },
                        {
                            clearPreferences()
                        }) {
                        override fun getHeaders(): MutableMap<String, String> {
                            val headers = HashMap<String, String>()
                            headers["Accept"] = "application/json"
                            headers["Authorization"] = "Bearer $token"
                            return headers
                        }
                    }
                queque.add(request)
            }

            alertDialog.create().show()
        }

        val view = binding.root
        setContentView(view)

    }

    private fun showDialog(title: String, message: String) {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle(title)
        dialog.setMessage(message)
        dialog.setPositiveButton("Aceptar", null)
        dialog.create().show()
    }

    fun clearPreferences() {
        sharedPreferences.edit().clear().apply()
        val intent = Intent(this, Auth::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }


}